package view.utils.customCharts;

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
    }

    public void addData(XYChart.Series series){
        ObservableList<XYChart.Data<Object, Object>> data = series.getData();
        data.forEach((point) -> {
            int index = data.indexOf(point);
            double translationX, translationY;
            translationX = translationY = -20;
            if(index == 0){
                translationX = 20;
            }
            if(index == 1){
                translationX = 10;
            }
            point.setNode(new CustomChartLabel(point.getYValue(), translationX, translationY));
        });
        this.getData().add(series);
    }

}
