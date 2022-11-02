package view.sceneControllers;

import business.MenuManager;
import model.entity.Menu;
import model.entity.Course;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class MenuListController extends BaseView implements Initializable {

    private MenuManager menuManager = new MenuManager();
    @FXML
    private GridPane menuListGridPane;

    @FXML
    private ScrollPane menuListScrollPane;

    @FXML
    private BorderPane menuListPane;

    @FXML
    private Button insertDishBtn;

    private ArrayList dishes;
    final int GRIDPANE_COLUMNS_NUMBER = 3;


    public void initialize(URL location, ResourceBundle resources) {

        commController.setMenuListController(this);
        this.dishes = new ArrayList<>();
        menuListPane.setStyle("-fx-background-image: url(\"/view/style/img/background/grey.jpeg\");-fx-background-repeat: no-repeat;");
        
        
        //definire metodo search
       /* searchBar.textProperty().addListener((observable, oldValue, newValue) ->{
            ObservableList<Node> products = productsContainer.getChildren();
            for(Node product : products){
                Label productNameLabel = (Label)((AnchorPane) product).lookup(this.PRODUCT_LABEL_ID);
                String productName = productNameLabel.getText();
                if(!productName.contains(newValue)){
                    product.setVisible(false);
                    product.setManaged(false);
                }else{
                    product.setVisible(true);
                    product.setManaged(true);
                }
            }
        });*/
    }


    public void loadDishesByCourses(HashMap<String, Object> portata){

        String course = portata.get("name").toString();
        menuListGridPane.getChildren().clear();
        ArrayList<HashMap<String, Object>> menulist = this.menuManager.getFrom(course, "course");
        for(int i = 0; i<menulist.size(); i++){
            HashMap<String, Object> menuDish = menulist.get(i);
            this.addMenu(menuDish, portata);
        }
    }


    public void addMenu(HashMap<String, Object> dishInfo,  HashMap<String, Object> course){
        int index = this.dishes.size() ;
        this.dishes.add(index, dishInfo);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene/menuItem.fxml"));

        Node menuNode = null;
        try {
            menuNode = loader.load();
            MenuItemController menuitemContr = loader.getController();
            menuitemContr.setDishInfo(dishInfo, course); //definire metodo
            int column = index%this.GRIDPANE_COLUMNS_NUMBER;
            int row = (int) Math.floor(index/this.GRIDPANE_COLUMNS_NUMBER);

            menuListGridPane.add(menuNode,column ,row );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void insertDishBtnClicked(ActionEvent event) throws IOException {

        BorderPane borderPane = (BorderPane) menuListPane.getParent();
        borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/scene/AddMenuDish.fxml")));

    }

}