package view.sceneControllers;

import business.OrderManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddOrderPaneController extends BaseView implements Initializable {

    private OrderManager orderManager = new OrderManager();
    @FXML
    private DatePicker dateField;

    @FXML
    private TextField productField;

    @FXML
    private Spinner<Integer> qtyField;

    @FXML
    private Button insertBtn;

    @FXML
    void insertOrderBtnClicked(MouseEvent event) {
        LocalDate d = dateField.getValue();
        String date = "";
        if(d != null){
            date = d.toString();
        }
        String productName = productField.getText();
        int qty = qtyField.getValue();
        if(date.isEmpty() || productName.isEmpty()){
            String alertText = "Tutti i campi devono essere riempiti";
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(alertText);
            a.show();
        }else{
            this.orderManager.insertOrder(date, productName, qty);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(1);
        qtyField.setValueFactory(valueFactory);

        TextFields.bindAutoCompletion(productField, new String[]{"pippo", "pluto", "paperino"});
    }

    //aggiunta di un listener sul campo prodotto che a ogni carattere inserito fa query per prodotti like testo inserito
    //limit 10 o 15 e riinizializza i dati
}