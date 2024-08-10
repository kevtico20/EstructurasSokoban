/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package una.cr.ac.model;

/**
 *
 * @author Kevin
 */
import java.util.Stack;
import javafx.stage.Stage;
import una.cr.ac.util.FlowController;

public class LevelManager {

    private LinkedList board;
    private Player player;
    private Stack<Point> stack;
    private int currentLevel;
    private static final int TOTAL_LEVELS = 5;
    private Runnable onGameCompletedCallback;

    public static int getTOTAL_LEVELS() {
        return TOTAL_LEVELS;
    }

    public void setOnGameCompleted(Runnable callback) {
        this.onGameCompletedCallback = callback;
    }

    private void onGameCompleted() {
        if (onGameCompletedCallback != null) {
            onGameCompletedCallback.run();  // Notifica al controlador que el juego ha sido completado
        }
    }

    public LevelManager() {
        this.currentLevel = 1;
        this.stack = new Stack<>();
        loadLevel(currentLevel);
    }

    public void loadLevel(int level) {
        if (level <= TOTAL_LEVELS) {
            board = new LinkedList(20, 10);
            board.initializeLevel(level);
            player = new Player(board);
            stack.clear();
            updateStack();  // Inicializa el stack para detectar cajas en posiciones finales
            System.out.println("Cargando nivel " + level + ". Número de cajas: " + getNumberOfTargets());
        } else {
            onGameCompleted();
        }
    }

    private void updateStack() {
        // Recorre el tablero y agrega cajas que ya están en posiciones finales
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getCell(j, i) == '!') {
                    stack.push(new Point(j, i));
                }
            }
        }
        System.out.println("Stack inicializado. Tamaño del stack: " + stack.size());
    }

    private void checkBoxPosition(int x, int y) {
        char cell = board.getCell(x, y);
        Point point = new Point(x, y);

        // Solo añadir al stack si la celda contiene '!'
        if (cell == '!') {
            if (!stack.contains(point)) {
                stack.push(point);  // Añadir al stack solo si la caja está en la posición final
            }
        }

        // Si la celda no contiene una caja en posición final, eliminar del stack
        if (cell != '!') {
            stack.remove(point);
        }

        checkLevelCompletion();  // Verificar si el nivel está completo
    }

    private void checkLevelCompletion() {
        int objectiveCount = countObjectives();  // Contar cuántos objetivos hay en el nivel
        System.out.println("Verificando si el nivel está completo. Cajas en el stack: " + stack.size() + " / Objetivos: " + objectiveCount);

        if (stack.size() == objectiveCount && objectiveCount > 0) {
            advanceLevel();
        }
    }

    public void movePlayer(int newX, int newY) {
        if (isValidMove(newX, newY)) {
            player.movePlayer(newX, newY);

            // Solo verifica la posición de la caja si está en un objetivo
            if (board.getCell(newX, newY) == '!' || board.getCell(newX, newY) == '$') {
                checkBoxPosition(newX, newY);
            }

            System.out.println(board);  // Depuración opcional
        } else {
            System.out.println("Movimiento no válido: (" + newX + ", " + newY + ")");
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight() && board.getCell(x, y) != '#';
    }

    private int countObjectives() {
        int objectiveCount = 0;
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getCell(j, i) == '.') {
                    objectiveCount++;
                }
            }
        }
        System.out.println("Número de objetivos en el nivel: " + objectiveCount);
        return objectiveCount;
    }

    public boolean isLevelComplete() {
        int objectiveCount = countObjectives();
        System.out.println("Verificando si el nivel está completo. Cajas en el stack: " + stack.size() + " / Objetivos: " + objectiveCount);

        // Si no hay objetivos, considera el nivel como completado
        if (objectiveCount == 0) {
            System.out.println("No hay objetivos en este nivel, avanzando automáticamente.");
            return true;
        }

        // Si hay objetivos, avanza solo si todos están cubiertos
        return stack.size() == objectiveCount;
    }

    private int getNumberOfTargets() {
        int count = 0;
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getCell(j, i) == '!') {
                    count++;
                }
            }
        }
        return count;
    }

    public void advanceLevel() {
        if (currentLevel < TOTAL_LEVELS) {
            currentLevel++;
            loadLevel(currentLevel);
        } else {
            onGameCompleted();  // Llama al método cuando se completa el último nivel
        }
    }

    public LinkedList getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    private static class Point {

        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }

    public void setBoard(LinkedList board) {
        this.board = board;
        updateStack();  // Asegúrate de que el stack esté alineado con el nuevo tablero
    }
}
