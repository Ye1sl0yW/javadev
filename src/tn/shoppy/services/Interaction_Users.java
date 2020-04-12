/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.services;
import java.sql.*;
import utils.ConnexionDB;
/**
 *
 * @author os
 */
public class Interaction_Users {
    
    public static String getUser(int id){
            ResultSet r = null;
            String username="ERREUR";
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select username from users u join portfolios p on (u.id=p.user_id) where p.id='"+ id+"'");
                if (r.next()) username=r.getString("username");
	}
		catch(SQLException e) {
			System.out.println("ERREUR");
		}
            
            return username;
        }
    
    public static ResultSet getAllUsersnoPortfolio(){
            ResultSet r = null;
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select username from users");
                // where portfolio_id is null
	}
		catch(SQLException e) {
			System.out.println("ERREUR");
		}
            
            return r;
        }
    
    public static int getUserID(String username){
            int id=-1;
            ResultSet r = null;
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select id from users where username='"+ username+"'");
                if (r.next()) id=r.getInt("id");
	}
		catch(SQLException e) {
			System.out.println("ERREUR");
		}
            
            return id;
        }
    
    
    public static String getUsername(int id){
            ResultSet r = null;
            String username="ERREUR";
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select username from users where id='"+ id+"'");
                if (r.next()) username=r.getString("username");
	}
		catch(SQLException e) {
			System.out.println("ERREUR");
		}
            
            return username;
        }
    
   public static ResultSet getAllUserswithPortfolio(){
            ResultSet r = null;
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select username from users");
                // where portfolio_id is not null
	}
		catch(SQLException e) {
			System.out.println("ERREUR");
		}
            
            return r;
        }
}
