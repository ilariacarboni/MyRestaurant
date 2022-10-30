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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class ProductController extends BaseView implements Initializable {


    public Label productPriceLabel;
    public Label productQtyLabel;
    public ImageView productImage;
    public Label productNameLabel;
    public ImageView productIcon;
    public ImageView productPriceIcon;
    public ImageView productQtyIcon;
    public AnchorPane productContainer;
    public Label productPriceValue;
    public Label productQtyValue;
    public AnchorPane product;

    private final String PRODUCT_SCENE = this.PRODUCT_INFO_PANE_PATH;
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
            this.productIcon.setImage(imagesProvider.getCategoryProductIcon(category.get("name").toString()));
        }
    }
    
    public HashMap<String, Object> getProductInfo(){
        return productInfo;
    }

    public void select(){
        Node productInfoPane = null;
        ProductInfoPaneController productInfoPaneContr = null;
        if(commController.getProductInfoPaneController() != null){
            productInfoPaneContr = commController.getProductInfoPaneController();
            productInfoPaneContr.refresh();
            productInfoPane = productInfoPaneContr.getInfoPane();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.PRODUCT_SCENE));
            try {
                productInfoPane = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            productInfoPaneContr = loader.getController();
        }
        productInfoPaneContr.setProductInfo(this.productInfo);
        commController.getProductsPaneController().showProductInfoPane(productInfoPane,(String)productInfo.get("name"));
    }

    @FXML
    private void productSelected(MouseEvent event){
        this.select();
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
