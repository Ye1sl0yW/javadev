/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asus
 */
public class Cart {
       private int orderNum;
 
    private int  customerInfo=1;
 
    private final List<CartLine> cartLines = new ArrayList<CartLine>();
 
    public Cart() {
 
    }
 
    public int getOrderNum() {
        return orderNum;
    }
 
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
 
    public int getCustomerInfo() {
        return customerInfo;
    }
 
    public void setCustomerInfo() {
       
    }
 
    public List<CartLine> getCartLines() {
        return this.cartLines;
    }
 
    private CartLine findLineByCode(String code) {
        for (CartLine line : this.cartLines) {
            if (line.getProduct().getNom().equals(code)) {
                return line;
            }
        }
        return null;
    }
 
    public void addProduct(Product productInfo, int quantity) {
        CartLine line = this.findLineByCode(productInfo.getNom());
 
        if (line == null) {
            line = new CartLine();
            line.setQuantity(0);
            line.setProduct(productInfo);
            this.cartLines.add(line);
        }
        int newQuantity = line.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.cartLines.remove(line);
        } else {
            line.setQuantity(newQuantity);
        }
    }
 
    public void validate() {
 
    }
 
    public void updateProduct(String code, int quantity) {
        CartLine line = this.findLineByCode(code);
 
        if (line != null) {
            if (quantity <= 0) {
                this.cartLines.remove(line);
            } else {
                line.setQuantity(quantity);
            }
        }
    }
 
    public void removeProduct(Product productInfo) {
        CartLine line = this.findLineByCode(productInfo.getNom());
        if (line != null) {
            this.cartLines.remove(line);
        }
    }
 
    public boolean isEmpty() {
        return this.cartLines.isEmpty();
    }
 
    public boolean isValidCustomer() {
       return true;
    }
 
    public int getQuantityTotal() {
        int quantity = 0;
        for (CartLine line : this.cartLines) {
            quantity += line.getQuantity();
        }
        return quantity;
    }
 
    public double getAmountTotal() {
        double total = 0;
        for (CartLine line : this.cartLines) {
            total += line.getAmount();
        }
        return total;
    }
 
    public void updateQuantity(Cart cartForm) {
        if (cartForm != null) {
            List<CartLine> lines = cartForm.getCartLines();
            for (CartLine line : lines) {
                this.updateProduct(line.getProduct().getNom(), line.getQuantity());
            }
        }
 
    }
  
}
