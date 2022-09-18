/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.sceneControllers;

/**
 *
 * @author milar
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.entity.Menu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class menuItemController extends BaseView {

    @FXML
    private AnchorPane dishAnchorPane;

    @FXML
    private ImageView imgDish;

    @FXML
    private Label itemNameLbl;

    @FXML
    private Label itemPriceLbl;
    
    @FXML
    private VBox itemVbox;
    

    @FXML
    /*void click(MouseEvent event) {
        myListener.onClickListener(menu);
}*/

    public void setData(Menu menu) {
        this.menu = menu;
       //this.myListener = myListener; se necessario da passare come parametro in setData
        itemNameLbl.setText(menu.getNameDish());
        itemPriceLbl.setText("€"+ menu.getPrice());
        //Image image = new Image(getClass().getResourceAsStream(menu.getImgSrc()));
        //img.setImage(image);
    }
    
   // private MyListener myListener;
    public Menu menu;
    
    @FXML
    public void itemSelected(MouseEvent event) {
       
       //commController.getDishInfoController().setChosenDish(itemNameLbl.getText(), itemPriceLbl.getText());
       //myListener.onClickListener(menu);
       commController.getDishInfoController().setChosenDish(menu);    
}
    
    
}
//Integer.parseInt(prezzoTxt.getText())
