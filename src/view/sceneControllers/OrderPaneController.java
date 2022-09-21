package view.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderPaneController extends BaseView implements Initializable {

    //definizione costati per modalità di rendering ordini in sospeso, storico ordini
    //renderizzazione ordini solo dopo aver settato la modalità
    private String renderingMode;
    @FXML
    private Label statusLabel;
    @FXML
    private GridPane ordersContainer;
    //aggiungere componente slider

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.commController.setOrderPaneController(this);
        this.ordersContainer.getChildren().clear();
        try {
            this.insertSearchComponent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMode(){
        //settare modalità
        this.insertOrders();
    }

    private void insertSearchComponent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene/ordersSearchPane.fxml"));
        Node orderSearch = loader.load();
        OrderSearchController orderSearchController = loader.getController();
        this.ordersContainer.add(orderSearch,0,0);
    }

    private void insertOrders(){
        String renderingMode = this.renderingMode;
        //determinare per quale pagina si stanno visualizzando gli ordini
    }
}
