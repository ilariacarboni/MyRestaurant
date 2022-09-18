package view.sceneControllers;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import controller.IControllerForView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class ProductInfoPaneController extends BaseView implements Initializable {
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
    @FXML
    public ImageView productImage;
    @FXML
    public Button chooseImgBtn;
    @FXML
    public VBox mainContainer;
    public HashMap<String,Object> shownProduct = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void setProductInfo(HashMap<String, Object> product){
        this.shownProduct = product;
        this.qtyLabel.setText(String.valueOf(product.get("qty")));
        this.supplierLabel.setText((String)product.get("supplier"));
        this.priceLabel.setText(String.valueOf(product.get("price")));
        this.barcodeLabel.setText(String.valueOf(product.get("barcode")));
        String imagePath = (String)product.get("image");
        try{
            Image productImage = new Image(new File(imagePath).toURI().toString());
            this.productImage.setImage(productImage);
        }catch (Exception e){
            this.productImage.setImage(new Image(new File("src/view/style/img/others/product-image-not-found.png").toURI().toString()));
        }
    }

    public void chooseImgForProduct(MouseEvent mouseEvent) {
        final FileChooser fc = new FileChooser();
        File returnVal = null;
        returnVal = fc.showOpenDialog((Stage)((Node) mouseEvent.getSource()).getScene().getWindow());
        if(returnVal != null && this.shownProduct != null){
            System.out.println(returnVal.toURI().toString());
            String customImagePath = returnVal.toURI().toString();
            HashMap<String, Object> product = new HashMap<String, Object>();
            this.shownProduct.put("image", customImagePath);
            controllerForView.update(this.shownProduct, "product");
        }
        //impostare immagine del prodotto
    }

}
