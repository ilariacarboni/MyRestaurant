/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 */
public class AddProductPaneController extends BaseView implements Initializable {

    @FXML
    private Label barcodeLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label qtyLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private TextField barcodeTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField qtyTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private Label priceLabel1;
    @FXML
    private Label priceLabel11;
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
    private void addProductBtnClicked(ActionEvent event) {
        
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
            boolean res = controllerForView.save(product, "product");
            if(!res){
                Alert a = new Alert(AlertType.WARNING);
                a.setContentText("Il prodotto non è stato inserito!");
                a.show();
                resetTextFields();
            }else{
                Alert a = new Alert(AlertType.INFORMATION);
                a.setContentText("Il prodotto è stato inserito correttamente!");
                a.show();
                resetTextFields();
            }
        }  
    }
    
    private void addListenersToTextFields(){
        barcodeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                barcodeTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        qtyTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                qtyTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        priceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*\\.?\\d*")) {
                priceTextField.setText(newValue.replaceAll("[^\\d*\\.?\\d*]", ""));
                }
            }
        });
    }
    
    private void setCategoryComboBox(){
        ArrayList<HashMap<String, Object>> categoryList = controllerForView.getAll("category");
        ArrayList categoryNameList = new ArrayList();
        categoryList.forEach((category) -> {
            categoryNameList.add(category.get("name"));
        });
        categoryComboBox.getItems().addAll(categoryNameList);
    }
    
    private void setSupplierComboBox(){
        ArrayList<HashMap<String, Object>> supplierList = controllerForView.getAll("supplier");
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
    
}
