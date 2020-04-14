/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.utils;
import java.sql.*;
/**
 *
 * @author os
 */
public class ConnexionDB {

	private static Connection conn = null;
	
	public static Connection getConnection() {
		return conn!=null?conn:getConnection("shoppy","root","");
	}
	
	private static Connection getConnection(String db, String user, String password) {
		try {
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db,user,password);
	}
		catch(SQLException e) {
			System.out.println("ERROR : Couldn't connect to DB");
		}
	
		return conn;

	}

}
