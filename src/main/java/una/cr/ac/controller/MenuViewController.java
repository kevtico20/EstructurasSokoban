/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package una.cr.ac.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import una.cr.ac.util.FlowController;

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

}
