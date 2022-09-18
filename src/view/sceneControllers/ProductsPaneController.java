package view.sceneControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class ProductsPaneController extends BaseView implements Initializable {

    private final String PRODUCT_LABEL_ID = "#productNameLabel";
    private final String PRODUCT_FXML = "/view/scene/product.fxml";
    private final String ADD_PRODUCT_PANE_FXML = "/view/scene/product.fxml";
    //numero di colonne del gridPane che può essere settato esternamente per renderlo responsive
    private int gridpaneColumnsNumber = 1;
    private final String PRODUCT_INFO_DEFAULT_TITLE = "Seleziona un prodotto per visualizzarne i dettagli";
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
    @FXML
    public BorderPane productInfoMainContainer;
    @FXML
    public Label productInfoMainContainerTitle;

    public boolean productInfoIsDirty;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setProductPaneController(this);
        
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->{
            ObservableList<Node> products = productsContainer.getChildren();
            for(Node product : products){
                Label productNameLabel = (Label)((AnchorPane) product).lookup(this.PRODUCT_LABEL_ID);
                String productName = productNameLabel.getText();
                if(!productName.contains(newValue)){
                    product.setVisible(false);
                    product.setManaged(false);
                }else{
                    product.setVisible(true);
                    product.setManaged(true);
                }
            }
        });
    }

    public void loadProductsByCategory(String category){
        category = category.toLowerCase();
        this.categoryLabel.setText(category);
        productsContainer.getChildren().clear();
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
    
    private void addProduct(HashMap<String, Object> productInfo, int index) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.PRODUCT_FXML));
        Node productNode = loader.load();
        ProductController productContr = loader.getController();
        productContr.setProductInfo(productInfo);
        int columnIndex = index%this.gridpaneColumnsNumber;
        int rowIndex = (int) Math.floor(index/this.gridpaneColumnsNumber);
        productsContainer.add(productNode, columnIndex, rowIndex);
    }
    
    public void showProductInfoPane(Node productInfoPane, String productName){
        this.productInfoIsDirty = true;
        productInfoMainContainerTitle.setText(productName);
        productInfoMainContainer.setCenter(productInfoPane);
    }

    public void emptyProductInfo(){
        if(this.productInfoIsDirty){
            productInfoMainContainerTitle.setText(this.PRODUCT_INFO_DEFAULT_TITLE);
            productInfoMainContainer.setCenter(null);
        }
    }

    @FXML
    private void addProductBtnClicked(ActionEvent event) throws IOException {
        BorderPane dashboardBorderPane = (BorderPane) mainContainer.getParent();
        dashboardBorderPane.setRight(FXMLLoader.load(getClass().getResource(this.ADD_PRODUCT_PANE_FXML)));
    }

    public void setGridPaneColumnNumber(int columnNumber){
        this.gridpaneColumnsNumber = columnNumber;
        ObservableList<Node> products = productsContainer.getChildren();
        for(int i = 0; i<products.size(); i++){
            Node product = products.get(i);
            int columnIndex = i%this.gridpaneColumnsNumber;
            int rowIndex = (int) Math.floor(i/this.gridpaneColumnsNumber);
            productsContainer.add(product, columnIndex, rowIndex);
        }
    }

    
}
