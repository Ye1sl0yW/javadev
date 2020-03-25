
package InteractionDB;
import Entities.Portfolio;
import java.sql.*;
import utils.ConnexionDB;
/**
 *
 * @author os
 */
public class Interaction_Portfolios {
    
    	public static boolean ajouter( Portfolio p) { //temporary -> arg = Ticket
		try {
			Connection c = ConnexionDB.getConnection();
			PreparedStatement s = c.prepareStatement("insert into portfolios (user_id) values(?)");
			s.setInt(1, p.getUser_id());
			s.executeUpdate(); //insertion + nombre de ligne insérées
			
			c.close();
			
			return true;
		}
		catch(SQLException e) {
			return false;
		}
	}

        
        public static boolean supprimer(int id) { //temporary -> arg = Ticket
		try {
			Connection c = ConnexionDB.getConnection();
			PreparedStatement s = c.prepareStatement("delete from portfolios where id=?");
			s.setInt(1, id);
			s.executeUpdate(); //insertion + nombre de ligne insérées
			
			c.close();
			
			return true;
		}
		catch(SQLException e) {
			return false;
		}
	}
        
        
        
        
        
        
        public static boolean modifier( Portfolio p) { //temporary -> arg = Ticket
		try {
			Connection c = ConnexionDB.getConnection();
			PreparedStatement s = c.prepareStatement("update portfolios set user_id=? where id=?");
			s.setInt(1, p.getUser_id());
			s.setInt(2, p.getId());
			s.executeUpdate(); //insertion + nombre de ligne insérées
			
			c.close();
			
			return true;
		}
		catch(SQLException e) {
			return false;
		}
	}
        
        public static ResultSet getAllTickets(){
            ResultSet r = null;
            try {
		Connection c = ConnexionDB.getConnection();
		Statement s = c.createStatement();
		r=s.executeQuery("select * from portfolios");
		
	}
		catch(SQLException e) {
			
		}
            
            return r;
        }
        
}