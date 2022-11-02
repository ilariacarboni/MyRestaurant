package view.sceneControllers;

import business.ChartsManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.chart.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import view.utils.customCharts.CustomAreaChart;
import view.utils.CustomGridPane;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ChartPaneController extends BaseView implements Initializable {

    public ScrollPane container;
    public BorderPane chartBorderPane;
    public AnchorPane moreOrderedDishesChart;

    private ChartsManager chartsManager = new ChartsManager();
    private int index = 0;
    private HashMap<Integer, BarChart> monthCharts = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        container.setFitToHeight(true);
        container.setFitToWidth(true);

        CustomGridPane gridPane = new CustomGridPane(2);

        HBox revenueTrendChart               = this.makeRevenueTrendChart();
        HBox warehouseCompositionChart       = this.makeWarehouseCompositionChart();
        HBox utilityCostsChart               = this.makeUtilityCostsChart();
        HBox moreOrderedDishesChart          = this.makeMoreOrderedDishesChart();
        HBox revenueOrdersComparisonChart    = this.makeRevenueOrdersComparisonChart();
        HBox revenueUtilitiesComparisonChart = this.makeRevenueUtilitiesComparisonChart();

        gridPane.add(revenueTrendChart, 0,0);
        gridPane.add(warehouseCompositionChart, 1, 0);
        gridPane.add(utilityCostsChart, 0, 1);
        gridPane.add(moreOrderedDishesChart, 1, 1);
        gridPane.add(revenueOrdersComparisonChart, 0, 2);
        gridPane.add(revenueUtilitiesComparisonChart, 1, 2);

        gridPane.setBreakPoint(0, 1250, 1);
        gridPane.setBreakPoint(1250, Double.MAX_VALUE, 2);
        gridPane.startToListenForAdjustments(commController.getStage());
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(5, 20, 20, 20));
        container.setContent(gridPane);
    }

    private HBox makeRevenueTrendChart(){
        HBox res = new HBox();
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
        }
        res.getChildren().add(revenueChart);
        res.setAlignment(Pos.CENTER);
        res.setMinHeight(400);
        return res;
    }

    private HBox makeWarehouseCompositionChart(){
        HBox res = new HBox();
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
        res.getChildren().add(chart);
        res.setMinHeight(400);
        res.setAlignment(Pos.CENTER);
        return res;
    }

    private HBox makeUtilityCostsChart(){
        HBox res = new HBox();
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
        }
        res.getChildren().add(utilityChart);
        res.setMinHeight(400);
        res.setAlignment(Pos.CENTER);
        return res;
    }

    private HBox makeMoreOrderedDishesChart(){
        HBox res = new HBox();
        BorderPane borderPane = new BorderPane();
        this.moreOrderedDishesChart = new AnchorPane();
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
                series.setName("Piatti più ordinati del mese");
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

        borderPane.setCenter(this.moreOrderedDishesChart);

        ImageView previousChartButton = new ImageView(imagesProvider.getBackIcon());
        previousChartButton.setFitHeight(32);
        previousChartButton.setFitWidth(32);
        previousChartButton.setCursor(Cursor.HAND);
        previousChartButton.setOnMouseClicked((e) -> {
            this.previousMonth();
        });

        ImageView followingChartButton = new ImageView(imagesProvider.getNextIcon());
        followingChartButton.setFitHeight(32);
        followingChartButton.setFitWidth(32);
        followingChartButton.setCursor(Cursor.HAND);
        followingChartButton.setOnMouseClicked((e) -> {
            this.followingMonth();
        });

        borderPane.setLeft(previousChartButton);
        BorderPane.setAlignment(previousChartButton, Pos.CENTER);
        borderPane.setRight(followingChartButton);
        BorderPane.setAlignment(followingChartButton, Pos.CENTER);

        index = 0;
        res.getChildren().add(borderPane);
        res.setMinHeight(400);
        res.setAlignment(Pos.CENTER);
        return res;
    }

    void followingMonth() {
        this.moreOrderedDishesChart.getChildren().clear();
        index = (index + 1) % this.monthCharts.size();
        this.moreOrderedDishesChart.getChildren().add(this.monthCharts.get(index));
    }

    void previousMonth() {
        this.moreOrderedDishesChart.getChildren().clear();
        index = index - 1;
        if(index < 0){
            index = this.monthCharts.size() - 1;
        }
        this.moreOrderedDishesChart.getChildren().add(this.monthCharts.get(index));
    }

    private HBox makeRevenueUtilitiesComparisonChart() {
        HBox res = new HBox();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LinkedHashMap<String, Double> revenueData = this.chartsManager.getRevenueData();
        LinkedHashMap<String, Double> utilityData = this.chartsManager.getUtilityCostPerMonthData();

        CustomAreaChart chart = new CustomAreaChart(xAxis, yAxis);
        xAxis.setLabel("mese");

        XYChart.Series series1 = new XYChart.Series();
        for (Map.Entry<String, Double> revenue : revenueData.entrySet()) {
            String month = revenue.getKey();
            Double value = revenue.getValue();
            series1.getData().add(new XYChart.Data(month, value));
        }

        XYChart.Series series2 = new XYChart.Series();
        for (Map.Entry<String, Double> utility : utilityData.entrySet()) {
            String month = utility.getKey();
            Double value = utility.getValue();
            series2.getData().add(new XYChart.Data(month, value));
        }

        chart.setTitle("confronto incassi/spesa utenze");

        series1.setName("incassi");
        series2.setName("spese utenze");
        chart.addSeries(series1, series2);
        res.getChildren().add(chart);
        res.setMinHeight(400);
        res.setAlignment(Pos.CENTER);
        return res;
    }

    /**
     * grafico confronto incassi-spese ordini
     */
    private HBox makeRevenueOrdersComparisonChart() {
        HBox res = new HBox();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LinkedHashMap<String, Double> revenueData = this.chartsManager.getRevenueData();
        LinkedHashMap<String, Double> ordersData = this.chartsManager.getMonthlyOrderExpense();

        CustomAreaChart chart = new CustomAreaChart(xAxis, yAxis);
        xAxis.setLabel("mese");

        XYChart.Series series1 = new XYChart.Series();
        for (Map.Entry<String, Double> revenue : revenueData.entrySet()) {
            String month = revenue.getKey();
            Double value = revenue.getValue();
            series1.getData().add(new XYChart.Data(month, value));
        }

        XYChart.Series series2 = new XYChart.Series();
        for (Map.Entry<String, Double> order : ordersData.entrySet()) {
            String month = order.getKey();
            Double value = order.getValue();
            series2.getData().add(new XYChart.Data(month, value));
        }

        chart.setTitle("confronto incassi/spesa ordini");

        series1.setName("incassi");
        series2.setName("spese ordini");
        chart.addSeries(series1, series2);
        res.getChildren().add(chart);
        res.setMinHeight(400);
        res.setAlignment(Pos.CENTER);
        return res;
    }

}
