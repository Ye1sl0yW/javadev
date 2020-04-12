package tn.shoppy.controller.gestionCommandes;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import tn.shoppy.model.omar.Shop;
import tn.shoppy.services.GestionCommandes.ShopService;
import tn.shoppy.utils.ConnectionDB;
import tn.shoppy.utils.InputCheck;

/**
 *
 * @author Haroun
 */
public class ShopController implements Initializable {
    Connection cnx = ConnectionDB.getCnx();

    /**
     * UI attributes
     */
    @FXML
    private TableView<Shop> shopTable;    
    @FXML
    private TableColumn<Shop, Integer> shopIdColumn;
    @FXML
    private TableColumn<Shop, String> shopNameColumn;
    @FXML
    private TableColumn<Shop, Integer> shopStockColumn;
    @FXML
    private TableColumn<Shop, Integer> shopFiscalityColumn;

    private ObservableList<Shop> shopData = FXCollections.observableArrayList();
 
    @FXML
    private TextField addMagasinNameField;
    @FXML
    private TextField addMagasinFiscalityField;
    @FXML
    private TextField updateMagasinNameField;
    @FXML
    private TextField updateMagasinFiscalityField;
    
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
        shopData.addAll(shopList);
        if (!shopList.isEmpty()) {
            shopIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            shopNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            shopStockColumn.setCellValueFactory(new PropertyValueFactory<>("taille_stock"));
            shopFiscalityColumn.setCellValueFactory(new PropertyValueFactory<>("matricule_fiscal"));
            shopTable.setItems(shopData);
            searchShopLabel.setText("Résultat : "+shopList.size()+" ligne(s).");
        } else {
            searchShopLabel.setText("");
            shopTable.setPlaceholder(new Label("Il n'y a aucun magasin dans la base de données. Veuillez en rajouter! "));
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
        
        if (inputCheck.testTextInput(name) && inputCheck.testNumberInput(taxID))
        {
            int intTaxID = Integer.parseInt(taxID);
        /* use  add method */    result = shopService.addShop(new Shop(name,intTaxID));
        }
        else
        {
            System.out.println("WIP : Error dialog => Wrong input format !");
        }
        if (result)
        {
            refreshTableData();
            System.out.println("Succès de l'ajout du magasin !");
        }
        else
        {
            System.out.println("Echec de l'ajout du magasin !");
            
        }

    }
    //********************* R **************************//
    public void refreshTableData() {
        List<Shop> shopList = new ArrayList<>();
        ShopService shopService = ShopService.getInstance();
        shopList = shopService.getAllShops();
        shopData.clear();
        shopData.addAll(shopList);
        shopTable.setItems(shopData);
        searchShopLabel.setText("Résultat : "+shopList.size()+" ligne(s).");
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
            Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir modifier le magasin: "+shop.getNom()+" de la base de données ?",ButtonType.YES,ButtonType.NO);
            a.showAndWait();
            if(a.getResult()==ButtonType.YES){
                if(inputCheck.testNumberInput(newTaxID) && inputCheck.testTextInput(newName))
                {
                    shop.setNom(newName);
                    shop.setMatricule_fiscal(Integer.parseInt(newTaxID));
                    ShopService shopService = ShopService.getInstance();
                    shopService.updateShop(shop);
                    a.close();
                }
                else
                {
                    Alert inputAlert = new Alert(Alert.AlertType.ERROR,"Le format de données saisi est incorrect.",ButtonType.OK);
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
        System.out.println(selectedItems);
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
    
}
