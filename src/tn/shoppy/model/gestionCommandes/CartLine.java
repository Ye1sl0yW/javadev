/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.model.gestionCommandes;
import tn.shoppy.model.Product;
import javafx.beans.property.StringProperty;

/**
 *
 * @author asus
 */
public class CartLine {
     
   private StringProperty ProductName;
    private Product productInfo;
    private int quantity;
  
    public CartLine() {
        this.quantity = 0;
    }
   public StringProperty ProductNameProperty() {
        

  return ProductName;
 }
    public Product getProduct() {
        return productInfo;
    }
  
    public void setProduct(Product productInfo) {
        this.productInfo = productInfo;
    }
  
    public int getQuantity() {
        return quantity;
    }
  
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
  
    public double getAmount() {
        return this.productInfo.getPrix()* this.quantity;
    }
     

}
