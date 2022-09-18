package view.utils;

import javafx.scene.Node;
import javafx.scene.control.Button;
import view.sceneControllers.DashboardController;

/**
 * Button that, placed in a particular dashboard scene (made by a central and a right component), memorize the
 * previous combination of center and right scene; when pressed repopulates the scene with that components.
 */
public class BackButton extends Button {
    private Node centerScene;
    private Node rightScene;
    private DashboardController dashboardController;

    public BackButton(){
        this.setOnAction(event -> {
            if(this.dashboardController != null){
                if(this.centerScene != null){
                    this.dashboardController.setCenterPane(this.centerScene);
                }
                if(this.rightScene != null){
                    this.dashboardController.setRightPane(this.rightScene);
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
