package view.utils.customCharts;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class CustomChartLabel extends StackPane {

    CustomChartLabel(Object value, double translateX, double translateY){
        final Label label = createLabel(value);
        label.setTranslateX(translateX);
        label.setTranslateY(translateY);
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

    private Label createLabel( Object value){
        Label label = new Label(value.toString() );
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 16;");
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

        return label;
    }
}
