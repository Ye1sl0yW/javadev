package tn.shoppy.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import tn.shoppy.services.Pdf;

/**
 * This controller class is used to control the whole dashboard window. 
 * (Precisely controlling the tabs and handeleing their events.)
 * WARNING: This controller is not used to handle events INSIDE the tab panes. 
 * To do that, refer to each tab's specific controller class.
 * @author Haroun
 */
public class DashboardController implements Initializable {

    @FXML
    private Tab tab_product;

    /**
     * UI attributes
     */


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    private void pdf_btn(ActionEvent event) {
                if (tab_product.isSelected()) {
            Pdf.PdfListeProduits();

    
    }
    
}

}
