/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package una.cr.ac.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Kevin
 */
public class Img {

    private ImageView imageView;

    public Img(Pane pane, String imagePath, double x, double y, double width, double height) {
        // Verifica la ruta del archivo
        System.out.println("Cargando imagen desde: " + imagePath);

        // Cargar la imagen desde el archivo de recursos
        Image image = new Image(getClass().getResourceAsStream(imagePath), width, height, false, true);
        if (image.isError()) {
            System.out.println("Error al cargar la imagen: " + image.getException());
        } else {
            imageView = new ImageView(image);
            imageView.setX(x);
            imageView.setY(y);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            pane.getChildren().add(imageView);
        }
    }

    public void setPosition(double x, double y) {
        imageView.setX(x);
        imageView.setY(y);
    }

    public void setSize(double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }
}
