package view.sceneControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class EmployeesListController extends BaseView implements Initializable {
    
    @FXML
    private Button dipendente1Btn;

    @FXML
    private GridPane gridPaneEmployees;

    @FXML
    private Label titoloLbl;
    
    @FXML
    private TextField employeeSearchBar;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setEmployeePaneController(this);
    }    
    
}
