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
import view.utils.CustomGridPane;
import view.utils.imageManagers.LocatedImage;

public class MenuListController extends BaseView implements Initializable {
    
    public CustomGridPane menuListGridPane;
    public ScrollPane menuListScrollPane;
    public BorderPane menuListPane;
    public Button insertDishBtn;
    public TextField dishSearchBar;
    public ImageView titleImg;

    final int GRIDPANE_COLUMNS_NUMBER = 3;
    private ArrayList dishes;
    private MenuManager menuManager = new MenuManager();
    private final String DISH_NAME = "#itemNameLbl";
    
    private HashMap<String, Object> course;

    public void initialize(URL location, ResourceBundle resources) {

        commController.setMenuListController(this);
        this.dishes = new ArrayList<>();
        menuListPane.setBackground(imagesProvider.getBackground());
        
        menuListGridPane = new CustomGridPane(this.GRIDPANE_COLUMNS_NUMBER);
        menuListGridPane.setBreakPoint(0, 800, 1);
        menuListGridPane.setBreakPoint(800, 1100, 2);
        menuListGridPane.setBreakPoint(1100, Double.MAX_VALUE, 3);
        menuListGridPane.startToListenForAdjustments(commController.getStage());
        menuListGridPane.setHgap(20);
        menuListGridPane.setVgap(20);
        menuListGridPane.setPadding(new Insets(5, 20, 20, 20));
        
        menuListScrollPane.setContent(menuListGridPane);
        this.initializeSearchBar();
    }

    public void loadDishesByCourses(HashMap<String, Object> portata){
        
        String course = portata.get("name").toString();
        if (portata.get("title-image") != null){
            titleImg.setImage(imagesProvider.getCourseTitleImage(course));
        }
        menuListGridPane.getChildren().clear();
        dishes.clear();
        ArrayList<HashMap<String, Object>> menulist = this.menuManager.getFrom(course, "course");
        imagesProvider.initializeMenuImg(menulist);
        for(int i = 0; i<menulist.size(); i++){
            HashMap<String, Object> menuDish = menulist.get(i);
            this.addMenu(menuDish, portata);
        }
    }

    public void addMenu(HashMap<String, Object> dishInfo,  HashMap<String, Object> course){
        int index = this.dishes.size() ;
        this.dishes.add(index, dishInfo);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.MENU_ITEM_PATH));

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
        borderPane.setRight(FXMLLoader.load(getClass().getResource(this.ADD_MENU_PATH)));

    }

    private void initializeSearchBar() {
        dishSearchBar.textProperty().addListener((observable, oldValue, newValue) ->{
                   ObservableList<Node> dishsearch = menuListGridPane.getChildren();
                   for(Node dish : dishsearch){
                       Label dishNameLabel = (Label)((AnchorPane) dish ).lookup(this.DISH_NAME);
                       String dishName = dishNameLabel.getText();
                       if(!dishName.toLowerCase().contains(newValue.toLowerCase())){
                           dish.setVisible(false);
                           dish.setManaged(false);
                       }else{
                           dish.setVisible(true);
                           dish.setManaged(true);
                       }
                   }
               });
    }

}