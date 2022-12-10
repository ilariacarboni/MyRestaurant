
package view.sceneControllers;

import business.CourseManager;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import business.MenuManager;
import java.io.File;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import view.utils.CustomDialog;

/**
 * FXML Controller class
 *
 *
 */

public class AddMenuDishController extends BaseView implements Initializable {
    public Label categoriaLbl;
    public ChoiceBox<String> categorieChoicebox;
    public AnchorPane insertdishBtn;
    public Label nomeLbl;
    public TextField nomeTxt;
    public Label prezzoLbl;
    public TextField prezzoTxt;
    public Button photoBtn;
    
    private MenuManager menuManager = new MenuManager();
    private CourseManager courseManager = new CourseManager();
    private String[] categorie = {"Antipasti", "Primi", "Secondi", "Contorni", "Dolci", "Bevande"};
    HashMap<String, Object> menu = new HashMap<String, Object>();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        categorieChoicebox.getItems().addAll(categorie);
        categorieChoicebox.setOnAction(this::getSelectedCategory);

    }    

    @FXML
    private void addMenuDishBtnClicked(MouseEvent event) {
        
       if( nomeTxt.getText().isEmpty() || prezzoTxt.getText().isEmpty() || categorieChoicebox.getValue() == null ){
           String text = "Nessun campo può essere vuoto!";
           dialog.setInfo(text, CustomDialog.TYPE_WARNING);
           dialog.setButtons(ButtonType.OK);
           dialog.showAndWait("Attenzione!");
        }else{
		String nameDish = nomeTxt.getText();
            	double price = Double.parseDouble(prezzoTxt.getText());
                String course = categorieChoicebox.getValue(); //param fisso per portata selezionata
                
                
                this.menu.put("nameDish", nameDish);
                this.menu.put("price", price);
                this.menu.put("course", course);
                
                if(menu.get("image")!=null){
                    String imagePath = (String)menu.get("image");
                    menu.put("image", imagePath);
                }
                    
                boolean res = this.menuManager.saveDish(menu);
                HashMap<String, Object> courseEntity = null;
                ArrayList<HashMap> courses = courseManager.getFrom(course, "name");
                if(!courses.isEmpty()){
                    courseEntity = courses.get(0);
                }
                if(!res){
                    String text = "Il piatto non è stato inserito";
                    dialog.setInfo(text, CustomDialog.TYPE_WARNING);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Attenzione !");
                    resetTextFields();
                }else{
                    String text = "Il piatto è stato inserito correttamente";
                    dialog.setInfo(text, CustomDialog.TYPE_SUCCESS);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Inserimento Piatto");
                    resetTextFields();
                    MenuListController menulistContr = commController.getMenuListController();
                    menulistContr.addMenu( menu, courseEntity );
                }
        }
        
    }

    private void getSelectedCategory(ActionEvent event) {
        String category = categorieChoicebox.getValue();
    }

    private void resetTextFields() {
        nomeTxt.setText("");
        prezzoTxt.setText("");
        categorieChoicebox.valueProperty().set(null);
    }
    
    @FXML
    void addPhotoBtnClicked(MouseEvent event) {
        final FileChooser fc = new FileChooser();
        File returnVal = null;
        returnVal = fc.showOpenDialog((Stage)((Node) event.getSource()).getScene().getWindow());
        if(returnVal != null && this.menu != null){
            String customImagePath = returnVal.toURI().getRawPath();
            this.menu.put("image", customImagePath);
        }
    }
    
    public void hideAddDishPane(MouseEvent mouseEvent) {
        commController.getDashboardController().setRightPane(null);
        commController.getMenuPaneController().showAddDishBtn();
    }

}
