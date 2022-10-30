/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class UtilityItemController extends BaseView implements Initializable {

    public Label dateLbl;
    public ImageView iconUtility;
    public AnchorPane itemAnchorPane;
    public Label numberidLbl;
    public Button stateBtn;
    public Label totalLbl;

    private HashMap<String, Object> utilityInfo;

    @FXML
    void changeStateBtnClicked(ActionEvent event) {
        
      if(stateBtn.getText().equals("Pagata")){
        stateBtn.setStyle("-fx-background-color: #ea613f");
        stateBtn.setText("Da pagare");
        //cambio stato nel db
      }
      else{
          stateBtn.setStyle("-fx-background-color: #96be25");
        stateBtn.setText("Pagata");
      }
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setUtilityInfo(HashMap<String, Object> utility) {
        this.utilityInfo = utility;
        this.numberidLbl.setText(utility.get("numberId").toString());
        this.dateLbl.setText(utility.get("date").toString());
        this.totalLbl.setText(utility.get("total").toString());
        String type = (String)utility.get("type");
        if(type.equals("gas")) {
            this.iconUtility.setImage(imagesProvider.getGasImage());
        }
        else if (type.equals("elettricità")){
          this.iconUtility.setImage(imagesProvider.getBulbImage());
        }
        else if(type.equals("acqua")){
            this.iconUtility.setImage(imagesProvider.getWaterDropImage());
        }
        
    }
    
    @FXML
    void itemHovered(MouseEvent event) {
        this.itemAnchorPane.getStyleClass().add("product-hover");
    }

    @FXML
    void itemNotHovered(MouseEvent event) {
     if (this.itemAnchorPane.getStyleClass().contains("product-hover")) {
            this.itemAnchorPane.getStyleClass().remove("product-hover");
        }
    }
}
