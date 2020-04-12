/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.view;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.shoppy.model.CartLine;
import tn.shoppy.model.Product;
import tn.shoppy.model.Shop;
import tn.shoppy.services.OrderService;
import tn.shoppy.services.ProductService;
import tn.shoppy.services.ShopService;
import tn.shoppy.utils.InputCheck;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class CartWindowController implements Initializable {

    @FXML
    private AnchorPane parent;
    @FXML
    private VBox productList;
    @FXML
    private Label grossTotal;
    @FXML
    private Label packagingTotal;
    @FXML
    private Label total;
    @FXML
    private Button searchProductButton;
    @FXML
    private Label searchProductLabel;
    @FXML
    private TextField magasin;
    @FXML
    private TextField searchProductField;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productShopColumn;
    @FXML
    private TableColumn<Product, String> productBrandColumn;
    @FXML
    private TableColumn<Product, Integer> productQuantityColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, String> productCategoriesColumn;

    private ObservableList<Product> productData = FXCollections.observableArrayList();
    private ObservableList<Shop> shopData = FXCollections.observableArrayList();
    @FXML
    private TableView<CartLine> CartTable;
    @FXML
    private TableColumn<?, ?> CartQuantityColumn;
    @FXML
    private TableColumn<?, ?> CartTotalProductColumn;
    @FXML
    private TableColumn<?, ?> CartProductColumn;
    @FXML
    private Label test;
    @FXML
    private TextField qte;
    /**
     * Initializes the controller class.
     */
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
          
            productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
            productCategoriesColumn.setCellValueFactory(new PropertyValueFactory<>("categoriesString"));
            
            
           

            productTable.setItems(productData);
            searchProductLabel.setText("Résultat : " + productList.size() + " produits(s).");
        } else {
            searchProductLabel.setText("Aucun résultat.");
            productTable.setPlaceholder(new Label("Il n'y a aucun produit dans la base de données. Veuillez en rajouter! "));
        }
        
        List<Shop> shopList = new ArrayList<>();
        ShopService shopService = ShopService.getInstance();
        shopList = shopService.getAllShops();    
        shopData.clear();
      

    }
  
 
    //********************* R **************************//
    @FXML
    public void refreshTableData() {
        List<Product> productList = new ArrayList<>();
        ProductService productService = ProductService.getInstance();
        productList = productService.getAllProducts();
        productData.clear();
        if (productList != null) {
            productData.addAll(productList);
            productTable.setItems(productData);
            searchProductLabel.setText("Total : " + productList.size() + " produits");
        } else {
            searchProductLabel.setText("Aucun résultat.");
            productTable.setPlaceholder(new Label("Il n'y a aucun produit dans la base de données. Veuillez en rajouter! "));
        }
    }
    
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
            searchProductLabel.setText("Total : "+resultList.size()+" Produits.");
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
            searchProductLabel.setText("Total : "+resultList.size()+" ¨Produits.");
            productTable.setItems(productData);   
        }
        else
        {
            refreshTableData();
        }
    }
  
    @FXML
    private void Rechercher() {

        searchProductField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                OrderService rs = OrderService.getInstance();
                ObservableList obeListe = FXCollections.observableList(rs.rechercherNomEt(newValue));
                productTable.setItems(obeListe);
            } catch (SQLException ex) {
                Logger.getLogger(CartWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
       

        });
    }
   
    @FXML
    private void exitWindow(ActionEvent event) {
    }

    @FXML
    private void checkout(ActionEvent event) {
    }

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
          String c =";";
          int a =product.getId_magasin();   
           try { 
                Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoppy?useUnicode=true" +
        "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&" +
        "serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
         
         
            ResultSet rs = st.executeQuery("SELECT nom,matricule_fiscal FROM `magasin` WHERE  id = " + a);
       
            int b=1; while (rs.next()) {
              //  table.addCell(rs.getString("id"));
             
              c =rs.getString("nom"); 
//              c+=" ";
//       c +=rs.getString("matricule_fiscal");  
       
            } }
            catch (Exception e) {
                    System.out.println("tn.shoppy.controller.OrderController.initialize()");
                    }
            magasin.setText(c);   
        
    }

    @FXML
    private void addAction(MouseEvent event) {
         Product selection = productTable.getSelectionModel().getSelectedItem();
          InputCheck inputCheck = InputCheck.getInstance();
          int i =0;
          double b=0;
         // String a =qte.getText()+"||"+selection.getPrix()+"||"+selection.getNom()+"||"+selection.getPrix()*Integer.valueOf(qte.getText());
          String c=qte.getText()+"unites || prix : "+selection.getPrix()+"  || produit : "+selection.getNom()+" || totalLigne : "+selection.getPrix()*Integer.valueOf(qte.getText());
        List l = new ArrayList();
        if (selection != null)
        { 
           
            b=Double.valueOf(total.getText())+selection.getPrix()*Integer.valueOf(qte.getText());
          
         
           i++;
           String str1 = Integer.toString(i); 
     
       //   l.add(a);
           test.setText(test.getText()+c+"\n");   
         
                 System.out.println("tn.shoppy.view.CartWindowController.addAction()");
                  total.setText(String.valueOf(b));
        }
       
    }
    @FXML
    private void reduceAction(MouseEvent event) {
    }
    
   
    
    
}
