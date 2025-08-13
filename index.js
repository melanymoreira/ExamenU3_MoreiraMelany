require('dotenv').config();
const express = require('express');
const passport = require('passport');
const GitHubStrategy = require('passport-github2').Strategy;
const jwt = require('jsonwebtoken');
const cookieParser = require('cookie-parser');
const http = require('http');
const { Server } = require('socket.io');
const cors = require('cors');

const app = express();
const server = http.createServer(app);
const io = new Server(server);

app.use(cors({ origin: true, credentials: true }));
app.use(cookieParser());
app.use(express.static('public'));

// Passport GitHub OAuth
passport.use(new GitHubStrategy({
    clientID: process.env.GITHUB_CLIENT_ID,
    clientSecret: process.env.GITHUB_CLIENT_SECRET,
    callbackURL: "/auth/github/callback"
  },
  function(accessToken, refreshToken, profile, done) {
    // Puedes guardar el usuario en la base de datos aquí si lo deseas
    return done(null, profile);
  }
));

app.use(passport.initialize());

// Rutas OAuth
app.get('/auth/github', passport.authenticate('github', { scope: [ 'user:email' ] }));

app.get('/auth/github/callback',
  passport.authenticate('github', { session: false, failureRedirect: '/' }),
  (req, res) => {
    // Generar JWT
    const token = jwt.sign({
      id: req.user.id,
      name: req.user.displayName || req.user.username
    }, process.env.JWT_SECRET, { expiresIn: '1h' });

    // Mostrar en consola el JWT y los datos del usuario
    console.log('Usuario autenticado:', {
      id: req.user.id,
      name: req.user.displayName || req.user.username
    });
    console.log('JWT generado:', token);

    // Enviar JWT en cookie httpOnly
    res.cookie('token', token, { httpOnly: true });
    res.redirect('/');
  }
);

// Middleware de protección con JWT
function authenticateJWT(req, res, next) {
  const token = req.cookies.token || req.headers.authorization?.split(' ')[1];
  if (!token) return res.status(401).json({ error: 'No token' });
  jwt.verify(token, process.env.JWT_SECRET, (err, user) => {
    if (err) return res.status(403).json({ error: 'Token inválido' });
    req.user = user;
    next();
  });
}

// Ruta protegida
app.get('/api/profile', authenticateJWT, (req, res) => {
  res.json({ user: req.user });
});

// Socket.io: Notificador de "Tarea Terminada"
io.on('connection', (socket) => {
  socket.on('tarea:terminada', (nombre) => {
    io.emit('notificacion', `¡${nombre} ha terminado la tarea!`);
  });
});

app.get('/logout', (req, res) => {
  res.clearCookie('token');
  res.redirect('/');
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => console.log(`Servidor en http://localhost:${PORT}`));