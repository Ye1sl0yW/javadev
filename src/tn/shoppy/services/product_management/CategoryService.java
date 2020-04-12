package tn.shoppy.services.product_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.shoppy.model.Category;
import tn.shoppy.utils.HA.ConnectionDB;

/**
 *
 * @author anas fattoum
 */
public class CategoryService {
    
    private static CategoryService categoryServiceInstance;
    private final Connection cn = ConnectionDB.getCnx();

    
    private CategoryService(){}
       
    public static CategoryService getInstance() {   //Singleton Design Pattern
        if (categoryServiceInstance==null)
        {
            categoryServiceInstance = new CategoryService();
        }
        return categoryServiceInstance;  
    }

    public List<Category> getAllCategory() 
    {
        List<Category> list = new ArrayList<>();
        int count = 0;
        
        String query="select * from categorie ";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                
                Category r = new Category();
                r.setId(rs.getInt(1));
                r.setNom(rs.getString(2));
               
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
    
    

    public boolean addCategory(Category category) throws SQLException
    {
        System.out.println(category.getNom() + " " + category.getId());
        
        PreparedStatement pst = cn.prepareStatement("INSERT INTO categorie "
                + "(nom)"
                + "VALUES (?)");
            
        try
        {
            System.out.println("adding category"+category);
            pst.setString(1,category.getNom());

            
            System.out.println(pst);
            pst.executeUpdate();
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
            return false;
        }
        return true;        
    }
    
    
    public boolean deleteCategory(Category category)
    {
        String query = "DELETE FROM categorie WHERE id=?";
        try 
        {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(1, category.getId());
            pst.executeUpdate();
            System.out.println("Deletion successful !");
        } 
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
    
    
    
    public boolean updateCategory(Category category)
    {
        System.out.println(category);
        String query = "UPDATE categorie SET nom=? WHERE id=?";
        try {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(2,category.getId());
            
            pst.setString(1,category.getNom());
  
            System.out.println(pst);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return true;
        }       
        return false;
    }
    
    
    
     public List<Category> findCategoryByName(String pattern)
    {
        List<Category> list = new ArrayList<>();
        int count = 0;
        String query = "SELECT * FROM categorie WHERE nom like '%"+ pattern +"%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Category r = new Category();
                r.setId(rs.getInt(1));
                r.setNom(rs.getString(2));

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

    String findCategoryNameByID(int id) {
         List<String> list = new ArrayList<>();
        int count = 0;
        
        String query="select * from categorie where id= "+id+" ;";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String r = rs.getString(2);             
                list.add(r);
                count++;
            }
            if(count == 0)
            {
                return null;
            }
            else
            {
               return list.get(0);
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public Category findOneCategoryByID(int id) {
         List<Category> list = new ArrayList<>();
        int count = 0;
        
        String query="select * from categorie where id= "+id+" ;";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Category r = new Category();
                r.setId(rs.getInt(1));
                r.setNom(rs.getString(2));
               
                list.add(r);
                count++;
            }
            if(count == 0)
            {
                return null;
            }
            else
            {
               return list.get(0);
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    
}
