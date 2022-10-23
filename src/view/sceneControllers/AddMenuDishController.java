package view.sceneControllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import business.MenuManager;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.image.Image;
import view.utils.CustomDialog;
import view.utils.LocatedImage;

/**
 * FXML Controller class
 *
 *
 */

public class AddMenuDishController extends BaseView implements Initializable {
    
    @FXML
    private Label categoriaLbl;

    @FXML
    private ChoiceBox<String> categorieChoicebox;
    private String[] categorie = {"Antipasti", "Primi", "Secondi", "Contorni", "Dolci", "Bevande"};

    @FXML
    private Button insertdishBtn;

    @FXML
    private Label nomeLbl;

    @FXML
    private TextField nomeTxt;

    @FXML
    private Label prezzoLbl;

    @FXML
    private TextField prezzoTxt;
    
    @FXML
    private Label titoloLbl;
    private MenuManager menuManager = new MenuManager();
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       /* nomeTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
            	int price = Integer.parseInt(prezzoTxt.getText());
                String category = categorieChoicebox.getValue(); //param fisso per categoria selezionata
                

                HashMap<String, Object> menu = new HashMap<String, Object>();
                menu.put("nameDish", nameDish);
                menu.put("price", price);
                menu.put("category", category);
                
                boolean res = this.menuManager.saveDish(menu);
                if(!res){
                    String text = "Il piatto non è stato inserito";
                    Image img = new LocatedImage("/view/style/img/dialog-icons/warning.png");
                    CustomDialog dialog = new CustomDialog(text, img);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Attenzione !");

                    resetTextFields();
                }else{
                    String text = "Il piatto è stato inserito correttamente";
                    Image img = new LocatedImage("/view/style/img/dialog-icons/check.png");
                    CustomDialog dialog = new CustomDialog(text, img);
                    dialog.setButtons(ButtonType.OK);
                    dialog.showAndWait("Inserimento Piatto");
                    resetTextFields();
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
