/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import business.UtilityManager;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view.utils.CustomDialog;

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
    private UtilityManager utilityManager;
    private String state = null;

    @FXML
    void changeStateBtnClicked(ActionEvent event) {
       
      if(stateBtn.getText().equals("Pagato")){
            stateBtn.setStyle("-fx-background-color: #ea613f");
            stateBtn.setText("Da pagare");
            state = "Da pagare";
      }
      else{
            stateBtn.setStyle("-fx-background-color: #96be25");
            stateBtn.setText("Pagato");
            state = "Pagato";
      }
        this.utilityInfo.put("state", state);
        boolean res = this.utilityManager.updateUtility(utilityInfo);
        if(!res){
            String text = "Lo stato dell'utenza non è stato aggiornato!";
            dialog.setInfo(text, CustomDialog.TYPE_ERROR);
            dialog.setButtons(ButtonType.OK);
            dialog.showAndWait("Errore !");
        }
        
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.utilityManager = new UtilityManager();
    }    

    void setUtilityInfo(HashMap<String, Object> utility) {
        this.utilityInfo = utility;
        this.numberidLbl.setText(utility.get("numberId").toString());
        this.dateLbl.setText(utility.get("date").toString());
        this.totalLbl.setText(utility.get("total").toString());
        
        String stateUtility = (String)utility.get("state");
        if(stateUtility==null){   
            stateUtility="Da Pagare";  //per inserimento utenza
        }
        this.stateBtn.setText(stateUtility);
        if(stateUtility.equals("Pagato")){
            stateBtn.setStyle("-fx-background-color: #96be25");
        }
        else{
            stateBtn.setStyle("-fx-background-color: #ea613f");
        }
        
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
