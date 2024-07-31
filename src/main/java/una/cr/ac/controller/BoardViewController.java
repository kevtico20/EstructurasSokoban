package una.cr.ac.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import una.cr.ac.model.LinkedList;
import una.cr.ac.model.Player;

/**
 * FXML Controller class
 *
 * @author Kevin
 */
public class BoardViewController extends Controller implements Initializable {

    @FXML
    private JFXButton btnUp;
    @FXML
    private JFXButton btnDown;
    @FXML
    private JFXButton btnLeft;
    @FXML
    private JFXButton btnRight;
    @FXML
    private GridPane gridPane;

    private LinkedList board;
    private Player player;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void moveUp(ActionEvent event) {
        player.movePlayer(player.getX(), player.getY() - 1);
        drawBoard();
        System.out.println(board); // Imprime el tablero después de mover
    }

    @FXML
    private void moveDown(ActionEvent event) {
        player.movePlayer(player.getX(), player.getY() + 1);
        drawBoard();
        System.out.println(board); // Imprime el tablero después de mover
    }

    @FXML
    private void moveLeft(ActionEvent event) {
        player.movePlayer(player.getX() - 1, player.getY());
        drawBoard();
        System.out.println(board); // Imprime el tablero después de mover
    }

    @FXML
    private void moveRight(ActionEvent event) {
        player.movePlayer(player.getX() + 1, player.getY());
        drawBoard();
        System.out.println(board); // Imprime el tablero después de mover
    }

    @Override
    public void initialize() {
        System.out.println("Inicializando el controlador...");
        board = new LinkedList(20, 10);
        board.initializeLevel(1); // Inicializa el primer nivel
        player = new Player(board);
        drawBoard();
        System.out.println(board);
    }

    private void drawBoard() {
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < board.getWidth(); i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(30); // Ajustar el tamaño de la columna
            gridPane.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < board.getHeight(); i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(30); // Ajustar el tamaño de la fila
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                char cell = board.getCell(j, i);
                Text text = new Text(String.valueOf(cell));
                text.setFont(Font.font(20));
                GridPane.setHalignment(text, HPos.CENTER); // Centrar el texto horizontalmente
                GridPane.setValignment(text, VPos.CENTER); // Centrar el texto verticalmente
                gridPane.add(text, j, i);
            }
        }
    }
}
