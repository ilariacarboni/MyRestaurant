package view.sceneControllers;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import business.ProductManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import view.utils.CustomLineChart;

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
    @FXML
    public CustomLineChart storeQuantityChart;
    public HashMap<String,Object> shownProduct = null;
    private ProductManager prodManager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.prodManager = new ProductManager();
    }
    
    public void setProductInfo(HashMap<String, Object> product){
        if(this.shownProduct != null){
            int oldProductId = (int)this.shownProduct.get("barcode");
            int newProductId = (int)product.get("barcode");
            if(oldProductId != newProductId){
                this.initializeStoreQtyChart(product);
            }
        }else{
            this.initializeStoreQtyChart(product);
        }
        this.shownProduct = product;
        this.setLabels(product);

    }

    private void setLabels(HashMap<String, Object> product){
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

    private void initializeStoreQtyChart(HashMap<String, Object> product){
        //ottenimento informazioni dalla classe di business
        int barcode = (int)product.get("barcode");
        HashMap<Integer, Integer> data = this.prodManager.getStoreStatistics(barcode);
        if(data != null){
            XYChart.Series series = new XYChart.Series();
            series.setName("uso/mese");
            for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
                int key = entry.getKey();
                int value = entry.getValue();
                XYChart.Data point = new XYChart.Data<>(key, value);
                series.getData().add(point);
            }
            storeQuantityChart.addData(series);
        }
    }
    public void chooseImgForProduct(MouseEvent mouseEvent) {
        final FileChooser fc = new FileChooser();
        File returnVal = null;
        returnVal = fc.showOpenDialog((Stage)((Node) mouseEvent.getSource()).getScene().getWindow());
        if(returnVal != null && this.shownProduct != null){
            String customImagePath = returnVal.toURI().getRawPath();
            HashMap<String, Object> product = new HashMap<String, Object>();
            this.shownProduct.put("image", customImagePath);
            this.prodManager.updateProduct(this.shownProduct);
        }
        //gestione aggiornamento
        int barcode = (int)this.shownProduct.get("barcode");
        HashMap<String, Object> updatedProd = this.prodManager.getProduct(barcode);
        if(updatedProd != null){
            this.setProductInfo(updatedProd);
        }
    }

}
