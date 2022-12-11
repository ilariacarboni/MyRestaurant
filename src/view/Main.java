/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.database.dbConnection;
import view.sceneControllers.CommunicationController;
import view.utils.CustomDialog;

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
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            close(primaryStage);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void close(Stage stage){
        CustomDialog dialog = CustomDialog.getInstance();
        String text = "Sei sicuro di voler chiudere l'applicazione?";
        dialog.setInfo(text, CustomDialog.TYPE_INFO);
        dialog.setButtons(ButtonType.YES, ButtonType.CANCEL);
        Optional<ButtonType> dialogRes = dialog.showAndWait("Attenzione !");
        if(dialogRes.get() == ButtonType.YES){
            dbConnection.close();
            stage.close();
        }
    }
}
