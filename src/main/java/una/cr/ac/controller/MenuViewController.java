/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package una.cr.ac.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import una.cr.ac.model.GuardarPartida;
import una.cr.ac.model.ManejoDeDatos;
import una.cr.ac.util.FlowController;
//import una.cr.ac.util.ManejoDatos;

/**
 * FXML Controller class
 *
 * @author Kevin
 */
public class MenuViewController extends Controller implements Initializable {

    @FXML
    private JFXButton btnJugar;
    @FXML
    private JFXButton btnCargarPartida;
    @FXML
    private JFXButton btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionJugar(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("BoardView");
        ((Stage) btnJugar.getScene().getWindow()).close();
    }

    @FXML
    private void onActionSalir(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void onActionBtnCargarPartida(ActionEvent event) {
        GuardarPartida partida = ManejoDeDatos.leePartida();
        if (partida != null) {
            // Pasar la partida a BoardView y cargarla
            FlowController.getInstance().goViewInWindow("BoardView", partida);
            ((Stage) btnCargarPartida.getScene().getWindow()).close();
        } else {
            System.out.println("Error al cargar la partida. Partida no encontrada o archivo corrupto.");
        }
    }

}
