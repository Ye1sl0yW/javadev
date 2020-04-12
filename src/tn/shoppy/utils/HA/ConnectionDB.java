package tn.shoppy.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class enables the connection to the data base !
 * @author Haroun
 */
public class ConnectionDB {
    //TODO use the project's data base !
//    private static String url= "jdbc:mysql://localhost:3306/test_pi?useUnicode=true" +
//        "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&" +
//        "serverTimezone=UTC";    
    private static String url = "jdbc:mysql://localhost:3306/shoppy?useUnicode=true"
            + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&"
            + "serverTimezone=UTC";
    private static String usr="root";
    private static String pwd="";
    private static ConnectionDB cbd;
    private static Connection cnx;
    
     public static Connection getCnx() {
        return cnx;
    }
    private ConnectionDB() 
    {
          try {
            cnx = DriverManager.getConnection(url, usr, pwd);
            System.out.println("Connection to data base successful !");
          } 
          catch (SQLException ex) 
          {
              System.out.println("Error: Failed to connect to the data base !\n "+ ex.getMessage());
          }
    }
    
    public static ConnectionDB getInstance()
    {   //Singleton Design Pattern
        if (cbd==null)
                cbd = new ConnectionDB();
            return cbd;  
    }

}