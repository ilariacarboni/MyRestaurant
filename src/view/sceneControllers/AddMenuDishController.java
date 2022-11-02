package view.sceneControllers;

import business.CourseManager;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import business.MenuManager;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import view.utils.CustomDialog;

/**
 * FXML Controller class
 *
 *
 */

public class AddMenuDishController extends BaseView implements Initializable {
    public Label categoriaLbl;
    public ChoiceBox<String> categorieChoicebox;
    public Button insertdishBtn;
    public Label nomeLbl;
    public TextField nomeTxt;
    public Label prezzoLbl;
    public TextField prezzoTxt;
    public Label titoloLbl;
    private MenuManager menuManager = new MenuManager();
    private CourseManager courseManager = new CourseManager();
    private String[] categorie = {"Antipasti", "Primi", "Secondi", "Contorni", "Dolci", "Bevande"};


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*nomeTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                nomeTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        
        prezzoTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                prezzoTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });*/
        
        categorieChoicebox.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                categorieChoicebox.setValue(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        categorieChoicebox.getItems().addAll(categorie);
        categorieChoicebox.setOnAction(this::getSelectedCategory);

        
    }    

    @FXML
    private void addMenuDishBtnClicked(ActionEvent event) {
        
        //controllo che siano stati inseriti tutti i campi
       if(nomeTxt.getText().isEmpty() || prezzoTxt.getText().isEmpty() || categorieChoicebox.getValue().isEmpty() ){
            
            System.out.println("campo vuoto");
            
        }else{
		String nameDish = nomeTxt.getText();
            	double price = Double.parseDouble(prezzoTxt.getText());
                String course = categorieChoicebox.getValue(); //param fisso per portata selezionata
                

                HashMap<String, Object> menu = new HashMap<String, Object>();
                menu.put("nameDish", nameDish);
                menu.put("price", price);
                menu.put("course", course);
                
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

}
