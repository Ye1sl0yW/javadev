package tn.shoppy.controller.SellerInterface;

import tn.shoppy.controller.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import static tn.shoppy.controller.ShopController.sessionShop;
import tn.shoppy.model.Product;
import tn.shoppy.model.Shop;
import tn.shoppy.services.ProductService;
import tn.shoppy.services.ShopService;
import tn.shoppy.utils.ConnectionDB;
import tn.shoppy.utils.InputCheck;

/**
 *
 * @author anas fattoum
 */
public class SellerProductController implements Initializable{
    
    Connection cnx = ConnectionDB.getCnx();
    
    /**
     * UI elements
     */
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productShopColumn;
    @FXML
    private TableColumn<Product, String> productBrandColumn;
    @FXML
    private TableColumn<Product, String> productDescriptionColumn;
    @FXML
    private TableColumn<Product, Integer> productQuantityColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, String> productCategoriesColumn;
    @FXML
    private TableColumn<Product, Date> productUpdatedAtColumn;

    
    @FXML
    private TextField addProductNameField;
    @FXML
    private TextField addProductQuantityField;
    @FXML
    private TextField addProductPriceField;
    @FXML
    private TextField addProductBrandField;
    @FXML
    private TextArea addProductDescriptionArea;
    
    @FXML
    private TextField updateProductNameField;
    @FXML
    private TextField updateProductQuantityField;
    @FXML
    private TextField updateProductPriceField;
    @FXML
    private TextField updateProductBrandField;
    @FXML
    private TextArea updateProductDescriptionArea;
    
    @FXML
    private Label searchProductLabel;
    private ObservableList<Product> productData = FXCollections.observableArrayList();

    @FXML
    private TextField searchProductField;

    @FXML
    private ImageView productHelpImage;
    private Tooltip helpTooltip;
    
    private Shop sessionShop;
    
    public SellerProductController(Shop shop){
        this.sessionShop = shop;
    }
    
    public SellerProductController()
    {
        this.sessionShop = ShopController.sessionShop;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        List<Product> productList = new ArrayList<>();
        ProductService productService = ProductService.getInstance();
        productList = productService.getAllProductsForOneShop(sessionShop);
        productData.clear();
        if (productList != null) {
            productData.addAll(productList);
            productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            productShopColumn.setCellValueFactory(new PropertyValueFactory<>("id_magasin"));
            productBrandColumn.setCellValueFactory(new PropertyValueFactory<>("marque"));
            productDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
            productCategoriesColumn.setCellValueFactory(new PropertyValueFactory<>("categoriesString"));
            productUpdatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("updated_at"));
            
           

            productTable.setItems(productData);
            searchProductLabel.setText("Résultat : " + productList.size() + " ligne(s).");
        } else {
            searchProductLabel.setText("Aucun résultat.");
            productTable.setPlaceholder(new Label("Il n'y a aucun produit dans la base de données. Veuillez en rajouter! "));
        }
        
        helpTooltip = new Tooltip("Vous êtes dans l'onglet de getion des produits.\n"
                + "Ici, vous pouvez visualiser la liste des produits des partenaires de Shoppy,\n "
                + "en rechercher par identifiant, nom et biensûr par identifiant de magasin.\n"
                + "Il est aussi possible d'en ajouter de nouveaux ou de modifier ceux existants.\n"
                + "Il est aussi posible d'en supprimer un ou plusieures.\n"
                + "Pour la sélection multiple, il vous suffit de maintenir la touche Ctrl de votre clavier\n"
                + "enfoncée lors du clic de sélection.");
        Tooltip.install(productHelpImage, helpTooltip);
    }

    
     //********************* C **************************//
    @FXML
    public void addProductAction() throws SQLException {
        ProductService productService = ProductService.getInstance();
        InputCheck inputCheck = InputCheck.getInstance();
        String name = addProductNameField.getText();
        String quantity = addProductQuantityField.getText();
        String description = addProductDescriptionArea.getText();
        String price = addProductPriceField.getText();
        String brand = addProductBrandField.getText();

        boolean result = false;

        if (inputCheck.testTextInput(name) && inputCheck.testDoubleInput(price)
                && (inputCheck.testNumberInput(quantity)) && inputCheck.testTextInput(brand) ) {
            Double priceDouble = Double.parseDouble(price);
            Integer quantityInt = Integer.parseInt(quantity);
            Product p = new Product(0,sessionShop.getId(),name,quantityInt,description,priceDouble,brand);
            System.out.println(p);
            result = productService.addProduct(p) ;
        } 
        else 
        {
            System.out.println("WIP : Error dialog => Wrong input format !");
        }
        if (result) {
            productData.clear();
            refreshTableData();
            System.out.println("Succès de l'ajout du produit !");
        } else {
            System.out.println("Echec de l'ajout du produit !");

        }

    }
    
    
    //********************* R **************************//
    public void refreshTableData() {
        List<Product> productList = new ArrayList<>();
        ProductService productService = ProductService.getInstance();
        productList = productService.getAllProductsForOneShop(sessionShop);
        productData.clear();
        if (productList != null) {
            productData.addAll(productList);
            productTable.setItems(productData);
            searchProductLabel.setText("Résultat : " + productList.size() + " ligne(s).");
        } else {
            searchProductLabel.setText("Aucun résultat.");
            productTable.setPlaceholder(new Label("Il n'y a aucun produit dans la base de données. Veuillez en rajouter! "));
        }
    }
    
     //********************* U **************************//
    @FXML
    public void selectOneProductAction(KeyEvent keyEvent) {
        Product product = (Product) productTable.getSelectionModel().getSelectedItem();
        if(product != null)
        {
            fillUpdateForm(product);
        }
    }
    @FXML
    public void clickOneProductAction() {
        Product product = (Product) productTable.getSelectionModel().getSelectedItem();
        if(product != null)
        {
            fillUpdateForm(product);
        }
    }
    
    public void fillUpdateForm(Product product)
    {
        ShopService shopService= ShopService.getInstance();
        
        updateProductNameField.setText(product.getNom());
        updateProductQuantityField.setText(String.valueOf(product.getQuantite()));
        updateProductDescriptionArea.setText(product.getDescription());
        updateProductPriceField.setText(String.valueOf(product.getPrix()));
        updateProductBrandField.setText(product.getMarque());    
        
    }
    
    @FXML
    public void updateProductAction() {
        Product selection = productTable.getSelectionModel().getSelectedItem();
        InputCheck inputCheck = InputCheck.getInstance();
        if (selection != null)
        {   
            Product product = new Product();
            product.setId(selection.getId());
            String newName = updateProductNameField.getText();
            String newQuantity = updateProductQuantityField.getText();  
            String newDescription = updateProductDescriptionArea.getText();
            String newPrice = updateProductPriceField.getText();  
            String newBrand = updateProductBrandField.getText();  
            
            Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir modifier le produit: "+selection.getNom()+" de la base de données ?",ButtonType.YES,ButtonType.NO);
            a.showAndWait();
            
            if(a.getResult()==ButtonType.YES){
                if (inputCheck.testTextInput(newName) && inputCheck.testDoubleInput(newPrice)
                && (inputCheck.testNumberInput(newQuantity)) && inputCheck.testTextInput(newBrand) ) 
       
                {
                    product.setNom(newName);
                    product.setQuantite(Integer.parseInt(newQuantity));
                    product.setDescription(newDescription);
                    product.setPrix(Double.parseDouble(newPrice));
                    product.setId_magasin(sessionShop.getId());
                    product.setUpdated_at(new java.sql.Date(System.currentTimeMillis()));

                    
                    ProductService productService = ProductService.getInstance();
                    productService.updateProduct(product);
                    refreshTableData();
                    a.close();
                }
                else
                {
                    Alert inputAlert = new Alert(Alert.AlertType.ERROR,"Le format de données saisi est incorrect.",ButtonType.OK);
                    inputAlert.showAndWait();
                }
            }else{
            a.close();
            }
        }
        else
        {   
            Alert a=new Alert(Alert.AlertType.WARNING,"Aucune séléction !",ButtonType.CLOSE); 
            a.showAndWait();
        }
        refreshTableData();
    }    
    
    
    //********************* D **************************//
    @FXML
    public void deleteProductAction() {
        ObservableList<Product> selectedItems = productTable.getSelectionModel().getSelectedItems();
        System.out.println(selectedItems);
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr(e) de vouloir supprimer ces " + selectedItems.size() + " éléments de la base de données ?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            ProductService productService = ProductService.getInstance();
            for (Product p : selectedItems) {
                productService.deleteProduct(p);
            }
            refreshTableData();
            a.close();
        } else {
            a.close();
        }
    }
    
        //************ SEARCH *********************//
    
     public void searchProductAction() {
        List<Product> resultList = new ArrayList<>();
        ProductService productService = ProductService.getInstance();
        String input = searchProductField.getText();
        if(input.length()>0)
        {
            resultList = productService.findProductByNameOrDescription(input);
            productData.clear();
            productData.addAll(resultList);
            productTable.setItems(productData);   
            searchProductLabel.setText("Résultat : "+resultList.size()+" ligne(s).");
        }
        else
        {
            refreshTableData();
        }
    }
     
     public void typingSearchProductAction() {
        List<Product> resultList = new ArrayList<>();
        ProductService productService = ProductService.getInstance();
        String input = searchProductField.getText();
        if(input.length()>0)
        {
            resultList = productService.findProductByNameOrDescription(input);
            productData.clear();
            productData.addAll(resultList);
            searchProductLabel.setText("Résultat : "+resultList.size()+" ligne(s).");
            productTable.setItems(productData);   
        }
        else
        {
            refreshTableData();
        }
    }
    
}
