/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package una.cr.ac.model;

/**
 *
 * @author Kevin
 */
public class Player {
     private final LinkedList board;
    private int x;
    private int y;

    public Player(LinkedList board) {
        this.board = board;
        // Encuentra la posición inicial del jugador
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getCell(j, i) == '@') {
                    this.x = j;
                    this.y = i;
                    return;
                }
            }
        }
        throw new IllegalStateException("El tablero no contiene un jugador '@'");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void movePlayer(int newX, int newY) {
        if (board.getCell(newX, newY) != '#') { // No mover si hay un muro
            char currentCell = board.getCell(newX, newY);

            if (currentCell == ' ' || currentCell == '.') {
                board.setCell(x, y, ' '); // Limpiar la posición anterior
                x = newX;
                y = newY;
                board.setCell(x, y, '@'); // Mover al jugador a la nueva posición
            } else if (currentCell == '$') {
                int nextX = newX + (newX - x);
                int nextY = newY + (newY - y);
                if (board.getCell(nextX, nextY) == ' ' || board.getCell(nextX, nextY) == '.') {
                    board.setCell(newX, newY, '@');
                    board.setCell(nextX, nextY, '$');
                    board.setCell(x, y, ' ');
                    x = newX;
                    y = newY;
                }
            }
        }
    }
}