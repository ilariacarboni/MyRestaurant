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

import business.ProductManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view.utils.LocatedImage;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class ProductController extends BaseView implements Initializable {

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
    public Label productPriceValue;
    @FXML
    public Label productQtyValue;
    @FXML
    private AnchorPane product;

    private HashMap<String, Object> productInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setProductInfo(HashMap<String, Object> productInfo, HashMap<String, Object> category){
        this.productInfo=productInfo;
        this.productNameLabel.setText((String)productInfo.get("name"));
        this.productPriceValue.setText(productInfo.get("price").toString());
        this.productQtyValue.setText(productInfo.get("qty").toString());
        if (category != null && category.get("img") != null){
            this.productIcon.setImage(new LocatedImage((String) category.get("icon")));
        }
    }
    
    public HashMap<String, Object> getProductInfo(){
        return productInfo;
    }

    @FXML
    private void productSelected(MouseEvent event) throws IOException {
        Node productInfoPane = null;
        ProductInfoPaneController productInfoPaneContr = null;
        if(commController.getProductInfoPaneController() != null){
            productInfoPaneContr = commController.getProductInfoPaneController();
            productInfoPaneContr.refresh();
            productInfoPane = productInfoPaneContr.getInfoPane();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.PRODUCT_SCENE));
            productInfoPane = loader.load();
            productInfoPaneContr = loader.getController();
        }
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
}
