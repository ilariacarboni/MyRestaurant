/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import business.MenuManager;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         commController.setDishInfoController(this);
         this.menuManager = new MenuManager();
    }  
    
    public void setChosenDish(HashMap<String, Object> menu){
        this.dishInfo = menu;
        String nameDish = menu.get("nameDish").toString();
        this.dishNameLbl.setText(nameDish);
       // this.dishPriceLbl.setText(String.valueOf(menu.get("price")));
       if (menu.get("image") != null){
             this.dishImg.setImage(imagesProvider.getMenuImage(nameDish));
        }else{
            this.dishImg.setImage(imagesProvider.getDefaultDishImage());
        }
    }
    
    
    //scelta immagine
    
    //modifica prezzo con rif a update 
    
    //cancellazione menu delete
    
}
