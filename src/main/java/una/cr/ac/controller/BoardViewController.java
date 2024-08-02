package una.cr.ac.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private Label lbnivel;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnReiniciar;
    @FXML
    private Button btnsalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        System.out.println("Inicializando el controlador...");
        board = new LinkedList(20, 10);
        board.initializeLevel(1); 
        player = new Player(board);
        drawBoard();
        System.out.println(board);

       
        btnUp.setOnAction(this::moveUp);
        btnDown.setOnAction(this::moveDown);
        btnLeft.setOnAction(this::moveLeft);
        btnRight.setOnAction(this::moveRight);

      
        gridPane.setFocusTraversable(true);
        gridPane.setOnKeyPressed(this::handleKeyPress);
        gridPane.requestFocus(); 
    }

      @FXML
    private void moveUp(ActionEvent event) {
        movePlayer(player.getX(), player.getY() - 1);
    }

    @FXML
    private void moveDown(ActionEvent event) {
        movePlayer(player.getX(), player.getY() + 1);
    }

    @FXML
    private void moveLeft(ActionEvent event) {
        movePlayer(player.getX() - 1, player.getY());
    }

    @FXML
    private void moveRight(ActionEvent event) {
        movePlayer(player.getX() + 1, player.getY());
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                movePlayer(player.getX(), player.getY() - 1);
                break;
            case S:
                movePlayer(player.getX(), player.getY() + 1);
                break;
            case A:
                movePlayer(player.getX() - 1, player.getY());
                System.out.println("Solo llego una vez");
                break;
            case D:
                movePlayer(player.getX() + 1, player.getY());
                break;
            default:
                break;
        }
    }

    private void movePlayer(int newX, int newY) {
        if (isValidMove(newX, newY)) {
            player.movePlayer(newX, newY);
            drawBoard();
            System.out.println(board);
        } else {
            System.out.println("Movimiento no válido: (" + newX + ", " + newY + ")");
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight();
    }

    private void drawBoard() {
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < board.getWidth(); i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(30);
            gridPane.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < board.getHeight(); i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(30);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                char cell = board.getCell(j, i);
                Text text = new Text(String.valueOf(cell));
                text.setFont(Font.font(20));
                GridPane.setHalignment(text, HPos.CENTER);
                GridPane.setValignment(text, VPos.CENTER);
                gridPane.add(text, j, i);
            }
        }
    }
}
