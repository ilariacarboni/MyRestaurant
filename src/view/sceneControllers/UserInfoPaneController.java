package view.sceneControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UserInfoPaneController extends BaseView implements Initializable {

    public AnchorPane editUser;
    public Label usernameLabel;
    public ImageView levelInfoIcon;
    public Label levelLabel;
    public Label lastLoginLabel;
    public Label loginNumberLabel;
    public ImageView userImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void setUserInfo(HashMap<String, Object> info){

    }

    public void editUser(MouseEvent mouseEvent) {
    }

    //mostrare le scene a cui l'utente può accedere avendo quel livello
    public void levelUserInfo(MouseEvent mouseEvent) {
    }
}
