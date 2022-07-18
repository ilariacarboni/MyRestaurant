package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class ProductsPaneController extends BaseView implements Initializable {

    final int GRIDPANE_COLUMNS_NUMBER = 3;
    
    @FXML
    private Label categoryLabel;
    @FXML
    private TextField searchBar;
    @FXML
    private Button addProductBtn;
    @FXML
    private GridPane productsContainer;
    @FXML
    private BorderPane mainContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setProductPaneController(this);
    }

    public void loadProductsByCategory(String category){
        this.categoryLabel.setText(category);
        ArrayList<HashMap<String, Object>> products = controllerForView.getFrom(category, "category", "product");
        for(int i = 0; i<products.size(); i++){
            HashMap<String, Object> product = products.get(i);
            try {
                addProduct(product, i);
            } catch (IOException ex) {
                Logger.getLogger(ProductsPaneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void addProduct(HashMap<String, Object> product, int index) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/product.fxml"));
        Node productNode = loader.load();
        ProductController productContr = loader.getController();
        productContr.setProductInfo(product);
        int columnIndex = index%this.GRIDPANE_COLUMNS_NUMBER;
        int rowIndex = (int) Math.floor(index/this.GRIDPANE_COLUMNS_NUMBER);
        productsContainer.add(productNode, columnIndex, rowIndex);
    }
    
    public void showProductInfoPane(Node productInfoPane){
        BorderPane dashboardBorderPane = (BorderPane) mainContainer.getParent();
        dashboardBorderPane.setRight(productInfoPane);
    }

    @FXML
    private void addProductBtnClicked(ActionEvent event) throws IOException {
        BorderPane dashboardBorderPane = (BorderPane) mainContainer.getParent();
        dashboardBorderPane.setRight(FXMLLoader.load(getClass().getResource("/view/addProductPane.fxml")));
    }
    
}
