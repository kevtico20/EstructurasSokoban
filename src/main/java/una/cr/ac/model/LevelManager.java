/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package una.cr.ac.model;

import java.util.Stack;

/**
 *
 * @author Kevin
 */
public class LevelManager {
    private LinkedList board;
    private Player player;
    private Stack<Point> stack;
    private int currentLevel;

    public LevelManager() {
        this.currentLevel = 1; // Inicializamos en el nivel 1
        this.stack = new Stack<>();
        loadLevel(currentLevel);
    }

    public void loadLevel(int level) {
        board = new LinkedList(20, 10);
        board.initializeLevel(level);
        player = new Player(board);
        stack.clear(); // Limpiar la pila al cargar un nuevo nivel
    }

    public void movePlayer(int newX, int newY) {
        player.movePlayer(newX, newY);
        checkBoxPosition(newX, newY);
    }

    private void checkBoxPosition(int x, int y) {
        if (board.getCell(x, y) == '!') {
            Point point = new Point(x, y);
            if (!stack.contains(point)) {
                stack.push(point);
            }
        }
        if (stack.size() == getNumberOfBoxes()) {
            advanceLevel();
        }
    }

    private int getNumberOfBoxes() {
        int count = 0;
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getCell(j, i) == '$') {
                    count++;
                }
            }
        }
        return count;
    }

    private void advanceLevel() {
        currentLevel++;
        loadLevel(currentLevel);
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
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
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
    }
    
}