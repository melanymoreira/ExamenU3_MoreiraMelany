package grafo.aydacencia;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxVertices;
        do {
            System.out.print("Ingrese la cantidad maxima de vertices en el grafo (maximo 20): ");
            maxVertices = scanner.nextInt();
            if (maxVertices <= 0 || maxVertices > 20) {
                System.out.println("Cantidad de vertices invalida. Debe estar entre 1 y 20.");
            }
        } while (maxVertices <= 0 || maxVertices > 20);

        GrafoAdcia grafo = new GrafoAdcia(maxVertices);

        while (true) {
        	System.out.println("***********************");
        	System.out.println("****Melany  Moreira****");
        	System.out.println("***********************");
            System.out.println("1. Agregar vertice");
            System.out.println("2. Agregar arco");
            System.out.println("3. Realizar recorrido en profundidad (DFS)");
            System.out.println("0. Salir");
            System.out.println("***********************");
            System.out.print("Elija una opcion: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del nodo: ");
                    String nombreCiudad = scanner.next();
                    VerticeAdy vertice = new VerticeAdy(nombreCiudad);
                    grafo.agregarVertice(vertice);
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del nodo origen: ");
                    String origenCiudad = scanner.next();
                    System.out.print("Ingrese el nombre del nodo destino: ");
                    String destinoCiudad = scanner.next();
                    System.out.print("Ingrese el peso del arco: ");
                    double pesoArco = scanner.nextDouble();

                    VerticeAdy origenVertice = grafo.buscarVerticePorNombre(origenCiudad);
                    VerticeAdy destinoVertice = grafo.buscarVerticePorNombre(destinoCiudad);

                    if (origenVertice != null && destinoVertice != null) {
                        grafo.agregarArco(origenVertice, destinoVertice, pesoArco);
                    } else {
                        System.out.println("Nodos no encontrados.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese el número del vértice de inicio para el DFS: ");
                    int inicioDFS = scanner.nextInt();
                    System.out.println("Recorrido en profundidad (DFS):");
                    grafo.dfs(inicioDFS);
                    break;
                case 0:
                    System.out.println("Saliendo del programa.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }
}
