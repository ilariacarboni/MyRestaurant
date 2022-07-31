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
<<<<<<< HEAD

=======
import javafx.event.ActionEvent;
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
<<<<<<< HEAD
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
=======
import javafx.scene.control.Button;
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class ProductController extends BaseView implements Initializable {

<<<<<<< HEAD
    private final String PRODUCT_SCENE = "/view/scene/productInfoPane.fxml";

    //breakpoints for responsive adjustment of product image
    private final int BREAKPOINT_0 = 300;
    private final int IMAGE_WIDTH_FOR_BREAKPOINT_0 = 64;
    private final int PRODUCT_HEIGHT_FOR_BREAKPOINT_0 = 200;
    private final int BREAKPOINT_1 = 400;
    private final int IMAGE_WIDTH_FOR_BREAKPOINT_1 = 128;
    private final int PRODUCT_HEIGHT_FOR_BREAKPOINT_1 = 300;

    @FXML
    public Label productPriceLabel;
    @FXML
    public Label productQtyLabel;
    @FXML
    public ImageView productImage;
    @FXML
    public Label productNameLabel;
    @FXML
    public ImageView productIcon;
    @FXML
    public ImageView productPriceIcon;
    @FXML
    public ImageView productQtyIcon;
    @FXML
    public AnchorPane productContainer;
    @FXML
    private AnchorPane product;

    private HashMap<String, Object> productInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setProductInfo(HashMap<String, Object> productInfo){
        this.productInfo=productInfo;
        this.productNameLabel.setText((String)productInfo.get("name"));
=======
    @FXML
    private Button product;

    private HashMap<String, Object> productInfo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setProductInfo(HashMap<String, Object> productInfo){
        this.productInfo=productInfo;
        this.product.setText((String)productInfo.get("name"));
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
    }
    
    public HashMap<String, Object> getProductInfo(){
        return productInfo;
    }

    @FXML
<<<<<<< HEAD
    private void productSelected(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.PRODUCT_SCENE));
        Node productInfoPane = loader.load();
        ProductInfoPaneController productInfoPaneContr = loader.getController();
        productInfoPaneContr.setProductInfo(this.productInfo);
        commController.getProductsPaneController().showProductInfoPane(productInfoPane,(String)productInfo.get("name"));
    }

    public void productHovered(MouseEvent mouseEvent) {
        product.getStyleClass().add("product-hover");
    }

    public void productNotHovered(MouseEvent mouseEvent) {
        if(product.getStyleClass().contains("product-hover")){
            product.getStyleClass().remove("product-hover");
        }
    }
=======
    private void productSelected(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene/productInfoPane.fxml"));
        Node productInfoPane = loader.load();
        ProductInfoPaneController productInfoPaneContr = loader.getController();
        productInfoPaneContr.setProductInfo(this.productInfo);
        commController.getProductsPaneController().showProductInfoPane(productInfoPane);
    }
    
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
}
