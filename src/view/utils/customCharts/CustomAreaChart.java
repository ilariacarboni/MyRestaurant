package view.utils.customCharts;

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
        }
        this.getData().addAll(series);
    }

}
