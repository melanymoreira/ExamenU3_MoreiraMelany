package grafo.aydacencia;

import java.util.ArrayList;

public class GrafoAdcia {

    int numVerts;
    int maxVerts;
    VerticeAdy[] tablAdc;

    public GrafoAdcia(int mx) {
        tablAdc = new VerticeAdy[mx];
        numVerts = 0;
        maxVerts = mx;
    }
    
    // Agregar la metodo que permite agregar un nuevo vertice
 	/*
 	 * A ade un nuevo vertice a la lista directorio. Si el vertice ya esta en la tabla no hace nada, 
 	 * devuelve control; si es nuevo, se asigna a continuacion del ultimo. 
 	 */
	public void agregarVertice(VerticeAdy vertice) {
		if (numVerts < maxVerts) {
			if (buscarVertice(vertice) == null) {
				tablAdc[numVerts] = vertice;
				vertice.asigVert(numVerts++);
			}
		}
	}

	private VerticeAdy buscarVertice(VerticeAdy vertice) {
		for (int i = 0; i < numVerts; i++) {
			if (tablAdc[i].equals(vertice)) {
				return tablAdc[i];
			}
		}
		return null;
	}
	
	//Agregar m todo que permite agregar un nuevo arco
		/*
		 * Esta operaci n da entrada a un arco del grafo. El m todo tiene como entrada el v rtice origen y el 
		 * v rtice destino. El m todo adyacente() determina si la arista ya est  en la lista de adyacencia, 
		 * y, por  ltimo, el Arco se inserta en la lista de adyacencia del nodo origen. 
		 * La inserci n se hace como primer nodo para que el tiempo sea constante, O(1). 
		 * Otra versi n del m todo recibe, directamente, los n meros de v rtices que forman los extremos del arco. 
		 * ara a adir una arista en un grafo valorado, se debe asignar el factor de peso al crear el Arco.
		 */
	public void agregarArco(VerticeAdy origen, VerticeAdy destino, double peso) {
		if (origen.numVertice >= 0 && origen.numVertice < numVerts && destino.numVertice >= 0 && destino.numVertice < numVerts) {
			if (!adyacente(origen, destino)) {
				Arco nuevoArco = new Arco(destino.numVertice, peso);
				origen.lad.add(nuevoArco);
			}
		}
	}
	
	//Agregar un m todo que determina si dos v rtices son adyacentes
		/*
		 * La operaci n determina si dos v rtices, v1 y v2, forman un arco. En definitiva, 
		 * si el v rtice v2 est  en la lista de adyacencia de v1. 
		 * El m todo buscarLista() devuelve la direcci n del nodo que contiene al arco, si no est  devuelve null. 
		 */
	public boolean adyacente(VerticeAdy origen, VerticeAdy destino) {
        if (origen.numVertice >= 0 && origen.numVertice < numVerts && destino.numVertice >= 0 && destino.numVertice < numVerts) {
        	for (Arco arco : origen.lad) {
                if (arco.destino == destino.numVertice) {
                    return true;
                }
            }
        }
        return false;
    }
	
	
	// recorra el grafo en profundidad
	public void dfs(int inicio) {
        boolean[] visitados = new boolean[numVerts];
        dfsRecursivo(inicio, visitados);
    }

    private void dfsRecursivo(int vertice, boolean[] visitados) {
        visitados[vertice] = true;
        VerticeAdy v = tablAdc[vertice];
        System.out.println("Visitando vertice: " + v.nomVertice());

        for (Arco arco : v.lad) {
            if (!visitados[arco.destino]) {
                dfsRecursivo(arco.destino, visitados);
            }
        }
    }
    
    public VerticeAdy buscarVerticePorNombre(String nombre) {
        for (int i = 0; i < numVerts; i++) {
            if (tablAdc[i].nombreCiudad.equals(nombre)) {
                return tablAdc[i];
            }
        }
        return null;
    }
}