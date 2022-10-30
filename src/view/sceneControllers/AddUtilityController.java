package view.sceneControllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

import business.UtilityManager;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import view.sceneControllers.CommunicationController;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import view.utils.CustomDialog;
import view.utils.LocatedImage;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class AddUtilityController  extends BaseView implements Initializable {

    public TextField datautenzaTxt;
    public TextField importoutenzaTxt;
    public Button insertUtilityBtn;
    public TextField nfatturaTxt;
    public ChoiceBox<String> utenzeChoiceBox;
    public VBox anchorpaneAddUtility;

    private CommunicationController commController = CommunicationController.getInstance();
    private UtilityManager utilityManager = new UtilityManager();
    private String[] tipo_utenza = {"elettricità", "acqua", "gas"};
   

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
                
                
                boolean res = this.utilityManager.save(utility);
                if(!res){
                    String text = "Il piatto non è stato inserito!";
                    CustomDialog dialog = new CustomDialog(text, CustomDialog.TYPE_ERROR);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Errore !");

                    resetTextFields();
                }else{
                    String text = "Il piatto è stato inserito correttamente!";
                    CustomDialog dialog = new CustomDialog(text, CustomDialog.TYPE_SUCCESS);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Inserimento Piatto");

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
