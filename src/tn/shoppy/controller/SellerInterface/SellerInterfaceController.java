package tn.shoppy.controller.SellerInterface;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import static tn.shoppy.controller.ShopController.sessionShop;
import tn.shoppy.model.Shop;
import tn.shoppy.services.OfferService;
import tn.shoppy.services.ShopService;
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
   
    /**
     * Graphical attributes 
     */
    @FXML
    Label shopOverviewShopNameLabel;
    @FXML
    Label shopOverviewSellerNameLabel;
    //--------
    @FXML
    Label shopOverviewCurrentOffersLabel;
    @FXML
    Label shopOverviewPlannedOffersLabel;
    @FXML
    Label shopOverviewExpiredOffersLabel;
    @FXML
    Label shopOverviewTotalOffersLabel;
    //--------
    @FXML
    Label shopOverviewStockSizeLabel;
    @FXML
    Label shopOverviewStockValueLabel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println(sessionShop);
        ShopService shopService = ShopService.getInstance();
        OfferService offerService = OfferService.getInstance();
        
//        Setting the controller for the offer tab ( unused)
        FXMLLoader offerLoader = new FXMLLoader(getClass().getResource("/tn/shoppy/view/SellerInterface/OfferTab.fxml"));
        SellerOfferController oc = new SellerOfferController(sessionShop);
        offerLoader.setControllerFactory(p -> oc);
        
//        Setting the controller for the products tab (unused)
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/tn/shoppy/view/SellerInterface/ProductTab.fxml"));
        SellerProductController pc = new SellerProductController(sessionShop);
        productLoader.setControllerFactory(x -> pc);
        
//        Initializing GUI elements
        shopOverviewShopNameLabel.setText(sessionShop.getNom());
        shopOverviewSellerNameLabel.setText(shopService.getShopSellerName(sessionShop));
        if(offerService.getAllOffersForOneShop(sessionShop)!=null){
            shopOverviewCurrentOffersLabel.setText(String.valueOf(shopService.getNumberOfCurrentOffersForOneShop(sessionShop)));               
            shopOverviewPlannedOffersLabel.setText(String.valueOf(shopService.getNumberOfPlannedOffersForOneShop(sessionShop)));
            shopOverviewExpiredOffersLabel.setText(String.valueOf(shopService.getNumberOfExpiredOffersForOneShop(sessionShop)));
            shopOverviewTotalOffersLabel.setText(String.valueOf(offerService.getAllOffersForOneShop(sessionShop).size())); 
        }
        shopOverviewStockSizeLabel.setText(String.valueOf(sessionShop.getTaille_stock()));
        shopOverviewStockValueLabel.setText(String.valueOf(ShopService.getInstance().calculateMerchandiseValue(sessionShop))+" TND");
    }

    public void refreshStockValuesAction()
    {
        shopOverviewStockSizeLabel.setText(String.valueOf(ShopService.getInstance().calculateStock(sessionShop)));
        shopOverviewStockValueLabel.setText(String.valueOf(ShopService.getInstance().calculateMerchandiseValue(sessionShop))+" TND");
    }

}