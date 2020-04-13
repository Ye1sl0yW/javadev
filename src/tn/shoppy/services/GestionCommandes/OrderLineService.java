/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.services.GestionCommandes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import tn.shoppy.model.gestionCommandes.OrderLine;
import tn.shoppy.utils.HA.ConnectionDB;

/**
 *
 * @author asus
 */
public class OrderLineService {
      
    private static OrderLineService orderLineServiceInstance;
    private final Connection cn = ConnectionDB.getCnx();
    
    
    public static OrderLineService getInstance() {   //Singleton Design Pattern
        if (orderLineServiceInstance==null)
        {
            orderLineServiceInstance = new OrderLineService();
        }
        return orderLineServiceInstance;  
//        return new OrderLineService();
    }
        
        
    public List<OrderLine> getAllOrderLines() 
    {
        List<OrderLine> list = new ArrayList<>();
        int count = 0;
        
        String query="select * from ligne_cmd ";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                
                OrderLine r = new OrderLine();
                r.setIdLC(rs.getInt(1));
                r.setQte(rs.getInt(4));
                r.setTotalLc(rs.getFloat(5));
               
                
                list.add(r);
                count++;
            }
            if(count == 0)
            {
                return null;
            }
            else
            {
               return list;
            }
        }
        catch (SQLException ex) {
//            Logger.getLogger(AvisService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
        
   
    public boolean addOrderLine(OrderLine orderLine)
    {
        System.out.println(orderLine.getIdLC() + " " + orderLine.getQte());

        String query = "INSERT INTO ligne_cmd (qte,totalLigne,id_product,id_cmd) VALUES (?,?,1,1)";      
        try
        {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(1, orderLine.getIdLC());
            pst.setInt(2, orderLine.getQte()); 
            pst.executeUpdate();
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
        //TODO ajouter le false si jamais on veut ajouter une condition d'unicité sur le nom du magasin.
        
    }
    
    public boolean deleteOrderLine(OrderLine orderLine)
    {
        String query = "DELETE FROM ligne_cmd WHERE id=?";
        try 
        {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(1, orderLine.getIdLC());
            pst.executeUpdate();
            System.out.println("Deletion successful !");
        } 
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
    
    
    public boolean updateOrderLine(OrderLine orderLine)
    {
        System.out.println(orderLine);
        String query = "UPDATE ligne_cmd SET totalLigne=?,qte=? WHERE id=?";
        try {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(3, orderLine.getIdLC());
            pst.setFloat(1, orderLine.getTotalLc());
            pst.setInt(2, orderLine.getQte());
            pst.executeUpdate();
            System.out.println("Update successful !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return true;
        }       
        return false;
    }


   
}
