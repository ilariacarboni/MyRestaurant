/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.sceneControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import business.CategoryManager;
import business.ProductManager;
import business.SupplierManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 */
public class AddProductPaneController extends BaseView implements Initializable {

    private ProductManager productManager = new ProductManager();
    private CategoryManager categoryManager = new CategoryManager();
    private SupplierManager supplierManager = new SupplierManager();
    @FXML
    private TextField barcodeTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField qtyTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ComboBox<String> supplierComboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addListenersToTextFields();
        setCategoryComboBox();
        setSupplierComboBox();
    }    

    @FXML
    private void addProductBtnClicked(MouseEvent event){
        
        //controllo che siano stati inseriti tutti i campi
        if(barcodeTextField.getText().isEmpty() || qtyTextField.getText().isEmpty() || 
           nameTextField.getText().isEmpty() || priceTextField.getText().isEmpty()){
            
            Alert a = new Alert(AlertType.WARNING);
            a.setContentText("Non ci possono essere campi vuoti");
            a.show();
                
        }else{
            int barcode = Integer.parseInt(barcodeTextField.getText());
            String name = nameTextField.getText();
            int qty = Integer.parseInt(qtyTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            String supplier = supplierComboBox.getValue();
            String category = categoryComboBox.getValue();

            HashMap<String, Object> product = new HashMap<String, Object>();
            product.put("barcode", barcode);
            product.put("name", name);
            product.put("qty", qty);
            product.put("price", price);
            product.put("supplier", supplier);
            product.put("category", category);
            boolean res = this.productManager.saveProduct(product);
            HashMap<String, Object> categoryEntity = null;
            ArrayList<HashMap> categories = categoryManager.getFrom(category, "name");
            if(!categories.isEmpty()){
                categoryEntity = categories.get(0);
            }
            if(!res){
                Alert a = new Alert(AlertType.WARNING);
                a.setContentText("Il prodotto non è stato inserito!");
                a.show();
            }else{
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("Il prodotto è stato inserito correttamente!");
                a.show();
                resetTextFields();
                resetTextFields();
                ProductsPaneController productsPaneController = commController.getProductsPaneController();
                productsPaneController.addProduct(product, categoryEntity);
            }
        }  
    }
    
    private void addListenersToTextFields(){
        this.addListener(barcodeTextField, "\\d*");
        this.addListener(qtyTextField, "\\d*");
        this.addListener(priceTextField, "\\d*\\.?\\d*");
    }

    private void addListener(TextField field, String regexToMatch){
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches(regexToMatch)) {
                    String toReplace = "[^"+regexToMatch+"]";
                    field.setText(newValue.replaceAll(toReplace, ""));
                }
            }
        });
    }
    
    private void setCategoryComboBox(){
        ArrayList<HashMap<String, Object>> categoryList = this.categoryManager.getAll();
        ArrayList categoryNameList = new ArrayList();
        categoryList.forEach((category) -> {
            categoryNameList.add(category.get("name"));
        });
        categoryComboBox.getItems().addAll(categoryNameList);
    }
    
    private void setSupplierComboBox(){
        ArrayList<HashMap<String, Object>> supplierList = this.supplierManager.getAll();
        ArrayList supplierNameList = new ArrayList();
        supplierList.forEach((category) -> {
            supplierNameList.add(category.get("name"));
        });
        supplierComboBox.getItems().addAll(supplierNameList);
    }
    
    private void resetTextFields(){
        barcodeTextField.setText("");
        nameTextField.setText("");
        qtyTextField.setText("");
        priceTextField.setText("");
        categoryComboBox.valueProperty().set(null);
        supplierComboBox.valueProperty().set(null);
    }

    public void closePaneBtnClicked(MouseEvent mouseEvent){
        commController.getDashboardController().setRightPane(null);
        commController.getProductsPaneController().refresh();
    }
}
