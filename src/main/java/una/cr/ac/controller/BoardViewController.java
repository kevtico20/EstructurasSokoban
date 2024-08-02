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
import una.cr.ac.model.LevelManager;
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

    private int currentLevel;

    private LevelManager levelManager;

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
        levelManager = new LevelManager();
        drawBoard();
        updateLevelLabel();
        System.out.println(levelManager.getBoard());

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
        movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() - 1);
    }

    @FXML
    private void moveDown(ActionEvent event) {
        movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() + 1);
    }

    @FXML
    private void moveLeft(ActionEvent event) {
        movePlayer(levelManager.getPlayer().getX() - 1, levelManager.getPlayer().getY());
    }

    @FXML
    private void moveRight(ActionEvent event) {
        movePlayer(levelManager.getPlayer().getX() + 1, levelManager.getPlayer().getY());
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() - 1);
                break;
            case S:
                movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() + 1);
                break;
            case A:
                movePlayer(levelManager.getPlayer().getX() - 1, levelManager.getPlayer().getY());
                break;
            case D:
                movePlayer(levelManager.getPlayer().getX() + 1, levelManager.getPlayer().getY());
                break;
            default:
                break;
        }
    }

    private void movePlayer(int newX, int newY) {
        if (isValidMove(newX, newY)) {
            levelManager.movePlayer(newX, newY);
            drawBoard();
            System.out.println(levelManager.getBoard());
        } else {
            System.out.println("Movimiento no válido: (" + newX + ", " + newY + ")");
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < levelManager.getBoard().getWidth() && y >= 0 && y < levelManager.getBoard().getHeight();
    }

    private void drawBoard() {
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        // Configurar las columnas y filas
        for (int i = 0; i < levelManager.getBoard().getWidth(); i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(40); // Tamaño de la imagen
            gridPane.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < levelManager.getBoard().getHeight(); i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(40); // Tamaño de la imagen
            gridPane.getRowConstraints().add(rowConstraints);
        }

        // Dibujar el tablero usando imágenes
        for (int i = 0; i < levelManager.getBoard().getHeight(); i++) {
            for (int j = 0; j < levelManager.getBoard().getWidth(); j++) {
                char cell = levelManager.getBoard().getCell(j, i);
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

        updateLevelLabel();
    }

    @FXML
    private void onActionReset(ActionEvent event) {
        gridPane.setFocusTraversable(true);
        gridPane.setOnKeyPressed(this::handleKeyPress);
        gridPane.requestFocus();
        levelManager.loadLevel(levelManager.getCurrentLevel());
        drawBoard();
    }

    private void updateLevelLabel() {
        lbnivel.setText("Nivel: " + levelManager.getCurrentLevel());
    }

}
