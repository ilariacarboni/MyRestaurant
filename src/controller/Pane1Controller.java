/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class Pane1Controller implements Initializable {

    @FXML
    private PieChart pieChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<PieChart.Data> pieChartData =
               FXCollections.observableArrayList(
               new PieChart.Data("1", 13),
               new PieChart.Data("2", 25),
               new PieChart.Data("3", 10),
               new PieChart.Data("4", 22),
               new PieChart.Data("5", 30));
        
        pieChart.setData(pieChartData);
        
    }    
    
}
