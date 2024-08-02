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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import una.cr.ac.model.Img;
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
        board.initializeLevel(4);

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

        // Configurar las columnas y filas
        for (int i = 0; i < board.getWidth(); i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(40); // Tamaño de la imagen
            gridPane.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < board.getHeight(); i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(40); // Tamaño de la imagen
            gridPane.getRowConstraints().add(rowConstraints);
        }

        // Dibujar el tablero usando imágenes
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                char cell = board.getCell(j, i);
                String imagePath = "";

                switch (cell) {
                    case '#':
                        imagePath = "/una/cr/ac/sokoban/resources/wall.png";
                        break;
                    case '.':
                        imagePath = "/una/cr/ac/sokoban/resources/point.png";
                        break;
                    case '@':
                        imagePath = "/una/cr/ac/sokoban/resources/foto1.png";
                        break;
                    case '$':
                        imagePath = "/una/cr/ac/sokoban/resources/box.png";
                        break;
                    case '!':
                        imagePath = "/una/cr/ac/sokoban/resources/boxPoint.png";
                        break;
                    case ' ':
                        imagePath = "/una/cr/ac/sokoban/resources/floor.png";
                        break;
                    default:
                        continue; // Si no hay imagen para este carácter, continuar
                }

                Image image = new Image(getClass().getResourceAsStream(imagePath), 40, 40, false, true);
                if (image.isError()) {
                    System.out.println("Error al cargar la imagen: " + image.getException());
                } else {
                    ImageView imageView = new ImageView(image);
                    imageView.setX(j * 40);
                    imageView.setY(i * 40);
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(40);
                    gridPane.add(imageView, j, i);
                }
            }
        }
    }

}
