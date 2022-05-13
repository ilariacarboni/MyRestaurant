/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 */
public class AddProductPaneController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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

    @FXML
    private void addProductBtnClicked(ActionEvent event) {
        
        //controllo che siano stati inseriti tutti i campi
        if(barcodeTextField.getText().isEmpty() || qtyTextField.getText().isEmpty() || 
           nameTextField.getText().isEmpty() || priceTextField.getText().isEmpty()){
            
            System.out.println("campo vuoto");
        }else{
            int barcode = Integer.parseInt(barcodeTextField.getText());
            String name = nameTextField.getText();
            int qty = Integer.parseInt(qtyTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());

            Product p = new Product(barcode, name, qty, price);
            ControllerForView.getInstance().save(p);
            System.out.println("aggiunto");
        }
        
        
    }
    
    
    
}
