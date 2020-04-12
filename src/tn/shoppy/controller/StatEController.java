/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.shoppy.model.Product;
import tn.shoppy.services.ProductService;

/**
 * FXML Controller class
 *
 * @author anas fattoum
 */
public class StatEController implements Initializable {

    @FXML
    private BarChart<String, Integer> BarChart;
    @FXML
    private Button retour;
    @FXML
    private Button affstat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    ProductService ps = new ProductService();

    @FXML
    public void retour(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/Dashboard.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
    }
    @FXML
    public void affstat(ActionEvent event) {
        int q1=0;
        int q2=0;
        int q3=0;
        int q4=0;
        int q5=0;
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
 
         ArrayList <Product> list = (ArrayList <Product>) ps.getAllProducts();

         for (Product p1 : list) {
             if (p1.getPrix()>10000)
             {
                 q1+=p1.getQuantite();
              }else 
                 if (p1.getPrix()<10000 && p1.getPrix()>1000)
                 {
                      q2+=p1.getQuantite();
                 }
                      else if (p1.getPrix()<=1000)
                           q3+=p1.getQuantite();
           
         }
            series.getData().add(new XYChart.Data<>("Produit de luxe" , q1));
            series.getData().add(new XYChart.Data<>("Produit à prix moyen" , q2));
            series.getData().add(new XYChart.Data<>("Produit à prix bas" , q3));

            BarChart.getData().add(series);
            
       
         
    }
}
