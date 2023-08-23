package grafo.aydacencia;

import java.util.ArrayList;

public class VerticeAdy {
	
	String nombreCiudad;
	int numVertice;
	ArrayList<Arco> lad; // lista de adyacencia
	
	// constructor de la clase
	public VerticeAdy(String x) {
		nombreCiudad = x;
		numVertice = -1;
		lad = new ArrayList<>();
	}
	
	public String nomVertice() {
		return nombreCiudad;
	}

	public boolean equals(VerticeAdy n) {
		return nombreCiudad.equals(n.nombreCiudad);
	}

	public void asigVert(int n) {
		numVertice = n;
	}
	
	public String toString() {
		return nombreCiudad + " (" + numVertice + ")";
	}
}
