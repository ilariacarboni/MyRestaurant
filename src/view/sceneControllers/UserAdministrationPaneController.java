package view.sceneControllers;

import business.AdminManager;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UserAdministrationPaneController extends BaseView implements Initializable {

    private AdminManager adminManager = new AdminManager();
    public TextField searchBar;
    public GridPane usersContainer;
    private int gridpaneColumnsNumber = 1;
    private ArrayList shownUsers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commController.setUserAdministrationPaneController(this);
        this.shownUsers = new ArrayList<>();
    }

    public void addUsers() throws IOException {
        ArrayList<HashMap<String, Object>> users = this.adminManager.getAll();
        int index = this.shownUsers.size();
        for(HashMap<String, Object> user:users){
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.USER_COMPONENT_PATH));
            Node userNode = null;
            try {
                userNode = loader.load();
                UserInfoPaneController userInfoPaneController = loader.getController();
                userInfoPaneController.setUserInfo(user);
                int columnIndex = index % this.gridpaneColumnsNumber;
                int rowIndex = (int) Math.floor(index / this.gridpaneColumnsNumber);
                usersContainer.add(userNode, columnIndex, rowIndex);
                this.shownUsers.add(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
