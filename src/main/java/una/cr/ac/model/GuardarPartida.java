/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package una.cr.ac.model;

import java.util.Vector;

/**
 *
 * @author ArauzKJ
 */
public class GuardarPartida {

    LinkedList board;
    Vector<String> vector;

    public GuardarPartida(LinkedList board, Vector<String> vector) {
        this.board = board;
        this.vector = vector;
    }

    public GuardarPartida() {
    }

    public LinkedList getBoard() {
        return board;
    }

    public void setBoard(LinkedList board) {
        this.board = board;
    }

    public Vector<String> getVector() {
        return vector;
    }

    public void setVector(Vector<String> vector) {
        this.vector = vector;
    }
    
}
