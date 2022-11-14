/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import business.CourseManager;
import business.MenuManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import view.utils.CustomDialog;
/**
 * FXML Controller class
 *
 * @author milar
 * 
 */

public class DishInfoController extends BaseView implements Initializable {
    public VBox chosenDishCard;
    public Button deleteBtn;
    public ImageView dishImg;
    public Label dishNameLbl;
    /* public Label dishPriceLbl;*/
    public Button modifyBtn;
    public TextField priceTxtfield;

    private HashMap<String, Object> dishInfo;
    private MenuManager menuManager;
    private CourseManager courseManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         commController.setDishInfoController(this);
         this.menuManager = new MenuManager();
         this.courseManager = new CourseManager();
    }  
    
    public void setChosenDish(HashMap<String, Object> menu){
        this.dishInfo = menu;
        String nameDish = menu.get("nameDish").toString();
        this.dishNameLbl.setText(nameDish);
       String imagePath = (String)menu.get("image");
       if(imagePath != null){
             this.dishImg.setImage(imagesProvider.getMenuImage(menu.get("nameDish").toString(), imagePath ));
        }else{
            this.dishImg.setImage(imagesProvider.getDefaultDishImage());
        }
    }
    
    @FXML
    void deleteBtnClicked(ActionEvent event) {
       boolean res = this.menuManager.deleteMenu(dishInfo);
        
        if(!res){
                    String text = "Il piatto non è stato cancellato";
                    dialog.setInfo(text, CustomDialog.TYPE_WARNING);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Attenzione !");
                    resetTextFields();
                }else{
                    String text = "Il piatto è stato cancellato correttamente";
                    dialog.setInfo(text, CustomDialog.TYPE_SUCCESS);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Cancellazione Piatto");
                    resetTextFields();
                    refreshCourse();
                      
                }
        
    }

    @FXML
    void modifyBtnClicked(ActionEvent event) {
        double price = Double.parseDouble(priceTxtfield.getText());
        dishInfo.put("price", price);
        
        boolean res = this.menuManager.updateMenu(dishInfo);
                if(!res){
                    String text = "I dati non sono stati modificati!";
                    dialog.setInfo(text, CustomDialog.TYPE_ERROR);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Errore !");
                    priceTxtfield.setText("");
                }else{
                    String text = "I dati sono stati modificati!";
                    dialog.setInfo(text, CustomDialog.TYPE_SUCCESS);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Inserimento bolletta");
                    resetTextFields();
                    refreshCourse();
                   
                }
    }
    
    private void resetTextFields() {
        priceTxtfield.setText("");
        dishNameLbl.setText("Seleziona piatto");
        dishImg.setImage(imagesProvider.getDefaultDishImage());
        
    }

    private void refreshCourse() {
        String courseName = dishInfo.get("course").toString();
        ArrayList<HashMap<String, Object>> courselist = this.courseManager.getFrom(courseName, "name");
        MenuListController menulistContr = commController.getMenuListController();
        for(int i = 0; i<courselist.size(); i++){
            HashMap<String, Object> course = courselist.get(i);
            menulistContr.loadDishesByCourses(course);
        }
    }
    
}
