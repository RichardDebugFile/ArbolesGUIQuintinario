import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ArbolGrafico extends JPanel {
    private Arbol arbol;

    public ArbolGrafico(Arbol arbol) {
        this.arbol = arbol;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Obtener el tamaño del panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        //posición inicial (centrado)
        int x = panelWidth / 2;
        int y = 50;

        // Dimensiones para los nodos hijos
        int dimensionX = panelWidth / 4;
        int dimensionY = 50;

        // Dibujar el árbol
        dibujarArbol(g2d, arbol.getRaiz(), x, y, dimensionX, dimensionY);
    }

    public void dibujarArbol(Graphics2D g2d, Nodo nodo, int x, int y, int dimensionX, int dimensionY) {
        if (nodo != null) {
            // Dibuja el nodo actual
            g2d.fillOval(x - 15, y - 15, 30, 30);
            g2d.drawString(nodo.etiqueta, x - 10, y + 5);

            // Asigna las coordenadas del nodo actual
            nodo.x = x;
            nodo.y = y;

            // Dibujar líneas y nodos hijos - Las hojas esas
            int offset = dimensionX / 2; // Espaciado

            // Nodo izquierda1
            if (nodo.izquierda1 != null) {
                g2d.drawLine(x, y, x - dimensionX, y + dimensionY);
                dibujarArbol(g2d, nodo.izquierda1, x - dimensionX, y + dimensionY, offset, dimensionY);
            }

            // Nodo izquierda2
            if (nodo.izquierda2 != null) {
                g2d.drawLine(x, y, x - offset, y + dimensionY);
                dibujarArbol(g2d, nodo.izquierda2, x - offset, y + dimensionY, offset / 2, dimensionY);
            }

            // Nodo centro
            if (nodo.centro != null) {
                g2d.drawLine(x, y, x, y + dimensionY);
                dibujarArbol(g2d, nodo.centro, x, y + dimensionY, offset, dimensionY);
            }

            // Nodo derecha1
            if (nodo.derecha1 != null) {
                g2d.drawLine(x, y, x + offset, y + dimensionY);
                dibujarArbol(g2d, nodo.derecha1, x + offset, y + dimensionY, offset / 2, dimensionY);
            }

            // Nodo derecha2
            if (nodo.derecha2 != null) {
                g2d.drawLine(x, y, x + dimensionX, y + dimensionY);
                dibujarArbol(g2d, nodo.derecha2, x + dimensionX, y + dimensionY, offset, dimensionY);
            }
        }
    }
}
