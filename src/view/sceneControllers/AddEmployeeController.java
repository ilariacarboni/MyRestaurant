
package view.sceneControllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

import business.EmployeeManager;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import view.utils.CustomDialog;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class AddEmployeeController extends BaseView implements  Initializable {

    /**
     * Initializes the controller class.
     */

    public Label categoriaLbl;
    public VBox chosenDishCard;
    public TextField codicefEmpTxt;
    public Label codicefLbl;
    public Label codicefLbl1;
    public Label codicefLbl11;
    public TextField cognomeEmpTxt;
    public TextField finecontrattoEmpTxt;
    public TextField iniziocontrattoEmpTxt;
    public Button insertEmployeeBtn;
    public TextField nomeEmpTxt;
    public Label prezzoLbl;
    public Label prezzoLbl1;
    public ChoiceBox<String> ruoloChoicebox;
    public TextField stipendioEmpTxt;
    public Label titoloLbl1;

    private EmployeeManager employeeManager = new EmployeeManager();
    HashMap<String, Object> employee = new HashMap<String, Object>();
    
    private String[] ruoli = {"cuoco", "cameriere"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruoloChoicebox.getItems().addAll(ruoli);
        ruoloChoicebox.setOnAction(this::getSelectedRole);
        
        
    }  
     @FXML
    private void addEmpolyeeBtnClicked(ActionEvent event) {
        
        //controllo che siano stati inseriti tutti i campi
        if(nomeEmpTxt.getText().isEmpty() || stipendioEmpTxt.getText().isEmpty() || cognomeEmpTxt.getText().isEmpty() || codicefEmpTxt.getText().isEmpty() ||
                ruoloChoicebox.getValue().isEmpty() || iniziocontrattoEmpTxt.getText().isEmpty() || finecontrattoEmpTxt.getText().isEmpty()){
            
            System.out.println("campo vuoto");
            
        }else{
	    String codice_fiscale = codicefEmpTxt.getText();
            String name = nomeEmpTxt.getText();
            String surname = cognomeEmpTxt.getText();
            String role = ruoloChoicebox.getValue(); 
            int wage = Integer.parseInt(stipendioEmpTxt.getText());

            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String begin_date_string = iniziocontrattoEmpTxt.getText();
            //LocalDate begin_date = LocalDate.parse(begin_date_string, formatter); // passo string a data
            String end_date_string = finecontrattoEmpTxt.getText();
           // LocalDate end_date = LocalDate.parse(end_date_string, formatter); // passo stringa a data
            
            employee.put("codice_fiscale", codice_fiscale);
            employee.put("name", name);
            employee.put("surname", surname);
            employee.put("role", role);
            employee.put("wage", wage);
            employee.put("begin_date", begin_date_string);
            employee.put("end_date", end_date_string);
            
           if(employee.get("image")!=null){
                    String imagePath = (String)employee.get("image");
                    employee.put("image", imagePath);
                }
            boolean res = this.employeeManager.saveEmployee(employee);
            if(!res){
                String text = "Il dipendente non è stato inserito!";
            }else{
                
            String text = "Il dipendente è stato inserito correttamente!";
            dialog.setInfo(text, CustomDialog.TYPE_SUCCESS);
            dialog.setButtons(ButtonType.OK);
            dialog.showAndWait("Inserimento dipendente");
            resetTextFields();
            EmployeesListController emplistContr = commController.getEmployeePaneController();
            emplistContr.addEmployee(employee);
            }
        }
        
    }
    
    private void resetTextFields(){
        codicefEmpTxt.setText("");
        nomeEmpTxt.setText("");
        stipendioEmpTxt.setText("");
        cognomeEmpTxt.setText("");
        ruoloChoicebox.valueProperty().set(null);
        iniziocontrattoEmpTxt.setText("");  //reset delle date
        finecontrattoEmpTxt.setText("");
    }
    
    @FXML
    void addPhotoBtnClicked(MouseEvent event) {
        final FileChooser fc = new FileChooser();
        File returnVal = null;
        returnVal = fc.showOpenDialog((Stage)((Node) event.getSource()).getScene().getWindow());
        if(returnVal != null && this.employee != null){
            String customImagePath = returnVal.toURI().getRawPath();
            this.employee.put("image", customImagePath);
            this.employeeManager.updateEmployee(this.employee);
        }
    }
        
        private void getSelectedRole(ActionEvent event) {
        String role = ruoloChoicebox.getValue();
    }
    
    
}
