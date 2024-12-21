import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.*;

public class Arbol {
    private Nodo raiz;
    private ArrayList<Nodo> nodos; // Lista de nodos para un fácil acceso.
    private int numNodos; // Contador de nodos para asignar etiquetas.

    public Arbol() {
        raiz = null; // Inicialmente, el árbol está vacío.
        nodos = new ArrayList<>(); // Inicialización de la lista de nodos.
        numNodos = 0; // Contador de nodos inicializado a cero.
    }

    public void anadirNodo(Nodo nodo, Nodo padre, String posicion) {
        if (padre == null) {
            if (raiz == null) {
                raiz= nodo; // El primer nodo agregado se convierte en la raíz.
            } else {
                throw new IllegalArgumentException("La raíz ya existe");
            }
        } else {
            //Se verifica la posición específica.
            if (posicion.equalsIgnoreCase("izquierda1")) {
                if (padre.izquierda1 == null) {
                    padre.izquierda1 = nodo;
                } else {
                    throw new IllegalArgumentException("Hoja Izq ya existe");
                }
            } else if (posicion.equalsIgnoreCase("izquierda2")) {
                if (padre.izquierda2 == null) {
                    padre.izquierda2 = nodo;
                } else {
                    throw new IllegalArgumentException("Hoja Izq ya existe");
                }
            } else if (posicion.equalsIgnoreCase("centro")) {
            if (padre.centro == null) {
                padre.centro = nodo;
            } else {
                throw new IllegalArgumentException("Hoja Cen ya existe");
            }
            } else if (posicion.equalsIgnoreCase("derecha1")) {
                if (padre.derecha1 == null) {
                    padre.derecha1 = nodo;
                } else {
                    throw new IllegalArgumentException("Hoja Der ya existe");
                }
            } else if (posicion.equalsIgnoreCase("derecha2")) {
                if (padre.derecha2 == null) {
                    padre.derecha2 = nodo;
                } else {
                    throw new IllegalArgumentException("Hoja Der ya existe");
                }
            } else {
                throw new IllegalArgumentException("Posición inválida: " + posicion);
            }

        }
        nodos.add(nodo); // Agrega el nodo a la lista de nodos.
    }

    public ArrayList<Nodo> getNodos() {
            return nodos; // Devuelve todos los nodos del árbol.
    }

    public Nodo getRaiz() {
        return raiz; // Devuelve la raíz del árbol.
    }

    public String getEtiquetaNodoSiguiente() {
        return String.valueOf((char) ('A' + numNodos++));
    }

    public String bfs() {
        if (raiz== null) return "";

        StringBuilder resultado = new StringBuilder();
        Queue<Nodo> queue = new LinkedList<>();
        queue.add(raiz);

        while (!queue.isEmpty()) {
            Nodo nodo = queue.poll();
            resultado.append(nodo.etiqueta).append(" ");
            // se agregan todas las ramas del árbol quintinario.
            if (nodo.izquierda1 != null) queue.add(nodo.izquierda1 );
            if (nodo.izquierda2 != null) queue.add(nodo.izquierda2 );
            if (nodo.centro != null) queue.add(nodo.centro);
            if (nodo.derecha1 != null) queue.add(nodo.derecha1 );
            if (nodo.derecha2 != null) queue.add(nodo.derecha2 );
        }

        return resultado.toString().trim();
    }

    public String dfs() {
        if (raiz== null) return ""; // Si el árbol está vacío, devuelve una cadena vacía.

        StringBuilder resultado = new StringBuilder();
        Stack<Nodo> stack = new Stack<>();
        stack.push(raiz);

        while (!stack.isEmpty()) {
            Nodo nodo = stack.pop();
            resultado.append(nodo.etiqueta).append(" ");
            // Se agregan todas las ramas del árbol quintinario.
            if (nodo.derecha2 != null) stack.push(nodo.derecha2);
            if (nodo.derecha1 != null) stack.push(nodo.derecha1);
            if (nodo.centro != null) stack.push(nodo.centro);
            if (nodo.izquierda2 != null) stack.push(nodo.izquierda2);
            if (nodo.izquierda1 != null) stack.push(nodo.izquierda1);
        }

        return resultado.toString().trim();
    }

    /**
     * Recorrido preorden.
     */
    public String preorden() {
        return preordenImpresion(raiz).trim();
    }

    private String preordenImpresion(Nodo nodo) {
        if (nodo == null) return "";
        return nodo.etiqueta + " " +
                preordenImpresion(nodo.izquierda1) +
                preordenImpresion(nodo.izquierda2) +
                preordenImpresion(nodo.centro) +
                preordenImpresion(nodo.derecha1) +
                preordenImpresion(nodo.derecha2);
    }

    /**
     * Recorrido inorden.
     */
    public String inorden() {
        return inordenImpresion(raiz).trim();
    }

    private String inordenImpresion(Nodo nodo) {
        if (nodo == null) return "";
        return inordenImpresion(nodo.izquierda1) +
                inordenImpresion(nodo.izquierda2) +
                nodo.etiqueta + " " +
                inordenImpresion(nodo.centro) +
                inordenImpresion(nodo.derecha1) +
                inordenImpresion(nodo.derecha2);
    }

    /**
     * Recorrido postorden.
     */

    public String postorden() {
        return postordenImpresion(raiz).trim();
    }

    private String postordenImpresion(Nodo nodo) {
        if (nodo == null) return "";
        return postordenImpresion(nodo.izquierda1) +
                postordenImpresion(nodo.izquierda2) +
                postordenImpresion(nodo.centro) +
                postordenImpresion(nodo.derecha1) +
                postordenImpresion(nodo.derecha2) +
                nodo.etiqueta + " ";
    }

    /**
     * Genera la matriz de adyacencia del árbol.
     */

    public Object[][] getMatrizAdyacencia() {
        int tam = nodos.size();
        Object[][] matriz = new Object[tam][tam];
        Map<String, Integer> etiquetaAIndice = new HashMap<>();

        for (int i = 0; i < tam; i++) {
            etiquetaAIndice.put(nodos.get(i).etiqueta, i);
            for (int j = 0; j < tam; j++) {
                matriz[i][j] = 0;
            }
        }

        for (Nodo nodo : nodos) {
            int desdeIndice = etiquetaAIndice.get(nodo.etiqueta);
            if (nodo.izquierda1 != null) {
                int hastaIndice = etiquetaAIndice.get(nodo.izquierda1.etiqueta);
                matriz[desdeIndice][hastaIndice] = 1;
            }
            if (nodo.izquierda2 != null) {
                int hastaIndice = etiquetaAIndice.get(nodo.izquierda2.etiqueta);
                matriz[desdeIndice][hastaIndice] = 1;
            }
            if (nodo.centro != null) {
                int hastaIndice = etiquetaAIndice.get(nodo.centro.etiqueta);
                matriz[desdeIndice][hastaIndice] = 1;
            }
            if (nodo.derecha1 != null) {
                int hastaIndice = etiquetaAIndice.get(nodo.derecha1.etiqueta);
                matriz[desdeIndice][hastaIndice] = 1;
            }
            if (nodo.derecha2 != null) {
                int hastaIndice = etiquetaAIndice.get(nodo.derecha2.etiqueta);
                matriz[desdeIndice][hastaIndice] = 1;
            }
        }

        return matriz;
    }
}
