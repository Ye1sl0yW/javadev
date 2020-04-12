package tn.shoppy.controller.gestionCommandes;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package tn.shoppy.controller;
//
//import java.awt.Panel;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableView;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.VBox;
//import javax.swing.JOptionPane;
//import javax.swing.table.DefaultTableModel;
//import tn.shoppy.model.Cart;
//import tn.shoppy.model.Order;
//import tn.shoppy.model.Product;
//import tn.shoppy.services.CartService;
//import tn.shoppy.services.OrderService;
//
///**
// * FXML Controller class
// *
// * @author asus
// */
//public class CartController implements Initializable {
//
//    @FXML
//    private Label productStock;
//    
//    @FXML
//    private Label productQty;
//    @FXML
//    private AnchorPane parent;
//    @FXML
//    private VBox productList;
//    @FXML
//    private Label grossTotal;
//    @FXML
//    private Label packagingTotal;
//    @FXML
//    private Label total;
//    @FXML
//    private Label productName ;
//     @FXML
//    private Label productModel ;
//      @FXML
//    private Label productPrice;
//     
//    private CartService cartService;
//    /**
//     * Initializes the controller class.
//     */
//  @FXML
//    private void exitWindow(ActionEvent event) {
//        
//        
//    }
//
//    @FXML
//    private void checkout(ActionEvent event) {
//
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//    //    this.cartService= CartService.getInstance();
//
//      //  for (CartService item : this.cartService.getItems()) {
//       //     this.productList.getChildren().add(new CartItemComponent(item));
//        //}
//    }    
//
//     public static ArrayList<Product> cartItem = new ArrayList();
//    
//    private void addToCartActionPerformed(ActionEvent  evt) {                                          
//        
//        if(Integer.parseInt(productStock.getText())<Integer.parseInt(this.productQty.getText())){
//            JOptionPane.showMessageDialog(null, "Not enough product in Stock!");
//        }
//        else{
//            int qty = Integer.parseInt(this.productQty.getText());
//            Product p = new Product(this.productName.getText(), this.productModel.getText(), qty*Integer.parseInt(this.productPrice.getText()), 
//                                                Integer.parseInt(this.productQty.getText()), null, null);
//            cartItem.add(p);
//            JOptionPane.showMessageDialog(null, "Product added to cart!");
//   
//            int qtyTester = Integer.parseInt(this.productStock.getText())-Integer.parseInt(this.productQty.getText());
//            
//            
//            
//            
//            
//        }
//        
//    }  
//    @FXML
//    private Panel cardParentPanel;
//    @FXML
//    private Panel cartPanel;
//    @FXML 
//    private TableView cartItemTable ;
//    static Object[] data = new Object[4];   
//
//      private void cartButtonActionPerformed(ActionEvent evt) {                                           
//        cardParentPanel.removeAll();
//        cardParentPanel.add(cartPanel);
//        cardParentPanel.repaint();
//        cardParentPanel.revalidate();
//        
//         DefaultTableModel model =  (DefaultTableModel) cartItemTable.getModel();
//         model.setRowCount(0);
//        int total = 0;
//        String tot = "";
//        
//        for(int i=0; i<ProductInformation.cartItem.size(); i++){
//            data[0] = ProductInformation.cartItem.get(i).getBrand();
//            data[1] = ProductInformation.cartItem.get(i).getModel();
//            data[2] = ProductInformation.cartItem.get(i).getPrice();
//            data[3] = ProductInformation.cartItem.get(i).getQty();
//            
//            model.addRow(data);
//            cartItemTable.setRowHeight(20);
//            total+=Integer.parseInt(cartItemTable.getValueAt(i, 2)+"");
//            
//        }
//        
//        billLabel.setText(String.valueOf(total));
//    }                                          
//}
