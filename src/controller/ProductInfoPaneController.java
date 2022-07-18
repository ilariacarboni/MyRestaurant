package controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class ProductInfoPaneController implements Initializable {

    @FXML
    private Label productNameLabel;
    @FXML
    private Label productQtyLabel;
    @FXML
    private Label productSupplierLabel;
    @FXML
    private Label productLastOrderLabel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void setProductInfo(HashMap<String, Object> product){
        this.productNameLabel.setText((String)product.get("name"));
        this.productQtyLabel.setText(String.valueOf(product.get("qty")));
        this.productSupplierLabel.setText((String)product.get("supplier"));
    }
    
}
