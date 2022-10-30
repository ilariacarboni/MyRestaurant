/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import business.EmployeeManager;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
    public Label endLbl;
    public Button modifyBtn;
    public Label wageLbl;

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
       this.beginLbl.setText(String.valueOf(employeeInfo.get("begin_date")));
       this.endLbl.setText(String.valueOf(employeeInfo.get("end_date")));
       if (employeeInfo.get("image") != null){
             this.employeeImg.setImage(imagesProvider.getEmployeeImage(employeeInfo.get("codice_fiscale").toString()));
        }else{
            this.employeeImg.setImage(imagesProvider.getDefaultEmployeeImage());
        }
    }
    
}
