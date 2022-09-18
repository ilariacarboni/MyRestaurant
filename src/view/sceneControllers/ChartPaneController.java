package view.sceneControllers;

import business.ChartsManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ChartPaneController extends BaseView implements Initializable {

    private ChartsManager chartsManager = new ChartsManager();
    private int index = 0;
    private HashMap<Integer, BarChart> monthCharts = new HashMap<>();
    @FXML
    private ScrollPane container;
    @FXML
    private BorderPane chartBorderPane;
    @FXML
    private GridPane chartsGridPane;
    @FXML
    private AnchorPane revenueTrendChart;
    @FXML
    private AnchorPane warehouseCompositionChart;
    @FXML
    private AnchorPane utilityCostsChart;
    @FXML
    private AnchorPane moreOrderedDishesChartContainer;
    @FXML
    private Button leftBtn;
    @FXML
    private Button rightBtn;
    @FXML
    private AnchorPane moreOrderedDishesChart;
    @FXML
    private AnchorPane revenueOrdersComparisonChart;
    @FXML
    private AnchorPane revenueUtilitiesComparisonChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.setFitToHeight(true);
        container.setFitToWidth(true);
        this.makeRevenueTrendChart();
        this.makeWarehouseCompositionChart();
        this.makeUtilityCostsChart();
        this.makeMoreOrderedDishesChart();
        this.makeRevenueOrdersComparisonChart();
        this.makeRevenueUtilitiesComparisonChart();
    }

    private void makeRevenueTrendChart(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> revenueChart = new BarChart<String,Number>(xAxis,yAxis);
        revenueChart.setTitle("Incasso totale per mese");
        xAxis.setLabel("mese");

        LinkedHashMap<String, Double> data = chartsManager.getRevenueData();
        if(data != null){
            XYChart.Series series = new XYChart.Series();
            series.setName("incasso/mese");
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                series.getData().add(new XYChart.Data(key, value));
            }
            revenueChart.getData().add(series);
            this.revenueTrendChart.getChildren().add(revenueChart);
        }

    }

    private void makeWarehouseCompositionChart(){
        HashMap<String, Double> composition = this.chartsManager.getWarehouseOccupationData();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : composition.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            PieChart.Data data = new PieChart.Data(key, value);
            pieChartData.add(data);
        }
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Composizione del magazzino");
        this.setAnchor(chart, 0.0);
        this.warehouseCompositionChart.getChildren().add(chart);
    }

    private void makeUtilityCostsChart(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> utilityChart = new BarChart<String,Number>(xAxis,yAxis);
        utilityChart.setTitle("Costo mensile utenze");
        xAxis.setLabel("mese");

        LinkedHashMap<String, Double> data = chartsManager.getUtilityCostPerMonthData();
        if(data != null){
            XYChart.Series series = new XYChart.Series();
            series.setName("costo/mese");
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();
                series.getData().add(new XYChart.Data(key, value));
            }
            utilityChart.getData().add(series);
            this.setAnchor(utilityChart, 10.0);
            this.utilityCostsChart.getChildren().add(utilityChart);
        }
    }

    private void makeMoreOrderedDishesChart(){
        LinkedHashMap<String, LinkedHashMap<String, Integer>> data = this.chartsManager.getMoreOrderedDishes();
        if(data != null){
            for (Map.Entry<String, LinkedHashMap<String, Integer>> entry : data.entrySet()) {
                String month = entry.getKey();
                LinkedHashMap<String, Integer> dishes = entry.getValue();

                CategoryAxis xAxis = new CategoryAxis();
                NumberAxis yAxis = new NumberAxis();
                BarChart<String,Number> monthChart = new BarChart<String,Number>(xAxis,yAxis);
                monthChart.setTitle(month);
                xAxis.setLabel("Piatto");
                yAxis.setLabel("N° ordini");
                XYChart.Series series = new XYChart.Series();
                for (Map.Entry<String, Integer> dish : dishes.entrySet()) {
                    String dishName = dish.getKey();
                    int qty = dish.getValue();
                    series.getData().add(new XYChart.Data(dishName, qty));
                }
                monthChart.getData().add(series);
                this.monthCharts.put(index, monthChart);
                index ++;
            }
        }
        this.moreOrderedDishesChart.getChildren().add(this.monthCharts.get(0));
        index = 0;
    }

    @FXML
    void followingMonth(MouseEvent event) {
        this.moreOrderedDishesChart.getChildren().clear();
        index = (index + 1) % this.monthCharts.size();
        this.moreOrderedDishesChart.getChildren().add(this.monthCharts.get(index));
        System.out.println(index);
    }

    @FXML
    void previousMonth(MouseEvent event) {
        this.moreOrderedDishesChart.getChildren().clear();
        index = (index - 1) % this.monthCharts.size();
        this.moreOrderedDishesChart.getChildren().add(this.monthCharts.get(index));
        System.out.println(index);
    }

    private void makeRevenueUtilitiesComparisonChart() {
    }

    private void makeRevenueOrdersComparisonChart() {
    }

    private void setAnchor(Node node, Double value){
        AnchorPane.setTopAnchor(node, value);
        AnchorPane.setBottomAnchor(node, value);
        AnchorPane.setLeftAnchor(node, value);
        AnchorPane.setRightAnchor(node, value);
    }
}
