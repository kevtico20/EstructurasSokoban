package una.cr.ac.controller;

import com.jfoenix.controls.JFXButton;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;
import una.cr.ac.model.GuardarPartida;
import una.cr.ac.model.LevelManager;
import una.cr.ac.model.LinkedList;
import una.cr.ac.model.Player;
import una.cr.ac.util.FlowController;

public class BoardViewController extends Controller implements Initializable {

    @FXML
    private JFXButton btnUp, btnDown, btnLeft, btnRight;
    @FXML
    public GridPane gridPane;
    @FXML
    private Label lbnivel;
    @FXML
    private Button btnGuardar, btnReiniciar;

    private LevelManager levelManager;
    private int direction = 0;
    private int count = 0;
    private Vector<String> movimientos = new Vector<>();
    @FXML
    private Button btnsalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        levelManager = new LevelManager();
        drawBoard();
        updateLevelLabel();

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
        if (partida != null) {
            levelManager.loadLevel(partida.getNivel(), true);
            levelManager.getPlayer().setPosition(partida.getPlayerX(), partida.getPlayerY());
            drawBoard();
            updateLevelLabel();
        }
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                moveUp(null);
                break;
            case S:
                moveDown(null);
                break;
            case A:
                moveLeft(null);
                break;
            case D:
                moveRight(null);
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
            if (levelManager.isLevelComplete()) {

                if (levelManager.getCurrentLevel() < LevelManager.getTOTAL_LEVELS()) {
                    System.out.println("Este es el nivel despues de cargarlo:");
                    replayMovements();
                } else {
                    onGameCompleted();
                }
            }
        } else {
            System.out.println("Movimiento no válido: (" + newX + ", " + newY + ")");
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < levelManager.getBoard().getWidth() && y >= 0 && y < levelManager.getBoard().getHeight()
                && levelManager.getBoard().getCell(x, y) != '#';
    }

    private void drawBoard() {
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < levelManager.getBoard().getWidth(); i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(40);
            gridPane.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < levelManager.getBoard().getHeight(); i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(40);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        LinkedList board = levelManager.getBoard();
        Player player = levelManager.getPlayer();

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                char cell = board.getCell(j, i);
                ImageView imageView = null;
                String imagePath = null;

                switch (cell) {
                    case '#':
                        imagePath = "/una/cr/ac/sokoban/resources/wall.png";
                        break;
                    case '$':
                        imagePath = "/una/cr/ac/sokoban/resources/box.png";
                        break;
                    case '.':
                        imagePath = "/una/cr/ac/sokoban/resources/point.png";
                        break;
                    case '!':
                        imagePath = "/una/cr/ac/sokoban/resources/boxPoint.png";
                        break;
                    case '@':
                        imageView = createAnimatedSprite();
                        break;
                    default:
                        imagePath = "/una/cr/ac/sokoban/resources/floor.png";
                        break;
                }

                if (imagePath != null && imageView == null) {
                    try {
                        imageView = new ImageView(new Image(getClass().getResource(imagePath).toString()));
                        imageView.setFitWidth(40);
                        imageView.setFitHeight(40);
                    } catch (Exception e) {
                        System.out.println("No se pudo cargar la imagen en la ruta: " + imagePath);
                    }
                }

                if (imageView != null) {
                    gridPane.add(imageView, j, i);
                }
            }
        }

        try {
            ImageView playerSprite = createAnimatedSprite();
            playerSprite.setFitWidth(40);
            playerSprite.setFitHeight(40);
            gridPane.add(playerSprite, player.getX(), player.getY());
        } catch (Exception e) {
            System.out.println("No se pudo cargar la animación del jugador.");
        }
    }

    private ImageView createAnimatedSprite() {
        Image spriteSheet = new Image(getClass().getResourceAsStream("/una/cr/ac/sokoban/resources/frames.png"));

        ImageView imageView = new ImageView(spriteSheet);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        final int FRAME_WIDTH = 160;
        final int FRAME_HEIGHT = 160;
        final int COLUMNS = 4;
        final int TOTAL_FRAMES = 4;
        final int[] currentFrame = {0};

        int x = currentFrame[0] * FRAME_WIDTH;
        int y = direction * FRAME_HEIGHT;

        imageView.setViewport(new javafx.geometry.Rectangle2D(x, y, FRAME_WIDTH, FRAME_HEIGHT));

        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis(200), e -> {
                    currentFrame[0] = (currentFrame[0] + 1) % TOTAL_FRAMES;
                    int newX = currentFrame[0] * FRAME_WIDTH;
                    imageView.setViewport(new javafx.geometry.Rectangle2D(newX, y, FRAME_WIDTH, FRAME_HEIGHT));
                })
        );

        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        return imageView;
    }

    @FXML
    private void onActionReset(ActionEvent event) {
        gridPane.setFocusTraversable(true);
        gridPane.setOnKeyPressed(this::handleKeyPress);
        gridPane.requestFocus();
        levelManager.loadLevel(levelManager.getCurrentLevel(), false);
        movimientos.clear();

        drawBoard();
    }

    public void updateLevelLabel() {
        lbnivel.setText("Nivel: " + levelManager.getCurrentLevel());
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            FileWriter fileWriter = new FileWriter("src/main/java/una/cr/ac/levels/partida_guardada.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("Nivel:" + levelManager.getCurrentLevel());

            LinkedList board = levelManager.getBoard();
            for (int i = 0; i < board.getHeight(); i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < board.getWidth(); j++) {
                    row.append(board.getCell(j, i));
                }
                printWriter.println(row.toString());
            }

            printWriter.println("Player:" + levelManager.getPlayer().getX() + "," + levelManager.getPlayer().getY());

            printWriter.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Partida Guardada");
            alert.setHeaderText(null);
            alert.setContentText("La partida se ha guardado exitosamente.");
            alert.showAndWait();  // Mostrar el alert y esperar a que el usuario lo cierre

            System.out.println("Partida guardada exitosamente.");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al guardar la partida: " + e.getMessage());
            alert.showAndWait();  // Mostrar el alert y esperar a que el usuario lo cierre
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
    }

    private void onGameCompleted() {
        System.out.println("¡Felicidades! Has completado todos los niveles.");

        FlowController.getInstance().goViewInWindow("FinalView");

        Stage currentStage = (Stage) gridPane.getScene().getWindow();
        currentStage.close();
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionSalir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que deseas salir?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit();
            } else {
                alert.close();
            }
        });
    }

    private void replayMovements() {
        // Restaurar el estado inicial del nivel
        levelManager.resetToInitialState();
        drawBoard();

        new Thread(() -> {
            for (int i = 0; i < movimientos.size(); i++) {
                String movimiento = movimientos.get(i);

                Platform.runLater(() -> {
                    switch (movimiento) {
                        case "up":
                            direction = 1;
                            movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() - 1);
                            break;
                        case "down":
                            direction = 0;
                            movePlayer(levelManager.getPlayer().getX(), levelManager.getPlayer().getY() + 1);
                            break;
                        case "left":
                            direction = 2;
                            movePlayer(levelManager.getPlayer().getX() - 1, levelManager.getPlayer().getY());
                            break;
                        case "right":
                            direction = 3;
                            movePlayer(levelManager.getPlayer().getX() + 1, levelManager.getPlayer().getY());
                            break;
                    }
                });

                // Pausar el hilo actual para crear el efecto de animación
                try {
                    Thread.sleep(500); // 0.5 segundos entre cada movimiento
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Verificar si es el último movimiento
                if (i == movimientos.size() - 2) {
                    Platform.runLater(() -> {
                        // Acciones finales después de la animación
                        movimientos.clear();
                        levelManager.advanceLevel();
                        drawBoard();
                        updateLevelLabel();
                        System.out.println("Este el vector de movimientos:" + movimientos);
                    });
                    break; // Salir del bucle una vez completada la animación
                }
            }

        }).start();
    }

}
