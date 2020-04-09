package tn.shoppy.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tn.shoppy.model.Product;
import tn.shoppy.model.Shop;
import tn.shoppy.services.Pdf;
import tn.shoppy.services.ProductService;
import tn.shoppy.services.ShopService;
import tn.shoppy.utils.ConnectionDB;
import tn.shoppy.utils.InputCheck;

/**
 *
 * @author anas fattoum
 */
public class ProductController implements Initializable{
    
    Connection cnx = ConnectionDB.getCnx();
    public static Product prod;
    
    /**
     * UI elements
     */
    @FXML
    private TableView<Product> productTable;
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
    private ComboBox<Shop> addProductShopComboBox;
    
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
    private ComboBox<Shop> updateProductShopComboBox;
    
    
    @FXML
    private Label searchProductLabel;
    private ObservableList<Product> productData = FXCollections.observableArrayList();
    private ObservableList<Shop> shopData = FXCollections.observableArrayList();

    @FXML
    private TextField searchProductField;

    @FXML
    private ImageView productHelpImage;
    private Tooltip helpTooltip;
    @FXML
    private Button searchProductButton;
    @FXML
    private Button updateProductButton;
    @FXML
    private Button addProductButton;
    
    @FXML
    private Button deleteProductAction;
    @FXML
    private Button pdf_btn;
    @FXML
    private Button btstat;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        List<Product> productList = new ArrayList<>();
        ProductService productService = ProductService.getInstance();
        productList = productService.getAllProducts();
        productData.clear();
        if (productList != null) {
            productData.addAll(productList);
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
        
        List<Shop> shopList = new ArrayList<>();
        ShopService shopService = ShopService.getInstance();
        shopList = shopService.getAllShops();    
        shopData.clear();
        if (shopList != null) {
            shopData.addAll(shopList);
            addProductShopComboBox.getItems().addAll(shopData);
            updateProductShopComboBox.getItems().addAll(shopData);
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
        int shopID = 2;
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
            if(addProductShopComboBox.getValue()!= null)
            {
                shopID = addProductShopComboBox.getValue().getId();
            }
            Product p = new Product(0,shopID,name,quantityInt,description,priceDouble,brand);
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
        productList = productService.getAllProducts();
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
        updateProductShopComboBox.setValue(shopService.findOneShopByID(product.getId_magasin()));    
        
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
            int newShopID = updateProductShopComboBox.getValue().getId();
            
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
                    product.setId_magasin(newShopID);
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
    
    @FXML
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
     
    @FXML
     public void typingSearchProductAction(KeyEvent event) {
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

    @FXML
    public void detailPAction() {
        System.out.println("tn.shoppy.controller.ProductController.DetailProductAction()");
        prod = productTable.getSelectionModel().getSelectedItem();

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/Detail.fxml"));
            Stage myWindow = (Stage) productTable.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("Détail produit");
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }


        
    }
        @FXML
    private void pdf_btn(ActionEvent event) {
                if (!(productTable.getItems().isEmpty())) {
            Pdf.PdfListeProduits();
                    System.out.println("Print to pdf done");

    
    }

}

    @FXML
    public void btstat(ActionEvent event) {
           try {
            Parent root = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/statE.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } catch (IOException ex) {
        }
    }

}