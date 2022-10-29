package view.sceneControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import business.ProductManager;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import view.utils.BackButton;
import view.utils.CustomDialog;
import view.utils.LocatedImage;

import static javafx.scene.layout.BackgroundPosition.CENTER;
import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;
import static javafx.scene.layout.BackgroundRepeat.REPEAT;
import static javafx.scene.layout.BackgroundSize.DEFAULT;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class ProductsPaneController extends BaseView implements Initializable {

    private ProductManager productManager = new ProductManager();
    private final String PRODUCT_LABEL_ID = "#productNameLabel";
    private final String PRODUCT_FXML = this.PRODUCT_COMPONENT_PATH;
    private final String ADD_PRODUCT_PANE_FXML = this.ADD_PRODUCT_PANE_PATH;
    //gridpane's columns number, can be set externally to make it responsive
    private int gridpaneColumnsNumber = 1;
    private final String PRODUCT_INFO_DEFAULT_TITLE = "Seleziona un prodotto per visualizzarne i dettagli";

    public AnchorPane addProductBtn;
    public ImageView categoryName;
    public TextField searchBar;
    public GridPane productsContainer;
    public BorderPane mainContainer;
    public BorderPane productInfoMainContainer;
    public Label productInfoMainContainerTitle;
    public AnchorPane backButtonContainer;
    public boolean productInfoIsDirty;
    private ArrayList shownProducts ;
    private String productsCategory;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setProductPaneController(this);
        mainContainer.setBackground(new Background(new BackgroundImage(new LocatedImage(BACKGROUND_PATH), REPEAT, NO_REPEAT, CENTER, DEFAULT)));
        this.shownProducts = new ArrayList<>();
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

    public void loadProductsByCategory(HashMap<String, Object> category){
        refresh();
        String categoryName = category.get("name").toString();
        this.productsCategory = categoryName;
        if(category.get("nameImg") != null){
            this.categoryName.setImage(new LocatedImage(category.get("nameImg").toString()));
        }
        productsContainer.getChildren().clear();
        ArrayList<HashMap<String, Object>> products = this.productManager.getFrom(categoryName, "category");
        for(int i = 0; i<products.size(); i++){
            HashMap<String, Object> product = products.get(i);
            this.addProduct(product, category);
        }
    }

    public void refresh(){
        this.shownProducts = new ArrayList();
        this.addProductBtn.setVisible(true);
        this.addProductBtn.setManaged(true);
    }
    
    public void addProduct(HashMap<String, Object> productInfo, HashMap<String, Object> category){
        if(category.get("name").toString().equals(this.productsCategory)){
            int index = this.shownProducts.size() ;
            this.shownProducts.add(index, productInfo);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.PRODUCT_FXML));
            Node productNode = null;
            try {
                productNode = loader.load();
                ProductController productContr = loader.getController();
                productContr.setProductInfo(productInfo, category);
                int columnIndex = index%this.gridpaneColumnsNumber;
                int rowIndex = (int) Math.floor(index/this.gridpaneColumnsNumber);
                productsContainer.add(productNode, columnIndex, rowIndex);
                if(index == 0){
                    productContr.select();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
    private void addProductBtnClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.ADD_PRODUCT_PANE_FXML));
        Node addProductPane = loader.load();
        commController.getDashboardController().setRightPane(addProductPane);
        this.addProductBtn.setVisible(false);
        this.addProductBtn.setManaged(false);
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

    public void newProductOrder(MouseEvent mouseEvent) {
        ProductInfoPaneController productInfoPaneController = commController.getProductInfoPaneController();
        HashMap<String, Object> productInfo = null;
        if(productInfoPaneController != null){
            productInfo = productInfoPaneController.getCurrentProductInfo();
        }
        if(productInfo != null){
            String name = (String) productInfo.get("name");
            commController.getDashboardController().newOrderFor(name);
        }else{
            String text = "Selezionare il prodotto per cui si vuole effettuare l'ordine";
            CustomDialog dialog = new CustomDialog(text, CustomDialog.TYPE_INFO);
            dialog.setButtons(ButtonType.OK);
            dialog.showAndWait("Attenzione !");
        }
    }
}
