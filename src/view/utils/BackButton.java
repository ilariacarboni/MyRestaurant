package view.utils;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view.sceneControllers.DashboardController;

/**
 * Button that, placed in a particular dashboard scene (made by a central and a right component), memorize the
 * previous combination of center and right scene; when pressed repopulates the scene with that components.
 */
public class BackButton extends AnchorPane {
    private Node centerScene;
    private Node rightScene;
    private DashboardController dashboardController;

    public BackButton(){
        Image img = new Image("view/style/img/others/back.png");
        this.getChildren().add(new ImageView(img));
        this.getStylesheets().add("view/style/css/common.css");
        this.getStyleClass().add("back-button");
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(dashboardController != null){
                    dashboardController.removeBackButton();
                    dashboardController.setCenterPane(centerScene);
                    dashboardController.setRightPane(rightScene);
                }
            }
        });
    }

    public void setDashboardController(DashboardController d){
        this.dashboardController = d;
    }

    public void setCenterScene(Node centerScene){
        this.centerScene = centerScene;
    }

    public void setRightScene(Node rightScene){
        this.rightScene = rightScene;
    }

}
