/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package una.cr.ac.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ArauzKJ
 */
public class FinalViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imgLogro;
    @FXML
    private Label labelWin;
    @FXML
    private ImageView imgTrofeo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        animateTrofeo();
        animateLogro();
    }

    @Override
    public void initialize() {
    }

    private void animateTrofeo() {
        // Animación de opacidad
        Timeline opacityTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(imgTrofeo.opacityProperty(), 0)),
            new KeyFrame(new Duration(5000), new KeyValue(imgTrofeo.opacityProperty(), 1))
        );
        opacityTimeline.play();

        // Efecto de brillo
        Glow glow = new Glow(0.5);
        ColorAdjust colorAdjust = new ColorAdjust();
        imgTrofeo.setEffect(glow);
        
        // Animación de color de brillo
        Timeline colorTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, 
                new KeyValue(colorAdjust.hueProperty(), -1)
            ),
            new KeyFrame(new Duration(2000), 
                new KeyValue(colorAdjust.hueProperty(), 1)
            )
        );
        colorTimeline.setCycleCount(Timeline.INDEFINITE);
        colorTimeline.setAutoReverse(true);
        colorTimeline.play();
        
        glow.setInput(colorAdjust);
    }
     private void animateLogro() {
        // Animación de desplazamiento para imgLogro
        TranslateTransition imgLogroTransition = new TranslateTransition(Duration.seconds(3), imgLogro);
        imgLogroTransition.setFromY(-imgLogro.getLayoutY() - imgLogro.getFitHeight());
        imgLogroTransition.setToY(0);
        imgLogroTransition.setOnFinished(event -> addRGBEffectToImgLogro());
        imgLogroTransition.play();

        // Animación de desplazamiento para labelWin
        TranslateTransition labelWinTransition = new TranslateTransition(Duration.seconds(3), labelWin);
        labelWinTransition.setFromY(-labelWin.getLayoutY() - labelWin.getHeight());
        labelWinTransition.setToY(0);
        labelWinTransition.play();
    }

    private void addRGBEffectToImgLogro() {
        Glow glow = new Glow(0.5);
        ColorAdjust colorAdjust = new ColorAdjust();
        imgLogro.setEffect(glow);

        // Animación de color de brillo RGB
        Timeline rgbTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(colorAdjust.hueProperty(), -1)),
            new KeyFrame(new Duration(2000), new KeyValue(colorAdjust.hueProperty(), 1))
        );
        rgbTimeline.setCycleCount(Timeline.INDEFINITE);
        rgbTimeline.setAutoReverse(true);
        rgbTimeline.play();

        glow.setInput(colorAdjust);
    }
}
