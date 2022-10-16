package view.sceneControllers;

import business.OrderManager;
import business.ProductManager;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AddOrderPaneController extends BaseView implements Initializable {

    private OrderManager orderManager = new OrderManager();
    private ProductManager productManager = new ProductManager();
    public AnchorPane addOrderPane;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField productField;
    @FXML
    private Spinner<Integer> qtyField;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commController.setAddOrderPaneController(this);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(1);
        qtyField.setValueFactory(valueFactory);
        SuggestionProvider suggestionProvider = SuggestionProvider.create(new ArrayList());
        new AutoCompletionTextFieldBinding<>(productField, suggestionProvider);
        this.addListenerForAutocompletion(suggestionProvider);
    }

    public void refresh(){
        dateField.setValue(null);
        productField.setText("");
        qtyField.getValueFactory().setValue(1);
    }

    public void insertOrderBtnClicked(MouseEvent event) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        LocalDate d = dateField.getValue();
        String date = "";
        if(d != null){
            date = d.toString();
        }
        String productName = productField.getText();
        int qty = qtyField.getValue();
        if(date.isEmpty() || productName.isEmpty()){
            String alertText = "Tutti i campi devono essere riempiti";

            a.setContentText(alertText);
            a.show();
        }else{
            boolean res = this.orderManager.insertOrder(date, productName, qty);
            if(!res){
                a.setContentText("Ordine non creato");
                a.show();
            }else{
                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setContentText("Ordine aggiunto con successo");
                a.show();
                commController.getOrderPaneController().updateOrders();
                this.refresh();
            }
        }
    }


    private void addListenerForAutocompletion(SuggestionProvider sp){
        productField.textProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<String> suggestions = new ArrayList<>();
            ArrayList<HashMap<String, Object>> products = this.productManager.getProductsWithNameLike(newValue);
            for(HashMap<String, Object> product : products){
                suggestions.add(product.get("name").toString());
            }
            List<String> newSuggestions = new ArrayList(suggestions);
            sp.clearSuggestions();
            sp.addPossibleSuggestions(newSuggestions);
        });
    }

    public void hideAddOrderPane(MouseEvent mouseEvent) {
        commController.getDashboardController().setRightPane(null);
        commController.getOrderPaneController().showAddOrderBtn();
    }

    public void setOrderInfo(HashMap<String, Object> info){
        if(info.containsKey("date")){
            this.dateField.setValue((LocalDate) info.get("date"));
        }
        if(info.containsKey("name")){
            this.productField.setText(info.get("name").toString());
        }
        if(info.containsKey("qty")){
            this.qtyField.getValueFactory().setValue((int)info.get("qty"));
        }
    }

    public AnchorPane getAddOrderPane(){return this.addOrderPane;}
}