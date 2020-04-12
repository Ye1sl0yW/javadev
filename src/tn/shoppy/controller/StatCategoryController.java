/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author anas fattoum
 */
public class StatCategoryController implements Initializable {

    @FXML
    private Pane PaneView;
    @FXML
    private Button retour;
    @FXML
    private Button PieChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    public void retour(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/Dashboard.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
    }
    


    public void PieChartStat(ActionEvent event) {
    
        PaneView.getChildren().clear();
        ObservableList<PieChart.Data> list=FXCollections.observableArrayList();
        list.add(new PieChart.Data("Sport",10));
        list.add(new PieChart.Data("Clothes",20));
        list.add(new PieChart.Data("PC Gamer",5));
        PieChart pieChart = new PieChart(list);
        PaneView.getChildren().add(pieChart);
        
    }
}
