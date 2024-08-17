package una.cr.ac.controller;

import com.jfoenix.controls.JFXButton;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
//import una.cr.ac.util.ManejoDatos;

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
    public GridPane gridPane;

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
        if (levelManager == null) {
            levelManager = new LevelManager();
        }
        drawBoard();
        updateLevelLabel();
//        System.out.println(levelManager.getBoard());

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
        direction = 0;
        movimientos.add("down");
        movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() + 1);

    }

    @FXML
    private void moveLeft(ActionEvent event) {
        direction = 2;
        movimientos.add("left");
        movePlayer(levelManager.getPlayer().getX() - 1, levelManager.getPlayer().getY());

    }

    @FXML
    private void moveRight(ActionEvent event) {
        direction = 3;
        movimientos.add("right");
        movePlayer(levelManager.getPlayer().getX() + 1, levelManager.getPlayer().getY());

    }

    public void cargarPartida(GuardarPartida partida) {
        if (partida == null) {
            throw new IllegalArgumentException("La partida guardada es nula.");
        }

        // Inicializar el LevelManager
        levelManager = new LevelManager(partida.getNivel(), true);

        // Crear y configurar el tablero basado en los datos guardados
        LinkedList board = new LinkedList(partida.getLevelData()[0].length(), partida.getLevelData().length);

        for (int i = 0; i < partida.getLevelData().length; i++) {
            String row = partida.getLevelData()[i];
            for (int j = 0; j < row.length(); j++) {
                board.setCell(j, i, row.charAt(j));
            }
        }

        // Verificación: Mostrar el estado del tablero después de configurarlo
        System.out.println("Tablero después de configurar desde la partida guardada:");
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                System.out.print(board.getCell(x, y));
            }
            System.out.println();
        }

        // Asignar el tablero al LevelManager
        levelManager.setBoard(board);
        // Redibujar el tablero en la interfaz gráfica
        drawBoard();
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                direction = 1;
                movimientos.add("up");
                movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() - 1);
                break;
            case S:
                direction = 0;
                movimientos.add("down");
                movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() + 1);
                break;
            case A:
                direction = 2;
                movimientos.add("left");
                movePlayer(levelManager.getPlayer().getX() - 1, levelManager.getPlayer().getY());
                break;
            case D:
                direction = 3;
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
            if (count == 3) {
                count = 0;
            } else {
                count++;
            }
            // Verifica si todas las cajas están en sus posiciones finales después del movimiento
            if (levelManager.isLevelComplete()) {

                if (levelManager.getCurrentLevel() < levelManager.getTOTAL_LEVELS()) {
                    levelManager.advanceLevel();  // Avanza al siguiente nivel si no es el último
                    drawBoard();  // Redibuja el tablero para el nuevo nivel
                } else {
                    onGameCompleted();  // Llama al método para completar el juego si es el último nivel
                }
            }

            System.out.println(levelManager.getBoard());
        } else {
            System.out.println("Movimiento no válido: (" + newX + ", " + newY + ")");
        }
    }

    private boolean isValidMove(int x, int y) {
        // Verifica si el movimiento está dentro de los límites y no es una pared
        return x >= 0 && x < levelManager.getBoard().getWidth() && y >= 0 && y < levelManager.getBoard().getHeight()
                && levelManager.getBoard().getCell(x, y) != '#';
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
        levelManager.loadLevel(levelManager.getCurrentLevel(), false);
        drawBoard();
    }

    private void updateLevelLabel() {
        lbnivel.setText("Nivel: " + levelManager.getCurrentLevel());
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            // Crear un archivo para guardar la partida
            FileWriter fileWriter = new FileWriter("src/main/java/una/cr/ac/levels/partida_guardada.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Guardar el nivel actual
            printWriter.println("Nivel:" + levelManager.getCurrentLevel());

            // Guardar el estado del tablero
            LinkedList board = levelManager.getBoard();
            for (int i = 0; i < board.getHeight(); i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < board.getWidth(); j++) {
                    row.append(board.getCell(j, i));
                }
                printWriter.println(row.toString());
            }
            // Cerrar el archivo
            printWriter.close();

            System.out.println("Partida guardada exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
        levelManager = null;
        // Navegar de vuelta al menú principal
        FlowController.getInstance().goViewInWindow("MenuView");
        ((Stage) btnGuardar.getScene().getWindow()).close();
    }

    private void onGameCompleted() {
        System.out.println("¡Felicidades! Has completado todos los niveles.");

        // Redirigir a la vista de ganadores
        FlowController.getInstance().goViewInWindow("FinalView");

        // Cerrar la ventana actual
        Stage currentStage = (Stage) gridPane.getScene().getWindow();
        currentStage.close();
    }
}
