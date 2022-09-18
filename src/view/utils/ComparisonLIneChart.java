package view.utils;

import javafx.beans.NamedArg;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;

public class ComparisonLIneChart extends CustomLineChart{

    public ComparisonLIneChart(@NamedArg("xAxis") CategoryAxis xAxis, @NamedArg("yAxis") NumberAxis yAxis) {
        super(xAxis, yAxis);
    }

    @Override
    protected Label createLabel(Object value) {
        //TODO etichetta per la comparazione : serie1 => valore1 serie2=>valore 2
        return super.createLabel(value);
    }
}
