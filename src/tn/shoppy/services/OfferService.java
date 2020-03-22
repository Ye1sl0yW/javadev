package tn.shoppy.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import tn.shoppy.model.Offer;
import tn.shoppy.model.Shop;
import tn.shoppy.utils.ConnectionDB;

/**
 *
 * @author Haroun
 */
public class OfferService {
    
    private static OfferService shopServiceInstance;
    private final Connection cn = ConnectionDB.getCnx();
    
    private OfferService(){}
       
    public static OfferService getInstance() {   //Singleton Design Pattern
        if (shopServiceInstance==null)
        {
            shopServiceInstance = new OfferService();
        }
        return shopServiceInstance;  
//        return new ShopService();
    }
        
        
    public List<Offer> getAllOffers() 
    {
        List<Offer> list = new ArrayList<>();
        int count = 0;
        System.out.println("tn.shoppy.services.OfferService.getAllOffers()");
        String query="select * from Offre ";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                
                Offer r = new Offer();
                r.setId(rs.getInt(1));
                r.setId_magasin(rs.getInt(2));
                r.setTaux(rs.getDouble(3));
                r.setNom(rs.getString(4));
                r.setDescription(rs.getString(5));
                r.setDate_debut(rs.getDate(6));
                r.setDate_fin(rs.getDate(7));
                System.out.println(r);
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
     * @param shopName
     * @param taxID
     * @return true if the operation is successful and false otherwise.
     */
    public boolean addOffer(Offer offer)
    {
        String query = "INSERT INTO Offre (id_magasin, taux, nom, description, date_debut, date_fin) VALUES (?,?,?,?,?,?)";      
        try
        {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(1, offer.getId_magasin());
            pst.setDouble(2, offer.getTaux());
            pst.setString(3, offer.getNom());
            pst.setString(4, offer.getDescription());
            pst.setDate(5, offer.getDate_debut());
            pst.setDate(6, offer.getDate_fin());

            pst.executeUpdate();
            System.out.println("Addition successful !");
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
        //TODO ajouter le false si jamais on veut ajouter une condition d'unicité sur le nom du magasin.
        
    }
    
    public boolean deleteOffer(Offer offer)
    {
        String query = "DELETE FROM Offre WHERE id=?";
        try 
        {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(1, offer.getId());
            pst.executeUpdate();
            System.out.println("Deletion successful !");
        } 
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
    
    
    public boolean updateOffer(Offer offer)
    {
        System.out.println(offer);
        String query = "UPDATE Offre SET id_magasin=?, taux=?, nom=?, description=?, date_debut=?, date_fin=? WHERE id=?";
        try {
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setInt(1, offer.getId_magasin());
            pst.setDouble(2, offer.getTaux());
            pst.setString(3, offer.getNom());
            pst.setString(4, offer.getDescription());
            pst.setDate(5, offer.getDate_debut());
            pst.setDate(6, offer.getDate_fin());
            System.out.println("Update successful !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return true;
        }       
        return false;
    }
//TODO search operations    
/**
 * Search operations
 * @param input : pattern your are looking for
 * @return 
 */



}
