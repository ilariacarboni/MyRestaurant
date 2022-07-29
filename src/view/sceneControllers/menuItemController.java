/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.sceneControllers;

/**
 *
 * @author milar
 */
import model.entity.Menu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view.scene.MyListener;


public class menuItemController {

    @FXML
    private AnchorPane dishAnchorPane;

    @FXML
    private ImageView imgDish;

    @FXML
    private Label itemNameLbl;

    @FXML
    private Label itemPriceLbl;

    @FXML
    void click(MouseEvent event) {
        myListener.onClickListener(menu);
}
    
    private Menu menu;
    private MyListener myListener;

    public void setData(Menu menu, MyListener myListener) {
        this.menu = menu;
        this.myListener = myListener;
        itemNameLbl.setText(menu.getNameDish());
        itemPriceLbl.setText("€"+ menu.getPrice());
        //Image image = new Image(getClass().getResourceAsStream(menu.getImgSrc()));
        //img.setImage(image);
    }
    
}
//Integer.parseInt(prezzoTxt.getText())
