package tn.shoppy.controller.SellerInterface;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import static tn.shoppy.controller.ShopController.sessionShop;
import tn.shoppy.model.Shop;
import tn.shoppy.utils.ConnectionDB;

/**
 * This controller class is used to control the whole seller interface window. 
 * (Precisely controlling the tabs and handeleing their events.)
 * WARNING: This controller is not used to handle events INSIDE the tab panes. 
 * To do that, refer to each tab's specific controller class.
 * @author Haroun
 */
public class SellerInterfaceController implements Initializable {

    Connection cnx = ConnectionDB.getCnx();
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(sessionShop);
//        Setting the controller for the offer tab ( unused)
        FXMLLoader offerLoader = new FXMLLoader(getClass().getResource("/tn/shoppy/view/SellerInterface/OfferTab.fxml"));
        SellerOfferController oc = new SellerOfferController(sessionShop);
        offerLoader.setControllerFactory(p -> oc);
        
//        Setting the controller for the products tab (unused)
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/tn/shoppy/view/SellerInterface/ProductTab.fxml"));
        SellerProductController pc = new SellerProductController(sessionShop);
        productLoader.setControllerFactory(x -> pc);

        
    }


}
