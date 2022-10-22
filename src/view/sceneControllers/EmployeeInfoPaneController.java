/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import business.EmployeeManager;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import view.utils.LocatedImage;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class EmployeeInfoPaneController extends BaseView implements Initializable {
     @FXML
    private Label beginLbl;

    @FXML
    private Label cfLbl;

    @FXML
    private VBox chosenEmployee;

    @FXML
    private Button deleteBtn;

    @FXML
    private Label empNameLbl;

    @FXML
    private Label empSurnameLbl;

    @FXML
    private ImageView employeeImg;

    @FXML
    private Label endLbl;

    @FXML
    private Button modifyBtn;

    @FXML
    private Label wageLbl;
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
      /* if (menu.get("image") != null){
             this.dishImg.setImage(new LocatedImage((String) employeeInfo.get("image"))); 
        }else{
            this.dishImg.setImage(new Image(new File(this.DEFAULT_IMAGE_PATH).toURI().toString()));
        }*/
    }
    
}
