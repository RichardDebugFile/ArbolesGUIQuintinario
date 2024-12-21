import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class ArbolGUI {
    private JTable tbMatrizAdyacencia;
    private JTextArea textArea;
    private JTextField txtRaiz;
    private JButton btnAgregarNodo;
    private JButton btnDibujarArbol;
    private JButton btnRecorridoAnchura;
    private JButton btnRecorridoProfundidad;
    private JButton btnPreorden;
    private JButton btnInorden;
    private JLabel lblNodo;
    private JLabel lblRaiz;
    private JLabel lblHoja;
    private JComboBox<String> posicionComboBox;
    private JPanel panelArbol;
    private JPanel panelGeneral;
    private JPanel panelDatos;
    private JLabel lblRecorridos;
    private JButton btnPostorden;
    private JButton btnMatrizAdyacencia;
    private JComboBox cbIzqDer;

    private Arbol arbol = new Arbol();
    private ArbolGrafico arbolGrafico = new ArbolGrafico(arbol);
    private DefaultTableModel modeloTabla = new DefaultTableModel();

    public ArbolGUI() {

        btnAgregarNodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Generar etiqueta para el nuevo nodo.
                    String etiqueta = arbol.getEtiquetaNodoSiguiente();
                    Nodo nuevoNodo = new Nodo(0, 0, etiqueta);
                    String etiquetaPadre = txtRaiz.getText().trim();
                    String posicion = (String) cbIzqDer.getSelectedItem(); // Obtener posición seleccionada del ComboBox.

                    if (arbol.getRaiz() == null) {
                        // Si el árbol está vacío, este nodo será la raíz
                        arbol.anadirNodo(nuevoNodo, null, posicion);
                        JOptionPane.showMessageDialog(null, "Nodo raíz añadido: " + etiqueta);

                        // Actualizar el campo txtRaiz con la etiqueta del nodo raíz
                        txtRaiz.setText(etiqueta);
                    } else {
                        // Buscar el nodo padre
                        Nodo nodoPadre = null;
                        for (Nodo nodo : arbol.getNodos()) {
                            if (nodo.etiqueta.equals(etiquetaPadre)) {
                                nodoPadre = nodo;
                                break;
                            }
                        }

                        // Si no se encuentra el nodo padre, se lanza un error.
                        if (nodoPadre == null) {
                            throw new IllegalArgumentException("El nodo padre no existe");
                        }

                        arbol.anadirNodo(nuevoNodo, nodoPadre, posicion);
                    }

                    imprimirArbol();
                    dibujarArbolEnPanel();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar nodo: " + ex.getMessage());
                }
            }
        });



        btnRecorridoAnchura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = arbol.bfs();
                textArea.append("Recorrido en Anchura (BFS): " + resultado + "\n");
            }
        });

        btnRecorridoProfundidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = arbol.dfs();
                textArea.append("Recorrido en Profundidad (DFS): " + resultado + "\n");
            }
        });

        btnPreorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = arbol.preorden();
                textArea.append("Preorden: " + resultado + "\n");
            }
        });

        btnInorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = arbol.inorden();
                textArea.append("Inorden: " + resultado + "\n");
            }
        });

        btnPostorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultado = arbol.postorden();
                textArea.append("Postorden: " + resultado + "\n");
            }
        });

        btnMatrizAdyacencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMatrizAdyacencia();
            }
        });

        btnDibujarArbol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dibujarArbolEnPanel();
            }
        });
    }

    /**
     * Metodo para dibujar el árbol en el panel.
     */
    private void dibujarArbolEnPanel() {
        Graphics g = panelArbol.getGraphics();

        if (g != null) {
            Graphics2D g2d = (Graphics2D) g;
            int panelWidth = panelArbol.getWidth();
            int panelHeight = panelArbol.getHeight();

            int x = panelWidth / 2;
            int y = 50;
            int dimensionX = panelWidth / 4;
            int dimensionY = 50;

            arbolGrafico.dibujarArbol(g2d, arbol.getRaiz(), x, y, dimensionX, dimensionY);
        }
    }

    /**
     * Metodo para imprimir información de los nodos en el área de texto.
     */

    private void imprimirArbol() {
        textArea.setText("");
        textArea.append("Nodos:\n");
        for (Nodo nodo : arbol.getNodos()) {
            textArea.append(nodo.etiqueta + ": " + nodo.toString() + "\n");
        }
    }

    /**
     * Metodo para mostrar la matriz de adyacencia en la tabla.
     */

    private void mostrarMatrizAdyacencia() {
        Object[][] matriz = arbol.getMatrizAdyacencia();
        String[] nombreColumnas = new String[matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            nombreColumnas[i] = String.valueOf((char) ('A' + i));
        }

        modeloTabla.setDataVector(matriz, nombreColumnas);
        tbMatrizAdyacencia.setModel(modeloTabla);
    }

    /**
     * Metodo principal para ejecutar la aplicación.
     */

    public static void main(String[] args) {
        JFrame frame = new JFrame("ArbolGUI");
        frame.setContentPane(new ArbolGUI().panelGeneral);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
