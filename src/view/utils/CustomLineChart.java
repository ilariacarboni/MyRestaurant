package view.utils;

import javafx.beans.NamedArg;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class CustomLineChart extends LineChart<Number, Number> {

    public CustomLineChart(@NamedArg("xAxis") NumberAxis xAxis, @NamedArg("yAxis") NumberAxis yAxis) {
        super(xAxis, yAxis);
        this.setCursor(Cursor.CROSSHAIR);
    }

    public void addData(XYChart.Series series){
        //per ogni elemento in series creare etichetta
        ObservableList<XYChart.Data<Object, Object>> data = series.getData();
        data.forEach((point) -> {
            point.setNode(new pointLabel(point.getYValue()));
        });
        this.getData().add(series);
    }

    class pointLabel extends StackPane {
        pointLabel(Object value){
            final Label label = this.createLabel(value);
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

        private Label createLabel(Object value){
            Label label = new Label(value.toString());
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 20;");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }
    }
}
