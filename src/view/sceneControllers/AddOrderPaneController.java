package view.sceneControllers;

import business.OrderManager;
import business.ProductManager;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.entity.Product;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AddOrderPaneController extends BaseView implements Initializable {

    private OrderManager orderManager = new OrderManager();
    private ProductManager productManager = new ProductManager();
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
        SuggestionProvider suggestionProvider = SuggestionProvider.create(new ArrayList());
        new AutoCompletionTextFieldBinding<>(productField, suggestionProvider);
        this.addListenerForAutocompletion(suggestionProvider);
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
}