package una.cr.ac.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una lista enlazada bidimensional para un tablero de
 * juego.
 */
public class LinkedList {

    private final int width;
    private final int height;
    private final Node head;

    public LinkedList(int width, int height) {
        this.width = width;
        this.height = height;
        this.head = new Node(' ');

        Node currentRowHead = head;
        for (int i = 0; i < height; i++) {
            Node currentNode = currentRowHead;
            for (int j = 1; j < width; j++) {
                currentNode.setNextColumn(new Node(' '));
                currentNode = currentNode.getNextColumn();
            }
            if (i < height - 1) {
                currentRowHead.setNextRow(new Node(' '));
                currentRowHead = currentRowHead.getNextRow();
            }
        }
    }

    public Node getHead() {
        return head;
    }

    public Node getNode(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null; // Asegúrate de manejar índices fuera de rango
        }

        Node currentNode = head;
        for (int i = 0; i < y; i++) {
            if (currentNode != null) {
                currentNode = currentNode.getNextRow();
            } else {
                return null;
            }
        }

        for (int j = 0; j < x; j++) {
            if (currentNode != null) {
                currentNode = currentNode.getNextColumn();
            } else {
                return null;
            }
        }

        return currentNode;
    }

    public void setCell(int x, int y, char value) {
        Node node = getNode(x, y);
        if (node != null) {
            node.setValue(value);
        } else {
            System.out.println("Error: Intento de acceder a un nodo null en (" + x + ", " + y + ")");
        }
    }

    public char getCell(int x, int y) {
        return getNode(x, y).getValue();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void initializeLevel(int level, boolean cargarPartida) {
        LevelLoader levelLoader = new LevelLoader();
        String[] levelData;

        // Si estamos cargando una partida guardada, lee desde el archivo de guardado
        if (cargarPartida) {
            GuardarPartida partida = ManejoDeDatos.leePartida();
            if (partida != null && partida.getNivel() == level) {
                levelData = partida.getLevelData();  // Usar los datos del archivo de guardado
            } else {
                throw new IllegalArgumentException("Error al cargar la partida guardada.");
            }
        } else {
            // Si no, carga el nivel desde el archivo correspondiente
            String directoryPath = "src/main/java/una/cr/ac/levels";
            levelData = levelLoader.loadLevelFromDirectory(directoryPath, level);
        }

        // Validar y configurar el tablero con los datos cargados
        if (levelData == null || levelData.length == 0) {
            throw new IllegalArgumentException("Nivel no válido o archivo vacío: " + level);
        }

        System.out.println("Datos del nivel cargado:");
        for (int y = 0; y < levelData.length; y++) {
            String row = levelData[y];
            if (row.length() < this.width) {
                row = String.format("%-" + this.width + "s", row);  // Rellenar con espacios a la derecha
            }
            System.out.println(row + " (longitud: " + row.length() + ")");
            for (int x = 0; x < row.length(); x++) {
                setCell(x, y, row.charAt(x));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            Node currentNode = getNode(0, i);
            for (int j = 0; j < width; j++) {
                sb.append(currentNode.getValue());
                currentNode = currentNode.getNextColumn();
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public void loadFromSavedData(String[] savedData) {
        for (int y = 0; y < savedData.length; y++) {
            String row = savedData[y];
            for (int x = 0; x < row.length(); x++) {
                setCell(x, y, row.charAt(x));
            }
        }
    }

}
