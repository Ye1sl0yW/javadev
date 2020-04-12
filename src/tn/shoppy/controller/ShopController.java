package tn.shoppy.controller;

import tn.shoppy.controller.SellerInterface.SellerInterfaceController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tn.shoppy.model.Shop;
import tn.shoppy.services.ExcelExport;
import tn.shoppy.services.ShopService;
import tn.shoppy.utils.ConnectionDB;
import tn.shoppy.utils.InputCheck;

/**
 *
 * @author Haroun
 */
public class ShopController implements Initializable {
    Connection cnx = ConnectionDB.getCnx();
    public static Shop sessionShop;

    /**
     * UI attributes
     */
    @FXML
    private TableView<Shop> shopTable;    
    @FXML
    private TableColumn<Shop, String> shopSellerNameColumn; 
    @FXML
    private TableColumn<Shop, String> shopNameColumn;
    @FXML
    private TableColumn<Shop, Integer> shopStockColumn;
    @FXML
    private TableColumn<Shop, Integer> shopFiscalityColumn;

    private ObservableList<Shop> shopData = FXCollections.observableArrayList();
    private ObservableList<Integer> sellersData = FXCollections.observableArrayList();
    
    @FXML
    private TextField addMagasinNameField;
    @FXML
    private TextField addMagasinFiscalityField;
    @FXML
    private ComboBox<Integer> addMagasinSellerComboBox;
    
    @FXML
    private TextField updateMagasinNameField;
    @FXML
    private TextField updateMagasinFiscalityField;
    @FXML
    private ComboBox<Integer> updateMagasinSellerComboBox;
    
    @FXML
    private TextField searchShopField;
    @FXML
    private Label searchShopLabel;
    
    @FXML
    private ImageView shopHelpImage;
    private Tooltip helpTooltip;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        shopTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        List<Shop> shopList = new ArrayList<>();
        ShopService shopService = ShopService.getInstance();
        shopList = shopService.getAllShops();    
        shopData.clear();
        if (shopList != null) {
            shopData.addAll(shopList);
            shopSellerNameColumn.setCellValueFactory(new PropertyValueFactory<>("sellerName"));
            shopNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            shopStockColumn.setCellValueFactory(new PropertyValueFactory<>("taille_stock"));
            shopFiscalityColumn.setCellValueFactory(new PropertyValueFactory<>("matricule_fiscal"));
            shopTable.setItems(shopData);
            searchShopLabel.setText("Résultat : "+shopList.size()+" ligne(s).");
        } else {
            searchShopLabel.setText("");
            shopTable.setPlaceholder(new Label("Il n'y a aucun magasin dans la base de données. Veuillez en rajouter! "));
        }
        
        List<Integer> availableSellers = new ArrayList<>();
        availableSellers = shopService.getAvailableSellers();
        if (!availableSellers.isEmpty())
        {
            sellersData.addAll(availableSellers);
            addMagasinSellerComboBox.setItems(sellersData);
            updateMagasinSellerComboBox.setItems(sellersData);
        }
        
        helpTooltip = new Tooltip("Vous êtes dans l'onglet de getion de magasin.\n"
                + "Ici, vous pouvez visualiser la liste des magasins partenaires de Shoppy,\n "
                + "en rechercher par identifiant, matricule fiscal et biensûr par nom.\n"
                + "Il est aussi possible d'en ajouter de nouveaux ou de modifier un magasin existant.\n"
                + "Il est aussi posible de supprimer un ou plusieurs magasins.\n"
                + "Pour la sélection multiple, il vous suffit de maintenir la touche Ctrl de votre clavier\n"
                + "enfoncée lors du clic de sélection.");
//        helpToltip.show();
        Tooltip.install(shopHelpImage, helpTooltip);
    }

    //********************* C **************************//
    @FXML
    public void addShopAction() {
        ShopService shopService = ShopService.getInstance();
        InputCheck inputCheck = InputCheck.getInstance();
        String name = addMagasinNameField.getText();
        String taxID = addMagasinFiscalityField.getText();
        boolean result = false;

        if (inputCheck.testTextInput(name) && inputCheck.testNumberInput(taxID)) {
            int intTaxID = Integer.parseInt(taxID);
            if (shopService.uniqueTaxID(intTaxID)) {
                Integer sellerId = addMagasinSellerComboBox.getValue();
                result = shopService.addShop(new Shop(0, sellerId, name, intTaxID));
            } else {
                Alert inputAlert = new Alert(Alert.AlertType.ERROR, "Ce matricule fiscal existe déja dans la base de données !", ButtonType.OK);
                inputAlert.showAndWait();
            }
        } else {
//            System.out.println("WIP : Error dialog => Wrong input format !");
            Alert inputAlert = new Alert(Alert.AlertType.ERROR, "Le format de données saisi est incorrect: \n"
                    + " Le nom du magasin doit contenir au moins une lettre. \n"
                    + " Le matricule fiscal n'est composé que de chiffres.", ButtonType.OK);
            inputAlert.showAndWait();
        }
        if (result) {
            refreshTableData();
            System.out.println("Succès de l'ajout du magasin !");
        } else {
            System.out.println("Echec de l'ajout du magasin !");

        }

    }
    //********************* R **************************//
    public void refreshTableData() {
        refreshSellersList();
        List<Shop> shopList = new ArrayList<>();
        ShopService shopService = ShopService.getInstance();
        shopList = shopService.getAllShops();
        shopData.clear();
        if (shopList != null)
        {           
            shopData.addAll(shopList);
            shopTable.setItems(shopData);
            searchShopLabel.setText("Résultat : "+shopList.size()+" ligne(s)."); 
        }
        else
        {
            searchShopLabel.setText("Aucun résultat."); 
            shopTable.setPlaceholder(new Label("Il n'y a aucun magasin dans la base de données. Veuillez en rajouter! "));
        }
    }
    
    public void refreshSellersList(){
        List<Integer> availableSellers = new ArrayList<>();
        ShopService shopService = ShopService.getInstance();
        availableSellers = shopService.getAvailableSellers();
        if (!availableSellers.isEmpty())
        {
            sellersData.addAll(availableSellers);
            addMagasinSellerComboBox.setItems(sellersData);
            updateMagasinSellerComboBox.setItems(sellersData);
        }        
    }
    
    //********************* U **************************//
    @FXML
    public void selectOneShopAction(KeyEvent keyEvent) {
        Shop shop = (Shop) shopTable.getSelectionModel().getSelectedItem();
        if(shop != null)
        {
            fillUpdateForm(shop);
        }
    }
    @FXML
    public void clickOneShopAction() {
        Shop shop = (Shop) shopTable.getSelectionModel().getSelectedItem();
        if(shop != null)
        {
            fillUpdateForm(shop);
        }
    }
    
    public void fillUpdateForm(Shop shop)
    {
        updateMagasinNameField.setText(shop.getNom());
        updateMagasinFiscalityField.setText(String.valueOf(shop.getMatricule_fiscal()));   
    }
    @FXML
    public void updateShopAction() {
        Shop selection = shopTable.getSelectionModel().getSelectedItem();
        InputCheck inputCheck = InputCheck.getInstance();
        if (selection != null)
        {   
            Shop shop = new Shop();
            shop.setId(selection.getId());
            String newName = updateMagasinNameField.getText();
            String newTaxID = updateMagasinFiscalityField.getText();            
            Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir modifier le magasin: "+selection.getNom()+" de la base de données ?",ButtonType.YES,ButtonType.NO);
            a.showAndWait();
            if(a.getResult()==ButtonType.YES){
                if(inputCheck.testNumberInput(newTaxID) && inputCheck.testTextInput(newName))
                {
                    shop.setNom(newName);
                    if(updateMagasinSellerComboBox.getValue() != null){
                       shop.setId_vendeur(updateMagasinSellerComboBox.getValue()); 
                    }             
                    int intTaxID = Integer.parseInt(newTaxID);
                    if(intTaxID != selection.getMatricule_fiscal() && ShopService.getInstance().uniqueTaxID(intTaxID)){
                       shop.setMatricule_fiscal(Integer.parseInt(newTaxID)); 
                    }
                    else if (intTaxID != selection.getMatricule_fiscal() && !ShopService.getInstance().uniqueTaxID(intTaxID)) {
                        Alert inputAlert = new Alert(Alert.AlertType.ERROR, "Ce matricule fiscal existe déja dans la base de données. \n"
                                + "Le nouveau matricule saisi ne sera pas pris en compte pour cette modification.", ButtonType.OK);
                        inputAlert.showAndWait();
                        shop.setMatricule_fiscal(selection.getMatricule_fiscal());
                    }
                    else if (intTaxID == selection.getMatricule_fiscal()){
                        shop.setMatricule_fiscal(selection.getMatricule_fiscal());
                    }
                    ShopService shopService = ShopService.getInstance();
                    shopService.updateShop(shop);
                    a.close();
                }
                else
                {
                    Alert inputAlert = new Alert(Alert.AlertType.ERROR, "Le format de données saisi est incorrect: \n"
                            + " Le nom du magasin doit contenir au moins une lettre. \n"
                            + " Le matricule fiscal n'est composé que de chiffres.", ButtonType.OK);
                    inputAlert.showAndWait();
                }
            }else{
            a.close();
            }
        }
        else
        {   
            updateMagasinNameField.setText("");
            updateMagasinFiscalityField.setText("");  
            Alert a=new Alert(Alert.AlertType.WARNING,"Aucune séléction !",ButtonType.CLOSE); 
            a.showAndWait();
        }
        refreshTableData();
    }
    
    //********************* D **************************//
    @FXML
    public void deleteShopAction() {
        ObservableList<Shop> selectedItems = shopTable.getSelectionModel().getSelectedItems();
        Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir supprimer ces "+ selectedItems.size() +" éléments de la base de données ?",ButtonType.YES,ButtonType.NO);
        a.showAndWait();
        if(a.getResult()==ButtonType.YES){
            ShopService shopService = ShopService.getInstance();
            for(Shop shop : selectedItems)
            {
                shopService.deleteShop(shop);
            }
            refreshTableData();
            a.close();
        }else{
        a.close();
        }
    }
     
    //************ SEARCH *********************//
        public void searchShopAction() {
        List<Shop> resultList = new ArrayList<>();
        ShopService shopService = ShopService.getInstance();
        String input = searchShopField.getText();
        if(input.length()>0)
        {
            resultList = shopService.findShops(input);
            shopData.clear();
            shopData.addAll(resultList);
            shopTable.setItems(shopData);   
            searchShopLabel.setText("Résultat : "+resultList.size()+" ligne(s).");
        }
        else
        {
            refreshTableData();
        }
    }
        
    public void typingSearchShopAction(KeyEvent event) {
        List<Shop> resultList = new ArrayList<>();
        ShopService shopService = ShopService.getInstance();
        String input = searchShopField.getText();
        if (input.length() > 0) {
            resultList = shopService.findShops(input);
            shopData.clear();
            shopData.addAll(resultList);
            shopTable.setItems(shopData);
            searchShopLabel.setText("Résultat : "+resultList.size()+" ligne(s).");
        } else {
            refreshTableData();
        }
    }
    
    public void exportToExcelAction()
    {
        ExcelExport exporter = new ExcelExport();
        String fileName = "Shop table - "+LocalDate.now().toString();
        exporter.export(shopTable, fileName);
    }
    
    public void switchToShopInterfaceAction()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/shoppy/view/SellerInterface/SellerInterface.fxml"));
        
        sessionShop = shopTable.getSelectionModel().getSelectedItem();        
        if(sessionShop != null)
        {
            Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir passer à la page de "+ sessionShop.getNom()+" ?",ButtonType.YES,ButtonType.NO);
            a.showAndWait();
            if(a.getResult()==ButtonType.YES){
                try {
                    Parent root = loader.load();
                    
                    Scene scene = new Scene(root);
                    Stage sellerInterface = new Stage();
                    sellerInterface.setTitle("Shoppy Desktop");
                    sellerInterface.getIcons().add(new Image(getClass().getResourceAsStream("/tn/shoppy/resources/images/logo.png")));
                    sellerInterface.setScene(scene);
                    sellerInterface.show();
                } catch (IOException ex) {
                    Logger.getLogger(ShopController.class.getName()).log(Level.SEVERE, null, ex);
                }
                a.close();
            }else{
            a.close();
            }            
        }
        else
        {
            Alert a=new Alert(Alert.AlertType.WARNING,"Aucune sélection !", ButtonType.OK);
            a.showAndWait();            
        }

    }    
}
