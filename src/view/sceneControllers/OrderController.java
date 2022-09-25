package view.sceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;

public class OrderController {
    public static String numberLabelId = "#numberLabel";
    @FXML
    public Label supplierLabel;
    @FXML
    private AnchorPane numberLabelContainer;
    @FXML
    private Label numberLabel;
    @FXML
    private AnchorPane dateLabelContainer;
    @FXML
    private Label dateLabel;
    @FXML
    private AnchorPane supplierLabelContainer;

    public void setOrderInfo(HashMap<String, Object> info){
        numberLabel.setText("numero: "+info.get("number").toString());
        dateLabel.setText("data: "+info.get("date").toString());
        supplierLabel.setText("fornitore: "+info.get("supplier").toString());
    }
}

