/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Natalia
 */
public class NewFXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("CreateProduct.fxml"));
<<<<<<< HEAD
        Parent root = FXMLLoader.load(getClass().getResource("scene/dashboard.fxml"));
        
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/style/createProduct.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/view/style/css/dashboard.css").toExternalForm());
=======
        Parent root = FXMLLoader.load(getClass().getResource("/view/scene/dashboard.fxml"));
        
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/style/createProduct.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/view/style/dashboard.css").toExternalForm());
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714

        //primaryStage.setTitle("Add Product");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
