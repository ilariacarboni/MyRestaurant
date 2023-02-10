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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import view.utils.customCharts.CustomLineChart;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class ProductInfoPaneController extends BaseView implements Initializable {

    public ScrollPane infoPane;
    public Label priceLabel;
    public Label qtyLabel;
    public Label lastOrderLabel;
    public Label supplierLabel;
    public Label barcodeLabel;
    public ImageView productImage;
    public VBox mainContainer;
    public HashMap<String,Object> shownProduct = null;
    public AnchorPane chartContainer;
    private ProductManager prodManager;
    private ArrayList productCharts = new ArrayList();
    private int index = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setProductInfoPaneController(this);
        this.prodManager = new ProductManager();
    }

    public void refresh(){
        productCharts = new ArrayList();
        index = 0;
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
        String productImage = null;
        if(product.get("image") != null){
            productImage = product.get("image").toString();
        }
        this.setImage(productImage, (int)product.get("barcode"));
        if(this.productCharts != null && !this.productCharts.isEmpty()){
            this.chartContainer.getChildren().clear();
            this.chartContainer.getChildren().add((Node)this.productCharts.get(index));
        }
    }

    public ScrollPane getInfoPane(){return this.infoPane;}

    private void setLabels(HashMap<String, Object> product){
        this.qtyLabel.setText(String.valueOf(product.get("qty")));
        this.supplierLabel.setText((String)product.get("supplier"));
        this.priceLabel.setText(String.valueOf(product.get("price")));
        this.barcodeLabel.setText(String.valueOf(product.get("barcode")));

    }

    private void setImage(String imagePath, int productBarcode){
        if(imagePath != null && !imagePath.equals("")){
            try{
                this.productImage.setImage(imagesProvider.getProductImage(productBarcode, imagePath));
            }catch(Exception e){
                this.productImage.setImage(imagesProvider.getDefaultProductImage());
            }
        }else{
            this.productImage.setImage(imagesProvider.getDefaultProductImage());
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
            imagesProvider.setProductImage((int)this.shownProduct.get("barcode"), customImagePath);
        }
        //gestione aggiornamento
        int barcode = (int)this.shownProduct.get("barcode");
        HashMap<String, Object> updatedProd = this.prodManager.getProduct(barcode);
        if(updatedProd != null){
            this.setProductInfo(updatedProd);
        }
    }

    public HashMap<String, Object> getCurrentProductInfo(){
        return this.shownProduct;
    }
}
