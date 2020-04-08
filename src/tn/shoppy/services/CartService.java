/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.services;

import java.sql.Connection;
import tn.shoppy.utils.ConnectionDB;

/**
 *
 * @author asus
 */
public class CartService {
     private static CartService CartServiceInstance;
    private final Connection cn = ConnectionDB.getCnx();
  
    public boolean DeleteOrder()
    {
        System.out.println("tn.shoppy.services.OrderService.DeleteOrder()");
        return true;
    }
     public static CartService getInstance() {   //Singleton Design Pattern
        if (CartServiceInstance==null)
        {
            CartServiceInstance = new CartService();
        }
        return CartServiceInstance ;  
//        return new ShopService();
    }
        
}
