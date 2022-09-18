package view.utils;

import javafx.beans.NamedArg;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class CustomLineChart extends LineChart<String, Number> {

    public CustomLineChart(@NamedArg("xAxis") CategoryAxis xAxis, @NamedArg("yAxis") NumberAxis yAxis) {
        super(xAxis, yAxis);
        this.setCursor(Cursor.CROSSHAIR);
    }

    public void addData(XYChart.Series series){
        ObservableList<XYChart.Data<Object, Object>> data = series.getData();
        data.forEach((point) -> {
            point.setNode(new pointLabel(point.getYValue()));
        });
        this.getData().add(series);
    }

    class pointLabel extends StackPane {
        pointLabel(Object value){
            final Label label = createLabel(value);
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    getChildren().setAll(label);
                    setCursor(Cursor.NONE);
                    toFront();
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    getChildren().clear();
                    setCursor(Cursor.CROSSHAIR);
                }
            });
        }
    }

    protected Label createLabel(Object value){
        Label label = new Label(value.toString());
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 20;");
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }
}
