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
    @FXML
    private AnchorPane numberLabelContainer;
    @FXML
    public TextField numberSearchBar;
    @FXML
    public TextField dateSearchBar;
    @FXML
    public TextField supplierSearchBar;
    @FXML
    private AnchorPane dateLabelContainer;
    @FXML
    private AnchorPane supplierLabelContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commController.setOrderSearchController(this);
    }

}

