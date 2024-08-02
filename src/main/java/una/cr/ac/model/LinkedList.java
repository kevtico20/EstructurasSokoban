package una.cr.ac.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

        // Crear el grid de listas enlazadas
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
        Node currentNode = head;
        for (int i = 0; i < y; i++) {
            currentNode = currentNode.getNextRow();
        }
        for (int j = 0; j < x; j++) {
            currentNode = currentNode.getNextColumn();
        }
        return currentNode;
    }

    public void setCell(int x, int y, char value) {
        Node node = getNode(x, y);
        node.setValue(value);
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

    public void initializeLevel(int level) {
        String[] levelData;

        switch (level) {
            case 1:
                levelData = readLevel("src/main/java/una/cr/ac/levels/Level1.txt");
                break;
            case 2:
                levelData = new String[]{
                    "#####",
                    "#   #",
                    "# @ #",
                    "#$ .#",
                    "#####"
                };
                break;
            case 3:
                levelData = new String[]{
                    "######",
                    "#        #",
                    "# ## #",
                    "#@ $.#",
                    "# .  #",
                    "######"
                };
                break;
            case 4:
                levelData = readLevel("src/main/java/una/cr/ac/levels/Level4.txt");;
                break;
            case 5:
                levelData = readLevel("src/main/java/una/cr/ac/levels/Level5.txt");;
                break;
            default:
                throw new IllegalArgumentException("Nivel no válido: " + level);
        }

        for (int y = 0; y < levelData.length; y++) {
            String row = levelData[y];
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
    
     public static String[] readLevel(String file) {
        String[] lineList = new String[10];

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int i=0;
            while ((line = br.readLine()) != null && i<10) {
                
                lineList[i]=line;
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return lineList;
    }



}
