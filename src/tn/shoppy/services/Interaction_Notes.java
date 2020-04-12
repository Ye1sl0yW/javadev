/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.services;
import tn.shoppy.model.Note;
import java.sql.*;
import utils.ConnexionDB;

/**
 *
 * @author os
 */
public class Interaction_Notes {
    public static boolean ajouter( Note n) { //temporary -> arg = Ticket
		try {
			Connection c = ConnexionDB.getConnection();
                        PreparedStatement s;
                        if (n.getType()==0){
                            
                        
			s = c.prepareStatement("insert into notes (user_id,type,value,produit_id,text) values(?,?,?,?,?)");
			
			s.setInt(4, n.getProduit_id());
                        }
                        
                        else{
                            s = c.prepareStatement("insert into notes (user_id,type,value,magasin_id,text) values(?,?,?,?,?)");
                            s.setInt(4, n.getMagasin_id());

                        }
                        s.setInt(1, n.getUser_id());
			s.setInt(2, n.getType());
			s.setInt(3, n.getValue());
                        s.setString(5, n.getText());

			s.executeUpdate(); //insertion + nombre de ligne insérées
			
			
			return true;
		}
		catch(SQLException e) {
			return false;
		}
	}

        
        public static boolean supprimer(int id) { //temporary -> arg = Ticket
		try {
			Connection c = ConnexionDB.getConnection();
			PreparedStatement s = c.prepareStatement("delete from notes where id=?");
			s.setInt(1, id);
			s.executeUpdate(); //insertion + nombre de ligne insérées
			
			
			return true;
		}
		catch(SQLException e) {
			return false;
		}
	}
        
        
        
        
        
        
        public static boolean modifier( Note n) { //temporary -> arg = Ticket
		try {
			Connection c = ConnexionDB.getConnection();
			PreparedStatement s = c.prepareStatement("update notes set user_id=?,type=?,value=?,magasin_id=?,produit_id=?,text=? where id=?");
			s.setInt(1, n.getUser_id());
			s.setInt(2, n.getType());
			s.setInt(3, n.getValue());
			s.setInt(4, n.getMagasin_id());
			s.setInt(5, n.getProduit_id());
			s.setString(6, n.getText());
			s.executeUpdate(); //insertion + nombre de ligne insérées
			
			
			return true;
		}
		catch(SQLException e) {
			return false;
		}
	}
        
        public static ResultSet getAllNotes(){
            ResultSet r = null;
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select * from notes");
		
	}
		catch(SQLException e) {
			
		}
            
            return r;
        }
        
        
        public static ResultSet getNotesBy(String col, String value){
            ResultSet r = null;
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select * from notes where "+ col+"='"+value+"'");
		
	}
		catch(SQLException e) {
			
		}
            
            return r;
        }
        public static ResultSet searchNotesBy(String col, String value){
            ResultSet r = null;
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select * from notes where "+ col+" LIKE '"+value+"%'");
		
	}
		catch(SQLException e) {
			
		}
            
            return r;
        }
        
        

        

        
}
