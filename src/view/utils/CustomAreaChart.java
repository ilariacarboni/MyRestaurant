package view.utils;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Iterator;

public class CustomAreaChart extends AreaChart<String, Number> {

    public CustomAreaChart(Axis axis, Axis axis1) {
        super(axis, axis1);
    }

    public void addSeries(XYChart.Series... series){
        for(XYChart.Series serie : series){
            ObservableList<Data<Object, Object>> data = serie.getData();
            data.forEach((point) ->{
                point.setNode(new areaChartLabel(point.getYValue()));
            });
        }
        this.getData().addAll(series);
    }

    class areaChartLabel extends StackPane {

        areaChartLabel(Object value){
            final Label label = createLabel(value);
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    getChildren().setAll(label);
                    setCursor(Cursor.HAND);
                    toFront();
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent mouseEvent) {
                    getChildren().clear();
                    setCursor(Cursor.DEFAULT);
                }
            });
        }
    }

    protected Label createLabel( Object value){
        Label label = new Label(value.toString() );
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 20;");
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }

}
