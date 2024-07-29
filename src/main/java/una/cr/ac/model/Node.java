/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package una.cr.ac.model;

/**
 *
 * @author Kevin
 */
public class Node {

    char value;
    Node nextRow;
    Node nextColumn;

    public Node(char value) {
        this.value = value;
       
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public Node getNextRow() {
        return nextRow;
    }

    public void setNextRow(Node nextRow) {
        this.nextRow = nextRow;
    }

    public Node getNextColumn() {
        return nextColumn;
    }

    public void setNextColumn(Node nextColumn) {
        this.nextColumn = nextColumn;
    }
}
