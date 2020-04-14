/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import tn.shoppy.model.Product;
import tn.shoppy.utils.ConnexionDB;

/**
 *
 * @author os
 */
public class Interaction_Products {
    public static ResultSet getAllProducts(){
            ResultSet r = null;
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select * from produit");
		
	}
		catch(SQLException e) {
			
		}
            
            return r;
        }
    
    public static Product getProductByName(String n){
            ResultSet r = null;
            Product p = new Product();

            try {
		Connection c = ConnexionDB.getConnection();


		Statement s = c.createStatement();
                 r=s.executeQuery("select id from produit where nom LIKE 'Peaugeot 508' ");

		//r=s.executeQuery("select * from produit where nom='"+n+"'");
                                System.out.println(r.getInt("id"));

                p.setId(r.getInt("id"));
                p.setNom(r.getString("nom"));
                p.setMarque(r.getString("marque"));
                p.setDescription(r.getString("description"));
                
                System.out.println(p);
	}
		catch(SQLException e) {
			System.out.println("Erreur dans la requete?");
		}
            
            return p;
        }
}
