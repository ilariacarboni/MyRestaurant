package view.utils;

import javafx.event.EventHandler;
import javafx.scene.Node;
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

    private final String BACK_BUTTON_ICON = "view/style/img/others/back.png";
    private final String BACK_BUTTON_STYLESHEET = "view/style/css/common.css";
    private final String BACK_BUTTON_STYLE_CLASS = "back-button";
    private Node centerScene;
    private Node rightScene;
    private DashboardController dashboardController;
    private BackButton previousBackButton = null;

    public BackButton(){
        Image img = new Image(this.BACK_BUTTON_ICON);
        this.getChildren().add(new ImageView(img));
        this.getStylesheets().add(this.BACK_BUTTON_STYLESHEET);
        this.getStyleClass().add(this.BACK_BUTTON_STYLE_CLASS);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(dashboardController != null){
                    dashboardController.removeBackButton();
                    dashboardController.setCenterPane(centerScene, previousBackButton);
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

    public void setPreviousBackButton(BackButton previous){
        this.previousBackButton = previous;
    }
}
