package tn.shoppy.services.GestionCommandes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import tn.shoppy.model.omar.Product;
import tn.shoppy.utils.ConnectionDB;

/**
 *
 * @author anas fattoum
 */
public class ProductService {
    
    private static ProductService productServiceInstance;
    private final Connection cn = ConnectionDB.getCnx();
    
    private ProductService(){}
       
    public static ProductService getInstance() {   //Singleton Design Pattern
        if (productServiceInstance==null)
        {
            productServiceInstance = new ProductService();
        }
        return productServiceInstance;  
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
    
   
    

    public boolean addProduct(Product product) throws SQLException
    {
        System.out.println(product.getNom() + " " + product.getId());
        
        PreparedStatement pst = cn.prepareStatement("INSERT INTO Produit "
                + "(id_magasin, quantite, nom, description, prix, marque, updated_at)"
                + "VALUES (?,?,?,?,?,?,?)");
            
        try
        {
            System.out.println("adding product"+product);
            pst.setInt(1,product.getId_magasin());
            pst.setInt(2,product.getQuantite());
            pst.setString(3,product.getNom());
            pst.setString(4,product.getDescription());
            pst.setDouble(5,product.getPrix());
            pst.setString(6,product.getMarque());
            pst.setDate(7,product.getUpdated_at());
            
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
    
    public boolean deleteProduct(Product product)
    {
        String query = "DELETE FROM Produit WHERE id=?";
        try 
        {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(1, product.getId());
            pst.executeUpdate();
            System.out.println("Deletion successful !");
        } 
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
    
    
    public boolean updateProduct(Product product)
    {
        System.out.println(product);
        String query = "UPDATE Produit SET nom=?, id_magasin=?, quantite=?, description=?, prix=?, marque=?, updated_at=? WHERE id=?";
        try {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(8,product.getId());
            
            pst.setString(1,product.getNom());
            pst.setInt(2,product.getId_magasin());
            pst.setInt(3,product.getQuantite());
            pst.setString(4,product.getDescription());
            pst.setDouble(5,product.getPrix());
            pst.setString(6,product.getMarque());
            pst.setDate(7,product.getUpdated_at());
            
            System.out.println(pst);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return true;
        }       
        return false;
    }
    
     public List<Product> findProductByNameOrDescription(String pattern)
    {
        List<Product> list = new ArrayList<>();
        int count = 0;
        String query = "SELECT * FROM Produit WHERE nom like '%"+ pattern +"%' or description like '%"+ pattern +"%'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Product r = new Product();
                r.setId(rs.getInt(1));
                r.setId_magasin(rs.getInt(2));
                r.setNom(rs.getString(4));
                r.setQuantite(rs.getInt(3));
                r.setDescription(rs.getString(5));
                r.setPrix(rs.getDouble(6));
                r.setMarque(rs.getString(7));
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
}
