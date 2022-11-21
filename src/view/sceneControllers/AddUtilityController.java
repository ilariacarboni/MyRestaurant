package view.sceneControllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

import business.UtilityManager;
import java.io.IOException;
import javafx.scene.control.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import view.utils.CustomDialog;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class AddUtilityController  extends BaseView implements Initializable {

    public DatePicker utilityDate;
    public TextField importoutenzaTxt;
    public Button insertUtilityBtn;
    public TextField nfatturaTxt;
    public ChoiceBox<String> utenzeChoiceBox;
    public VBox anchorpaneAddUtility;

    private CommunicationController commController = CommunicationController.getInstance();
    private UtilityManager utilityManager = new UtilityManager();
    private String[] tipo_utenza = {"elettricità", "acqua", "gas"};
    HashMap<String, Object> utility = new HashMap<String, Object>();
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        utenzeChoiceBox.getItems().addAll(tipo_utenza);
        utenzeChoiceBox.setOnAction(this::getUtilityType);

    }    
        
    @FXML
    void AddUtilityBtnClicked(ActionEvent event) throws IOException {
        LocalDate d = utilityDate.getValue();
                String date = "";
                if(d != null){
                  date = d.toString();
                }
        if(nfatturaTxt.getText().isEmpty() || importoutenzaTxt.getText().isEmpty() || date.isEmpty() || utenzeChoiceBox.getValue().isEmpty() ){
            
            System.out.println("campo vuoto");
            
        }else{
		int numberId = Integer.parseInt(nfatturaTxt.getText());
            	double  total= Double.parseDouble(importoutenzaTxt.getText());
                String type = utenzeChoiceBox.getValue();
                
                utility.put("numberId", numberId);
                utility.put("total", total);
                utility.put("type", type);
                utility.put("date", date);
                
                boolean res = this.utilityManager.save(utility);
                if(!res){
                    String text = "La bolletta non è stata inserita!";
                    dialog.setInfo(text, CustomDialog.TYPE_ERROR);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Errore !");
                    resetTextFields();
                }else{
                    String text = "La bolletta è stata inserita!";
                    dialog.setInfo(text, CustomDialog.TYPE_SUCCESS);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Inserimento bolletta");
                    resetTextFields();
                    UtilitiesPaneController utilitiesContr = commController.getUtilitiesPaneController();
                    utilitiesContr.addUtility(utility,0);
                }
        }

    }
   
        private void getUtilityType(ActionEvent event) {
        String tipologia_utenza = utenzeChoiceBox.getValue();
    }
        
        private void resetTextFields() {
        nfatturaTxt.setText("");
        importoutenzaTxt.setText("");
        utilityDate.setValue(null);
        utenzeChoiceBox.valueProperty().set(null);
    }
        
        public void closePaneBtnClicked(MouseEvent mouseEvent){
        commController.getDashboardController().setRightPane(null);
        commController.getUtilitiesPaneController().showAddUtilityBtn();
    }
    
}
