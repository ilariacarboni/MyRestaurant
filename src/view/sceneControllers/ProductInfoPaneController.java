package view.sceneControllers;

import java.io.File;
import java.net.URL;
import java.util.*;

import business.ProductManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import view.utils.CustomLineChart;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class ProductInfoPaneController extends BaseView implements Initializable {

    private final String DEFAULT_IMAGE_PATH = "src/view/style/img/others/no-results.png";
    private ProductManager prodManager;
    private ArrayList productCharts = new ArrayList();
    private int index = 0;
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
    public VBox mainContainer;
    public HashMap<String,Object> shownProduct = null;
    @FXML
    public AnchorPane chartContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.prodManager = new ProductManager();
    }
    
    public void setProductInfo(HashMap<String, Object> product){
        if(this.shownProduct != null){
            int oldProductId = (int)this.shownProduct.get("barcode");
            int newProductId = (int)product.get("barcode");
            if(oldProductId != newProductId){
                this.initializeUsageChart(product);
                this.initializeOrdersChart(product);
            }
        }else{
            this.initializeUsageChart(product);
            this.initializeOrdersChart(product);
        }
        this.shownProduct = product;
        this.setLabels(product);
        if(this.productCharts != null){
            this.chartContainer.getChildren().clear();
            this.chartContainer.getChildren().add((Node)this.productCharts.get(index));
        }
    }

    private void setLabels(HashMap<String, Object> product){
        this.qtyLabel.setText(String.valueOf(product.get("qty")));
        this.supplierLabel.setText((String)product.get("supplier"));
        this.priceLabel.setText(String.valueOf(product.get("price")));
        this.barcodeLabel.setText(String.valueOf(product.get("barcode")));
        String imagePath = (String)product.get("image");
        if(imagePath != null && !imagePath.equals("")){
            try{
                Image productImage = new Image(new File(imagePath).toURI().toString());
                this.productImage.setImage(productImage);
            }catch(Exception e){
                this.productImage.setImage(new Image(new File(this.DEFAULT_IMAGE_PATH).toURI().toString()));
            }
        }else{
            this.productImage.setImage(new Image(new File(this.DEFAULT_IMAGE_PATH).toURI().toString()));
        }
    }

    private void initializeUsageChart(HashMap<String, Object> product){
        int barcode = (int)product.get("barcode");
        LinkedHashMap<String, Integer> data = this.prodManager.getProductUsage(barcode);
        CustomLineChart storeQtyChart = this.buildChart(data, "uso/mese");
        this.productCharts.add(storeQtyChart);
    }

    private void initializeOrdersChart(HashMap<String, Object> product){
        int barcode = (int)product.get("barcode");
        LinkedHashMap<String, Integer> data = this.prodManager.getProductOrders(barcode);
        CustomLineChart ordersForProductChart = this.buildChart(data, "ordini/mese");
        this.productCharts.add(ordersForProductChart);
    }

    private CustomLineChart buildChart(LinkedHashMap<String, Integer> data, String name){
        CategoryAxis catAx = new CategoryAxis();
        NumberAxis numAx = new NumberAxis();
        CustomLineChart chart = new CustomLineChart(catAx, numAx);
        if(data != null){
            XYChart.Series series = new XYChart.Series();
            series.setName(name);
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                XYChart.Data point = new XYChart.Data<>(key, value);
                series.getData().add(point);
            }
            chart.addData(series);
        }
        return chart;
    }

    @FXML
    void followingChart(MouseEvent event) {
        index = (index+1)%this.productCharts.size();
        this.chartContainer.getChildren().clear();
        this.chartContainer.getChildren().add((Node)this.productCharts.get(index));
    }

    @FXML
    void previousChart(MouseEvent event) {
        index = Math.abs((index-1)%this.productCharts.size());
        this.chartContainer.getChildren().clear();
        this.chartContainer.getChildren().add((Node)this.productCharts.get(index));
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
