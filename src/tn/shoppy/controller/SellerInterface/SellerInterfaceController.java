package tn.shoppy.controller.SellerInterface;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import static tn.shoppy.controller.ShopController.sessionShop;
import tn.shoppy.model.Shop;
import tn.shoppy.services.NodeToPDFExport;
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
    //--------
    @FXML
    Button sellerExportToPDFButton;
    @FXML
    AnchorPane sellerOverviewPane;
    @FXML
    Button shopOverviewCalculateStockValuesButton;
    
    @FXML
    WebView sellerWebView;
    
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
        
        
        WebEngine webEngine = sellerWebView.getEngine();
//        webEngine.load("http://google.com");
//        webEngine.load("http://localhost/validation_finale/pi_dev_2020/web/app_dev.php/");
        URL url = this.getClass().getResource("/tn/shoppy/services/CurrencyConverter.html");
        webEngine.load(url.toString());

    }

    public void refreshStockValuesAction()
    {
        shopOverviewStockSizeLabel.setText(String.valueOf(ShopService.getInstance().calculateStock(sessionShop)));
        shopOverviewStockValueLabel.setText(String.valueOf(ShopService.getInstance().calculateMerchandiseValue(sessionShop))+" TND");
    }
    
    public void exportToPDFAction() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
                //Node.setVisible(boolean)
        sellerExportToPDFButton.setVisible(false);
        shopOverviewCalculateStockValuesButton.setVisible(false);
        sellerWebView.setVisible(false);    
        
        NodeToPDFExport.printNode(sellerOverviewPane);
        
        shopOverviewCalculateStockValuesButton.setVisible(true);
        sellerExportToPDFButton.setVisible(true);
        sellerWebView.setVisible(true);
    }
    


}
