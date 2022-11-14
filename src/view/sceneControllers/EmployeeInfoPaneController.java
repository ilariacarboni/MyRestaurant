/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import business.EmployeeManager;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import view.utils.CustomDialog;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class EmployeeInfoPaneController extends BaseView implements Initializable {
    public Label beginLbl;
    public Label cfLbl;
    public VBox chosenEmployee;
    public Button deleteBtn;
    public Label empNameLbl;
    public Label empSurnameLbl;
    public ImageView employeeImg;
    public Button modifyBtn;
    public Label wageLbl;
    public DatePicker enddatePicker;

    private EmployeeManager employeeManager;
    private HashMap<String, Object> empInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         commController.setEmployeeInfoController(this);
         this.employeeManager = new EmployeeManager();  
    }    

    public void setChosenEmployee(HashMap<String, Object> employeeInfo) {
       this.empInfo = employeeInfo;
       this.empNameLbl.setText(String.valueOf(employeeInfo.get("name")));
       this.empSurnameLbl.setText(String.valueOf(employeeInfo.get("surname")));
       this.cfLbl.setText(String.valueOf(employeeInfo.get("codice_fiscale")));
       this.wageLbl.setText(String.valueOf(employeeInfo.get("wage")));
       String imagePath = (String)employeeInfo.get("image");
       if (employeeInfo.get("image") != null){
             this.employeeImg.setImage(imagesProvider.getEmployeeImage(employeeInfo.get("codice_fiscale").toString(), imagePath));
        }else{
            this.employeeImg.setImage(imagesProvider.getDefaultEmployeeImage());
        }
    }
    
    @FXML
    private void deleteBtnClicked(ActionEvent event) {
        boolean res = this.employeeManager.deleteEmployee(empInfo);
        
        if(!res){
                String text = "Il dipendente non è stato cancellato";
                dialog.setInfo(text, CustomDialog.TYPE_WARNING);
                dialog.setButtons(ButtonType.OK);
                dialog.showAndWait("Attenzione !");
                enddatePicker.setValue(null);
                }else{
                    String text = "Il dipendete è stato cancellato correttamente";
                    dialog.setInfo(text, CustomDialog.TYPE_SUCCESS);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Cancellazione dipendente");
                    enddatePicker.setValue(null);
                    EmployeesListController emplistContr = commController.getEmployeePaneController();
                    emplistContr.refresh();
                }

    }

    @FXML
    private void modifyBtnClicked(ActionEvent event) {
        
        LocalDate d = enddatePicker.getValue();
        String end_date = "";
        if(d != null){
            end_date = d.toString();
        }
        else{
            end_date = null;
        }
        
        empInfo.put("end_date", end_date);
        
        boolean res = this.employeeManager.updateEmployee(empInfo);
                if(!res){
                    String text = "I dati non sono stati modificati!";
                    dialog.setInfo(text, CustomDialog.TYPE_ERROR);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Errore !");
                    enddatePicker.setValue(null);
                }else{
                    String text = "I dati sono stati modificati!";
                    dialog.setInfo(text, CustomDialog.TYPE_SUCCESS);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Inserimento bolletta");
                    enddatePicker.setValue(null);
                    EmployeesListController emplistContr = commController.getEmployeePaneController();
                    emplistContr.refresh();
                }
    }

    
}
