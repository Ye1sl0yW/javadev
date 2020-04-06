package tn.shoppy.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import tn.shoppy.model.Shop;
import tn.shoppy.utils.ConnectionDB;

/**
 *
 * @author Haroun
 */
public class ShopService {
    
    private static ShopService shopServiceInstance;
    private final Connection cn = ConnectionDB.getCnx();
    
    
    public static ShopService getInstance() {   //Singleton Design Pattern
        if (shopServiceInstance==null)
        {
            shopServiceInstance = new ShopService();
        }
        return shopServiceInstance;  
//        return new ShopService();
    }
        
        
    public List<Shop> getAllShops() 
    {
        List<Shop> list = new ArrayList<>();
        int count = 0;
        
        String query="select * from Magasin ";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                
                Shop r = new Shop();
                r.setId(rs.getInt(1));
                r.setId_vendeur(rs.getInt(2));
                r.setNom(rs.getString(3));
                r.setTaille_stock(rs.getInt(4));
                r.setMatricule_fiscal(rs.getInt(5));
                
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
        
    /**
     *  This method is used to add a shop to the dataBase. Call it in the
     * ShopController class. 
     * DISCLAIMER: the input checking must be done BEFORE using this methods.
     * @param shopName
     * @param taxID
     * @return true if the operation is successful and false otherwise.
     */
    public boolean addShop(Shop shop)
    {     
        if (shop.getId_vendeur() == 0)
        {
            return addShopNoSeller(shop);
        }
        
        String query = "INSERT INTO Magasin (nom,id_vendeur,matricule_fiscal) VALUES (?,?,?)";      
        try
        {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, shop.getNom());
            pst.setInt(2, shop.getId_vendeur());
            pst.setInt(3, shop.getMatricule_fiscal());
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
    
    public boolean addShopNoSeller(Shop shop)
    {
        String query = "INSERT INTO Magasin (nom,matricule_fiscal) VALUES (?,?)";      
        try
        {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, shop.getNom());
            pst.setInt(2, shop.getMatricule_fiscal());
            pst.executeUpdate();
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
            return false;
        }
        return true; 
    }
    
    public boolean deleteShop(Shop shop)
    {
        String query = "DELETE FROM Magasin WHERE id=?";
        try 
        {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(1, shop.getId());
            pst.executeUpdate();
            System.out.println("Deletion successful !");
        } 
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
    
    
    public boolean updateShop(Shop shop)
    {
        System.out.println(shop);
        String query = "UPDATE Magasin SET nom=?,matricule_fiscal=? WHERE id=?";
        try {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(3, shop.getId());
            pst.setString(1, shop.getNom());
            pst.setInt(2, shop.getMatricule_fiscal());
            pst.executeUpdate();
            System.out.println("Update successful !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return true;
        }       
        return false;
    }
/**
 * Search operations
 * @param input : pattern your are looking for
 * @return 
 */
    public List<Shop> findShops(String input) {  
        HashSet<Shop> cleanResult = new HashSet<>();
        List<Shop> r1 = findShopsByName(input);
        List<Shop> r2 = findShopsByTaxID(input);
        List<Shop> r3 = findShopsByID(input);

        if(r1 != null){
            cleanResult.addAll(r1);
        }
        if(r2 != null){
            cleanResult.addAll(r2);
        }
        if(r3 != null){
            cleanResult.addAll(r3);
        }
        List<Shop> result = new ArrayList<>(cleanResult);
        return result;
    }
    

    public List<Shop> findShopsByName(String name) {  
     List<Shop> list = new ArrayList<>();
        int count = 0;
        String query="select * from Magasin where nom like '%"+name+"%'";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                
                Shop r = new Shop();
                r.setId(rs.getInt(1));
                r.setId_vendeur(rs.getInt(2));
                r.setNom(rs.getString(3));
                r.setTaille_stock(rs.getInt(4));
                r.setMatricule_fiscal(rs.getInt(5));
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
    
    public List<Shop> findShopsByTaxID(String taxID) {
        List<Shop> list = new ArrayList<>();
        int count = 0;
        String query = "SELECT * FROM Magasin WHERE convert(matricule_fiscal,CHARACTER) like '%" + taxID + "%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                Shop r = new Shop();
                r.setId(rs.getInt(1));
                r.setId_vendeur(rs.getInt(2));
                r.setNom(rs.getString(3));
                r.setTaille_stock(rs.getInt(4));
                r.setMatricule_fiscal(rs.getInt(5));
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
    
        public List<Shop> findShopsByID(String ID) {
        List<Shop> list = new ArrayList<>();
        int count = 0;
        String query = "SELECT * FROM Magasin WHERE convert(id,CHARACTER) like '%" + ID + "%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                Shop r = new Shop();
                r.setId(rs.getInt(1));
                r.setId_vendeur(rs.getInt(2));
                r.setNom(rs.getString(3));
                r.setTaille_stock(rs.getInt(4));
                r.setMatricule_fiscal(rs.getInt(5));
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
        
        public Shop findOneShopByID(int ID) {
        String query = "SELECT * FROM Magasin WHERE id = " + ID;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) 
            {
                Shop r = new Shop();
                r.setId(rs.getInt(1));
                r.setId_vendeur(rs.getInt(2));
                r.setNom(rs.getString(3));
                r.setTaille_stock(rs.getInt(4));
                r.setMatricule_fiscal(rs.getInt(5));
                return r;
            }
            return null;
        } 
        catch (SQLException ex) 
        {
            System.err.println(ex.getMessage());
            return null;
        }
    }        

    public List<Integer> getAvailableSellers()
    {
        List<Integer> list = new ArrayList<>();
        int count = 0;
        String query = "select id from users where ROLES like '%ROLE_VENDEUR%' and id_magasin IS NULL ";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) 
            {
                Integer r = rs.getInt(1);
                count++;
                list.add(r);
            }
            return list;
        } 
        catch (SQLException ex) 
        {
            System.err.println(ex.getMessage());
            return null;
        }
    }

}
