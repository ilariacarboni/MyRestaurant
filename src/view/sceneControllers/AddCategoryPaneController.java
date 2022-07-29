/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.sceneControllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class AddCategoryPaneController extends BaseView implements Initializable {

    @FXML
    private BorderPane addCategoryPane;
    @FXML
    private Button addCategoryBtn;
    @FXML
    private TextField textField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addCategoryBtnClicked(ActionEvent event) throws IOException {
        String categoryName = textField.getText();
        if(categoryName.length() >0){
            //aggiunta categoria a DB
          HashMap<String, Object> categoryMap = new HashMap<String, Object>();
          categoryMap.put("name", categoryName);
          controllerForView.save(categoryMap, "category");
          
          CategoryPaneController cat = commController.getCategoryPaneController();
          cat.addCategory(categoryName);
        }
    }
}
