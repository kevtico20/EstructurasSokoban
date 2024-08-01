/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package una.cr.ac.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Kevin
 */
public class Player {

    private final LinkedList board;
    private int x;
    private int y;

    // Guardar posiciones de los puntos en un conjunto
    private final Set<Point> points = new HashSet<>();

    public Player(LinkedList board) {
        this.board = board;
        // Encuentra la posición inicial del jugador
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                char cell = board.getCell(j, i);
                if (cell == '@') {
                    this.x = j;
                    this.y = i;
                } else if (cell == '.') {
                    points.add(new Point(j, i));
                }
            }
        }
        if (this.x == -1 || this.y == -1) {
            throw new IllegalStateException("El tablero no contiene un jugador '@'");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void movePlayer(int newX, int newY) {
        // Verificar que las nuevas coordenadas están dentro del rango del tablero
        if (newX >= 0 && newX < board.getWidth() && newY >= 0 && newY < board.getHeight()) {
            System.out.println("Intentando mover a: (" + newX + ", " + newY + ")");

            if (board.getCell(newX, newY) != '#') { // No mover si hay un muro
                char currentCell = board.getCell(newX, newY);
                System.out.println("Celda actual: " + currentCell);

                if (currentCell == ' ' || currentCell == '.') {
                    // Limpiar la posición anterior del jugador
                    if (board.getCell(x, y) == '@' || board.getCell(x, y) == '!') {
                        // Restaurar el punto original si el jugador estaba en un punto
                        if (points.contains(new Point(x, y))) {
                            board.setCell(x, y, '.'); // Restaurar el punto si estaba en la posición original
                        } else {
                            board.setCell(x, y, ' '); // Limpiar la posición anterior
                        }
                    } else if (board.getCell(x, y) == '!') {
                        board.setCell(x, y, '.'); // Restaurar el punto original si el jugador estaba en un punto
                    }

                    x = newX;
                    y = newY;
                    board.setCell(x, y, '@'); // Mover al jugador a la nueva posición
                    System.out.println("Movimiento exitoso");
                    printBoard(); // Mostrar el tablero después de mover el jugador
                } else if (currentCell == '$'  || currentCell == '!') {
                    // Movimiento de caja
                    int nextX = newX + (newX - x);
                    int nextY = newY + (newY - y);

                    System.out.println("Intentando mover la caja a: (" + nextX + ", " + nextY + ")");

                    if (nextX >= 0 && nextX < board.getWidth() && nextY >= 0 && nextY < board.getHeight()) {
                        char nextCell = board.getCell(nextX, nextY);
                        System.out.println("Celda de destino para la caja: " + nextCell);

                        if (nextCell == ' ' || nextCell == '.') {
                            // Restaurar el estado de la celda original de la caja
                            if (points.contains(new Point(x, y))) {
                                board.setCell(x, y, '.'); // Restaurar el punto si era un destino
                            } else {
                                board.setCell(x, y, ' '); // Limpiar la posición anterior
                            }

                            // Mover la caja
                            if (nextCell == '.') {
                                board.setCell(nextX, nextY, '!'); // Caja en el punto de destino
                            } else {
                                board.setCell(nextX, nextY, '$'); // Mover la caja a una celda vacía
                            }

                            // Mover al jugador
                            board.setCell(newX, newY, '@'); // Mover el jugador
                            x = newX;
                            y = newY;
                            System.out.println("Movimiento con caja exitoso");
                            printBoard(); // Mostrar el tablero después de mover la caja
                        } else {
                            System.out.println("Movimiento con caja fallido: la celda de destino está ocupada por " + nextCell);
                        }
                    } else {
                        System.out.println("Movimiento con caja fallido: fuera de límites");
                    }
                }
            } else {
                System.out.println("Movimiento fallido: hay un muro");
            }
        } else {
            System.out.println("Movimiento fallido: fuera de límites");
        }
    }

    private void printBoard() {
        System.out.println("Tablero actual:");
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                System.out.print(board.getCell(j, i) + " ");
            }
            System.out.println();
        }
    }

    // Clase para manejar las posiciones de los puntos
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
}
