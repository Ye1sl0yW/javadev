/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import static java.time.LocalDateTime.now;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import tn.shoppy.model.Order;
import tn.shoppy.model.Product;
import tn.shoppy.model.Shop;
import tn.shoppy.utils.ConnectionDB;

/**
 *
 * @author asus
 */
public class OrderService {
      private static OrderService orderServiceInstance;
    private final Connection cn = ConnectionDB.getCnx();
  
    public boolean DeleteOrder()
    {
        System.out.println("tn.shoppy.services.OrderService.DeleteOrder()");
        return true;
    }
     public static OrderService getInstance() {   //Singleton Design Pattern
        if (orderServiceInstance==null)
        {
            orderServiceInstance = new OrderService();
        }
        return orderServiceInstance ;  
//        return new ShopService();
    }
        
        
  public List<Order> getAllOrders() 
    {
        List<Order> list = new ArrayList<>();
        int count = 0;
           String s = "Archivage Active "; 
        String query="select * from commande where adresse_liv not like '"+s+"%'";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                
                Order r = new Order();
                //Build formatter

                r.setIdCmd(rs.getInt(1));
                r.setTotal(rs.getFloat(2));
                r.setQteTot(rs.getInt(3));
                r.setDateCreation(rs.getTimestamp(4).toLocalDateTime());
                r.setAdresseLiv(rs.getString(5));
                r.setId_Acheteur(rs.getInt(6));
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
        
    /**
     *  This method is used to add a shop to the dataBase. Call it in the
     * ShopController class. 
     * DISCLAIMER: the input checking must be done BEFORE using this methods.
     * @param 
     * @param 
     * @return true if the operation is successful and false otherwise.
     */
    public boolean addOrder(Order order)
    {
        System.out.println(order.getIdCmd() + " " + order.getTotal());

        String query = "INSERT INTO Commande (QteTot,total,adresse_liv,DateCreation,id_acheteur) VALUES (?,?,?,?,1)";      
          DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
 ZoneId zoneId = ZoneId.systemDefault();
//Format LocalDateTime
        try
            
        {
            PreparedStatement pst = cn.prepareStatement(query);
                  pst.setTimestamp(4,Timestamp.from(order.getDateCreation().atZone(zoneId).toInstant()));

            pst.setString(3, order.getAdresseLiv());
            pst.setInt(1, order.getQteTot()); 
            pst.setFloat(2, order.getTotal()); 
            
      //  r.setDateCreation(rs.getTimestamp(4).toLocalDateTime());
     
    
 

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
      public boolean deleteOrder(Order order)
    {


        String query = "DELETE FROM Commande WHERE id=? ";
        try 
        {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(1, order.getIdCmd());
            pst.executeUpdate();
            System.out.println("suppression ,succes !");
        } 
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
    
      /* find method */
        public List<Order> findOrders(String input) {  
        List<Order> cleanResult = new ArrayList<Order>();
        List<Order> r1 = findOrdersByQte(input);
        List<Order> r2 = findOrdersByTotal(input);
        List<Order> r3 = findOrdersByID(input);
         List<Order> r4= findOrdersByAdr(input);

        if(r1 != null){
            cleanResult.addAll(r1);
        }
        if(r2 != null){
            cleanResult.addAll(r2);
        }
        if(r3 != null){
            cleanResult.addAll(r3);
        }
        if(r4 != null){
            cleanResult.addAll(r4);
        }
        List<Order> result = cleanResult.stream().distinct().collect(Collectors.toList());
        return result;
    }
     public boolean updateOrder(Order order)
    {
        System.out.println(order);
        String query = "UPDATE Commande SET adresse_liv=?,total=?,QteTot=? WHERE id=?";
        try {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(4, order.getIdCmd());
            pst.setString(1, order.getAdresseLiv());
            pst.setFloat(2, order.getTotal());
            pst.setInt(3, order.getQteTot());
            pst.executeUpdate();
            System.out.println("Update successful !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return true;
        }       
        return false;
    }
public List<Order> findOrdersByQte(String qt) {  
     List<Order> list = new ArrayList<>();
        int count = 0;
        String query="select * from Commande where  convert(QteTot,CHARACTER)  like '%"+qt+"%'";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                
                 Order r = new Order();
                r.setIdCmd(rs.getInt(1));
                r.setTotal(rs.getFloat(2));
                r.setQteTot(rs.getInt(3));
                r.setAdresseLiv(rs.getString(5)); 
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
            System.err.println(ex.getMessage());
            return null;
        }
    }
    
    public List<Order> findOrdersByTotal(String total) {
        List<Order> list = new ArrayList<>();
        int count = 0;
        String query = "SELECT * FROM commande WHERE convert(total,CHARACTER) like '%" + total + "%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                
                 Order r = new Order();
                r.setIdCmd(rs.getInt(1));
                r.setTotal(rs.getFloat(2));
                r.setQteTot(rs.getInt(3));
                r.setAdresseLiv(rs.getString(5)); 
                list.add(r);
                count++;
            }
            if (count == 0) {
                return null;
            } else {
                return list;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
     public List<Order> findOrdersByID(String ID) {
        List<Order> list = new ArrayList<>();
        int count = 0;
        String s = "Archivage Active"; 
        String query = "SELECT * FROM commande WHERE convert(id,CHARACTER) like '%" + ID + "%' and adresse_liv not like '"+s+"%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                Order r = new Order();
                r.setIdCmd(rs.getInt(1));
                r.setTotal(rs.getFloat(2));
                r.setQteTot(rs.getInt(3));
                r.setAdresseLiv(rs.getString(5)); 
                list.add(r);
                count++;
            }
            if (count == 0) {
                return null;
            } else {
                return list;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
        public List<Order> findOrdersByAdr(String ID) {
        List<Order> list = new ArrayList<>();
        int count = 0;
        String s = "Archivage Active"; 
        String query = "SELECT * FROM commande WHERE adresse_liv like '%" + ID + "%' and adresse_liv not like '"+s+"%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                Order r = new Order();
                r.setIdCmd(rs.getInt(1));
                r.setTotal(rs.getFloat(2));
                r.setQteTot(rs.getInt(3));
                r.setAdresseLiv(rs.getString(5)); 
                list.add(r);
                count++;
            }
            if (count == 0) {
                return null;
            } else {
                return list;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
        

 public boolean archiveOrder(Order order) {
       System.out.println(order);
     
        
        String query = "UPDATE commande SET adresse_liv=? WHERE id=?";
        try 
        {    
            String s = "Archivage Active "; 
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(2, order.getIdCmd());
            System.out.println(order.getAdresseLiv());
            pst.setString(1,s+(order.getAdresseLiv()));
          
            pst.executeUpdate();
            System.out.println("archivage successful !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return true;
        }       
        return false;
    }
public ArrayList<Product> rechercherNomEt(String rech) throws SQLException {

        ArrayList<Product> off = new ArrayList<>();
           Product e = null;
        String req = "SELECT `id`,`nom`,`quantite`,`prix`,`marque` FROM produit where nom Like '%"+rech+"%' ";  
         
        Statement stm = cn.createStatement();
        ResultSet rst = stm.executeQuery(req);
        

        while (rst.next()) {
                              e = new Product();
                
              
                e.setId(rst.getInt("id"));
               
                e.setMarque(rst.getString("marque"));
                e.setNom(rst.getString("nom"));
                e.setQuantite(rst.getInt("quantite"));
                e.setPrix(rst.getDouble("prix"));
                
                   if (findAllCategoriesByProductID(rst.getInt(1)) != null)
                {
                    e.getCategoriesID().addAll(findAllCategoriesByProductID(rst.getInt(1)));
                    e.setCategoriesString(getAllCategoriesAsString(rst.getInt(1)));
                }
                
                
                      
   Product per = new Product();
   //public Product(int id,int id_magasin, String nom, int quantite, String description, double prix, String marque) {
   //
   per.setId(rst.getInt(1));
   per.setNom(rst.getString(2));
   per.setQuantite(rst.getInt(3));
   per.setPrix(rst.getFloat(4));
   per.setMarque(rst.getString(5));

  

            off.add(e);
        }
        return off;
    } 
 public String getAllCategoriesAsString(int id)
    {
        List<Integer> list = new ArrayList<>();
        list = findAllCategoriesByProductID(id);
        String result= "";
        for (int elem : list){
            CategoryService cs = CategoryService.getInstance();
            result += cs.findCategoryNameByID(elem) +"  ";  
        }
        return result;
    }
       
 public List<Integer> findAllCategoriesByProductID(int id)
    {
        List<Integer> result = new ArrayList<>();
        int count = 0;
        String query = "SELECT * FROM produit_categorie WHERE produit_id=" + id + " ;";
         try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                
                Integer r = null;
                r=rs.getInt(2);
                
               
                result.add(r);
                count++;
            }
            if(count == 0)
            {
                return null;
            }
            else
            {
               return result;
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
  public List<Product> getAllProducts() 
    {
        List<Product> list = new ArrayList<>();
        int count = 0;
        
        String query="select * from produit ";
     
        
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                
                Product r = new Product();
                r.setId(rs.getInt(1));
                r.setId_magasin(rs.getInt(2));
                r.setNom(rs.getString(4));
                r.setQuantite(rs.getInt(3));
              r.setDescription(rs.getString(5));
                r.setPrix(rs.getDouble(6));
                r.setMarque(rs.getString(7));
                if (findAllCategoriesByProductID(rs.getInt(1)) != null){
                    r.getCategoriesID().addAll(findAllCategoriesByProductID(rs.getInt(1)));
                    r.setCategoriesString(getAllCategoriesAsString(rs.getInt(1)));
//                        String query1="select nom from magasin where id= "+r.getId_magasin()+" ;" ;
//                       Statement st1 = cn.createStatement();
//                       ResultSet rs1 = st1.executeQuery(query1);
//             while (rs1.next()){
//                
//                }
                }
                
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
            System.err.println(ex.getMessage());
            return null;
        }
    }
  
}
