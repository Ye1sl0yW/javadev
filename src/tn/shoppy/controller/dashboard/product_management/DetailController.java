/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.controller.dashboard.product_management;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author anas fattoum
 */
public class DetailController implements Initializable {

    @FXML
    private Label NameProductLabel;
    @FXML
    private Label QuantityProductLabel;
    @FXML
    private Label PriceProductLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NameProductLabel.setText(ProductController.prod.getNom());
        QuantityProductLabel.setText(ProductController.prod.getQuantite()+"");
        PriceProductLabel.setText(ProductController.prod.getPrix()+"");
    }    
    
}
