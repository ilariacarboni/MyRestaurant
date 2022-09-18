/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import model.entity.Utility;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class AddUtilityController  extends BaseView implements Initializable {
    
    @FXML
    private Label categoriaLbl;

    @FXML
    private Label dataLbl;

    @FXML
    private TextField datautenzaTxt;

    @FXML
    private Label importoLbl;

    @FXML
    private TextField importoutenzaTxt;

    @FXML
    private Button insertUtilityBtn;

    @FXML
    private Label nfatturaLbl;

    @FXML
    private TextField nfatturaTxt;
    
    @FXML
    private Label titoloLbl;
    
    @FXML
    public AnchorPane anchorpaneAddUtility;
    
    private CommunicationController commController = CommunicationController.getInstance();

    @FXML
    private ChoiceBox<String> utenzeChoiceBox;
    private String[] tipo_utenza = {"Energia elettrica", "Acqua", "Gas"};
   

    @FXML
    void AddUtilityBtnClicked(ActionEvent event) {
        if(nfatturaTxt.getText().isEmpty() || importoutenzaTxt.getText().isEmpty() || datautenzaTxt.getText().isEmpty() ||utenzeChoiceBox.getValue().isEmpty() ){
            
            System.out.println("campo vuoto");
            
        }else{
		int numberId = Integer.parseInt(nfatturaTxt.getText());
            	int  total= Integer.parseInt(importoutenzaTxt.getText());
                String type = utenzeChoiceBox.getValue();
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                String utility_date_string = datautenzaTxt.getText();
                LocalDate date = LocalDate.parse(utility_date_string, formatter);
                

                HashMap<String, Object> utility = new HashMap<String, Object>();
                utility.put("numberId", numberId);
                utility.put("total", total);
                utility.put("type", type);
                utility.put("date", date);
                
                
                boolean res = controllerForView.save(utility, "utility");
                if(!res){
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText("Il piatto non è stato inserito!");
                    a.show();
                    resetTextFields();
                }else{
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setContentText("Il piatto è stato inserito correttamente!");
                    a.show();
                    resetTextFields();
                }
        }

    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       /* nfatturaTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                nfatturaTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        
        importoutenzaTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                importoutenzaTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
       
        datautenzaTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                datautenzaTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });*/
        
        utenzeChoiceBox.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                utenzeChoiceBox.setValue(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        utenzeChoiceBox.getItems().addAll(tipo_utenza);
        utenzeChoiceBox.setOnAction(this::getUtilityType);

      
    }    
    
        private void getUtilityType(ActionEvent event) {
        String tipologia_utenza = utenzeChoiceBox.getValue(); //return?
    }
        
        private void resetTextFields() {
        nfatturaTxt.setText("");
        importoutenzaTxt.setText("");
        datautenzaTxt.setText("");
        utenzeChoiceBox.valueProperty().set(null);
    }
    
}
