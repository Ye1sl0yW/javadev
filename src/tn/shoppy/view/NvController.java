/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.view.omar;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import tn.shoppy.model.gestionCommandes.OrderStat;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class NvController implements Initializable {

    @FXML
    private PieChart pie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

      @FXML
    private void stat(MouseEvent event) {
        OrderStat c=new OrderStat();
        pie.setData(c.Stats());
    }
    
}
