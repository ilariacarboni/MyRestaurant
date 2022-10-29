package view.sceneControllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderSearchController extends BaseView implements Initializable {

    public AnchorPane numberLabelContainer;
    public TextField numberSearchBar;
    public TextField dateSearchBar;
    public TextField supplierSearchBar;
    public AnchorPane dateLabelContainer;
    public AnchorPane supplierLabelContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commController.setOrderSearchController(this);
    }

}

