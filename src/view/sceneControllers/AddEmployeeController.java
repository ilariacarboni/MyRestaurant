
package view.sceneControllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

import business.EmployeeManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import view.utils.CustomDialog;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
    public ImageView imgEmployee;
    public TextField iniziocontrattoEmpTxt;
    public Button insertEmployeeBtn;
    public TextField nomeEmpTxt;
    public Label prezzoLbl;
    public Label prezzoLbl1;
    public TextField ruoloEmpTxt;
    public TextField stipendioEmpTxt;
    public Label titoloLbl1;

    private EmployeeManager employeeManager = new EmployeeManager();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgEmployee.setImage(imagesProvider.getEmployeeImage(imagesProvider.EMPLOYEE_DEFAULT_IMAGE_TYPE));
        
    }  
     @FXML
    private void addEmpolyeeBtnClicked(ActionEvent event) {
        
        //controllo che siano stati inseriti tutti i campi
        if(nomeEmpTxt.getText().isEmpty() || stipendioEmpTxt.getText().isEmpty() || cognomeEmpTxt.getText().isEmpty() || codicefEmpTxt.getText().isEmpty() ||
                ruoloEmpTxt.getText().isEmpty() || iniziocontrattoEmpTxt.getText().isEmpty() || finecontrattoEmpTxt.getText().isEmpty()){
            
            System.out.println("campo vuoto");
            
        }else{
		    String codice_fiscale = codicefEmpTxt.getText();
            String name = nomeEmpTxt.getText();
            String surname = cognomeEmpTxt.getText();
            String role = ruoloEmpTxt.getText();
            int wage = Integer.parseInt(stipendioEmpTxt.getText());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            String begin_date_string = iniziocontrattoEmpTxt.getText();
            LocalDate begin_date = LocalDate.parse(begin_date_string, formatter); // passo string a data
            String end_date_string = finecontrattoEmpTxt.getText();
            LocalDate end_date = LocalDate.parse(end_date_string, formatter); // passo stringa a data



            HashMap<String, Object> employee = new HashMap<String, Object>();
            employee.put("codice_fiscale", codice_fiscale);
            employee.put("name", name);
            employee.put("surname", surname);
            employee.put("role", role);
            employee.put("wage", wage);
            employee.put("begin_date", begin_date);
            employee.put("end_date", end_date);
            boolean res = this.employeeManager.saveEmployee(employee);
            String text = "";
            Image img = null;
            String title = "";
            if(!res){
                text = "Il dipendente non è stato inserito!";
            }else{
                text = "Il dipendente è stato inserito correttamente!";
            }
            dialog.setInfo(text, CustomDialog.TYPE_SUCCESS);
            dialog.setButtons(ButtonType.OK);
            dialog.showAndWait(title);
            resetTextFields();
        }
        
    }
    
    private void resetTextFields(){
        codicefEmpTxt.setText("");
        nomeEmpTxt.setText("");
        stipendioEmpTxt.setText("");
        cognomeEmpTxt.setText("");
        ruoloEmpTxt.setText("");
        iniziocontrattoEmpTxt.setText("");  //reset delle date
        finecontrattoEmpTxt.setText("");
    }
    
    
}
