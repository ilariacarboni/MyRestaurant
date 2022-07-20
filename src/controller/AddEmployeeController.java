/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import entity.Employee;
import entity.Menu;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class AddEmployeeController extends BaseView implements  Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private TextField codicefEmpTxt;

    @FXML
    private Label codicefLbl;

    @FXML
    private TextField cognomeEmpTxt;

    @FXML
    private Label cognomeLbl;

    @FXML
    private TextField finecontrattoEmpTxt;

    @FXML
    private Label finecontrattoLbl;

    @FXML
    private Label iniziocontrattoLbl;

    @FXML
    private Button insertEmployeeBtn;

    @FXML
    private TextField iniziocontrattoEmpTxt;

    @FXML
    private TextField nomeEmpTxt;

    @FXML
    private Label nomeLbl;

    @FXML
    private TextField ruoloEmpTxt;

    @FXML
    private Label ruoloLbl;

    @FXML
    private TextField stipendioEmpTxt;

    @FXML
    private Label stipendioLbl;
    
    @FXML
    private Label titoloLbl;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       /* nomeEmpTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                nomeEmpTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        
        stipendioEmpTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                stipendioEmpTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
         
        cognomeEmpTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                cognomeEmpTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
         ruoloEmpTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                ruoloEmpTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        
        codicefEmpTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                codicefEmpTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
         
        iniziocontrattoEmpTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                iniziocontrattoEmpTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        finecontrattoEmpTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                finecontrattoEmpTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });*/
        
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
                boolean res = controllerForView.save(employee, "employee");
                if(!res){
                    Alert a = new Alert(AlertType.WARNING);
                    a.setContentText("Il dipendente non è stato inserito!");
                    a.show();
                    resetTextFields();
                }else{
                    Alert a = new Alert(AlertType.INFORMATION);
                    a.setContentText("Il dipendente è stato inserito correttamente!");
                    a.show();
                    resetTextFields();
                }
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


