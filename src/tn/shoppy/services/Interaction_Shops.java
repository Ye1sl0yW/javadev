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
import tn.shoppy.utils.ConnexionDB;

/**
 *
 * @author os
 */
public class Interaction_Shops {
    public static ResultSet getAllShops(){
            ResultSet r = null;
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select * from magasin");
		
	}
		catch(SQLException e) {
			
		}
            
            return r;
        }
    
    
    public static ResultSet getShopByName(String n){
            ResultSet r = null;
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select * from magasin where nom='"+n+"'");
		
	}
		catch(SQLException e) {
			
		}
            
            return r;
        }
}
