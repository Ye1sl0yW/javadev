package tn.shoppy.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import tn.shoppy.model.Order;


import tn.shoppy.services.CartService;
/**
 *
 * @author asus
 */
public class CartItemComponent  extends AnchorPane{
    
    @FXML
    private AnchorPane frame;

    @FXML
    private TextField quantity;

    @FXML
    private Label total;

    @FXML
    private Label productName;

    private Order item;

    private CartService cartState;

    public CartItemComponent(Order item) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ordermanagementsystem/views/components/CartItemComponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            exception.getStackTrace();
        }

        this.cartState = CartService.getInstance();
        this.item = item;

        this.setQuantity(1);
        this.quantity.setEditable(false);

      //  this.productName.setText(this.item.getProduct().getName());
    }

    private void setQuantity(int quantity) {
        //this.quantity.setText(String.valueOf(quantity));
       // this.item.setQuantity(quantity);
      //  this.cartState.updateItem(this.item);
      //  this.total.setText("RM" + String.format("%.2f", item.getProduct().getPrice() * quantity));
    }

    private int getQuantity() {
        return Integer.parseInt(this.quantity.getText());
    }

    private void updateSummary() {

    }

    @FXML
    private void add(ActionEvent event) {
        int quantity = this.getQuantity();

      //  if (quantity == 99) {
        //    DialogBox.showValidationDialog("The quantity cannot be over 99.");
       // } else {
        //    this.setQuantity(quantity + 1);
        //}
    }

    @FXML
    private void substract(ActionEvent event) {
       // int quantity = this.getQuantity();

       // if (quantity == 1) {
       //     DialogBox.showValidationDialog("The quantity cannot be below 1.");
       // } else {
        //    this.setQuantity(quantity - 1);
       // }
    }
}
