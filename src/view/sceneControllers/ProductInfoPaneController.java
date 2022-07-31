package view.sceneControllers;

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
    final String PRICE_LABEL_PREFIX = "Costo: ";
    final String QTY_LABEL_PREFIX = "Quantità disponibile: ";
    final String LAST_ORDER_LABEL_PREFIX = "Ultimo ordine: ";
    final String SUPPLIER_LABEL_PREFIX = "Fornitore: ";
    final String BARCODE_LABEL_PREFIX = "Codice a barre: ";
    @FXML
    public Label priceLabel;
    @FXML
    public Label qtyLabel;
    @FXML
    public Label lastOrderLabel;
    @FXML
    public Label supplierLabel;
    @FXML
    public Label barcodeLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void setProductInfo(HashMap<String, Object> product){

        this.qtyLabel.setText(this.QTY_LABEL_PREFIX + String.valueOf(product.get("qty")));
        this.supplierLabel.setText(this.SUPPLIER_LABEL_PREFIX + (String)product.get("supplier"));
        this.priceLabel.setText(this.PRICE_LABEL_PREFIX + String.valueOf(product.get("price")));
        this.barcodeLabel.setText(this.BARCODE_LABEL_PREFIX + String.valueOf(product.get("barcode")));

    }
    
}
