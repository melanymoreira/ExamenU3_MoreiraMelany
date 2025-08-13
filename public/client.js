const socket = io();

async function getProfile() {
  const res = await fetch('/api/profile', { credentials: 'include' });
  if (res.ok) {
    const data = await res.json();
    document.getElementById('login').style.display = 'none';
    document.getElementById('app').style.display = '';
    document.getElementById('user').textContent = `Hola, ${data.user.name}`;
    document.getElementById('doneBtn').onclick = () => {
      socket.emit('tarea:terminada', data.user.name);
    };
  }
}

socket.on('notificacion', msg => {
  const div = document.getElementById('notificaciones');
  div.innerHTML = `<p>${msg}</p>` + div.innerHTML;
});

document.getElementById('logoutBtn').onclick = () => {
  fetch('/logout', { credentials: 'include' })
    .then(() => window.location.reload());
};

getProfile();