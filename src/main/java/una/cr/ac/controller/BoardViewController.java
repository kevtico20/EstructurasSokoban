package una.cr.ac.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.Vector;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import una.cr.ac.model.GuardarPartida;
import una.cr.ac.model.Img;
import una.cr.ac.model.LevelManager;
import una.cr.ac.model.LinkedList;
import una.cr.ac.model.Player;
import una.cr.ac.util.FlowController;
import una.cr.ac.util.ManejoDatos;

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

    int direction = 0;
    int count = 0;
    
    private Vector<String> movimientos = new Vector<>();

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
        direction = 1;
        movimientos.add("up");
        movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() - 1);
        

    }

    @FXML
    private void moveDown(ActionEvent event) {
        direction= 0;
        movimientos.add("down");
        movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() + 1);
        

    }

    @FXML
    private void moveLeft(ActionEvent event) {
        direction=2;
        movimientos.add("left");
        movePlayer(levelManager.getPlayer().getX() - 1, levelManager.getPlayer().getY());
        

    }

    @FXML
    private void moveRight(ActionEvent event) {
        direction=3;
        movimientos.add("right");
        movePlayer(levelManager.getPlayer().getX() + 1, levelManager.getPlayer().getY());

    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                direction=1;
                movimientos.add("up");
                movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() - 1);
                break;
            case S:
                direction=0;
                movimientos.add("down");
                movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() + 1);
                break;
            case A:
                direction=2;
                movimientos.add("left");
                movePlayer(levelManager.getPlayer().getX() - 1, levelManager.getPlayer().getY());
                break;
            case D:
                direction=3;
                movimientos.add("right");
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
             if(count==3){
                    count=0;
                } else{
                count++;
              }
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
                ImageView imageView = null;

                switch (cell) {
                    case '#':
                        imagePath = "/una/cr/ac/sokoban/resources/wall.png";
                        imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath), 40, 40, false, true));
                        break;
                    case '.':
                        imagePath = "/una/cr/ac/sokoban/resources/point.png";
                        imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath), 40, 40, false, true));
                        break;
                    case '@':
                        // Implementar animación para el personaje '@'
                        imageView = createAnimatedSprite();
                        break;
                    case '$':
                        imagePath = "/una/cr/ac/sokoban/resources/box.png";
                        imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath), 40, 40, false, true));
                        break;
                    case '!':
                        imagePath = "/una/cr/ac/sokoban/resources/boxPoint.png";
                        imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath), 40, 40, false, true));
                        break;
                    case ' ':
                        imagePath = "/una/cr/ac/sokoban/resources/floor.png";
                        imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath), 40, 40, false, true));
                        break;
                    default:
                        continue; // Si no hay imagen para este carácter, continuar
                }

                if (imageView != null) {
                    gridPane.add(imageView, j, i); // Agregar ImageView a GridPane en la posición (columna, fila)
                }
            }
        }

        updateLevelLabel();
    }

    private ImageView createAnimatedSprite() {
        // Configuración del sprite sheet
        Image spriteSheet = new Image(getClass().getResourceAsStream("/una/cr/ac/sokoban/resources/frames.png"));
        ImageView imageView = new ImageView(spriteSheet);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        // Variables para la animación
        final int[] currentFrame = {0};
        final int row = direction;
        final int WIDTH = 160;
        final int HEIGHT = 160;
        

        // Crear la animación
        currentFrame[0] = (currentFrame[0] + count) % 4;
        int x = currentFrame[0] * WIDTH;
        int y = row * HEIGHT;
        imageView.setViewport(new javafx.geometry.Rectangle2D(x, y, WIDTH, HEIGHT));

        return imageView;
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

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        GuardarPartida guardarPartida= new GuardarPartida();
        guardarPartida.setBoard(levelManager.getBoard());
        guardarPartida.setVector(movimientos);
        ManejoDatos.guardarPartida(guardarPartida);
        FlowController.getInstance().goViewInWindow("MenuView");
       ((Stage) btnGuardar.getScene().getWindow()).close();
    }

}
