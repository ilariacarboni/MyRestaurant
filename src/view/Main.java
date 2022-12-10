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
import view.sceneControllers.CommunicationController;

/**
 *
 * @author Natalia
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        CommunicationController.getInstance().setStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("/view/scene/dashboard.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/style/css/dashboard.css").toExternalForm());
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
