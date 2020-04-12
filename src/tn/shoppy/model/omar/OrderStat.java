/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.model.omar;

import static java.lang.String.valueOf;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import tn.shoppy.utils.ConnectionDB;

/**
 *
 * @author asus
 */
public class OrderStat {
        Connection cnx = ConnectionDB.getCnx();

                     public ObservableList<PieChart.Data> Stats() {
        String requete = "SELECT (SELECT count(*)  from commande where total>=500 ) as note_sup , (SELECT count(*)  from commande where total<500 ) as note_inf  from commande ";
        try {
            Statement st2 = cnx.createStatement();

            ResultSet rs = st2.executeQuery(requete);
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
           rs.next() ; 
    
                pieChartData.add(new PieChart.Data("factures >= 500dt : %"+String.valueOf(100* Float.valueOf(rs.getInt(1))/Float.valueOf(rs.getInt(1)+rs.getInt(2))), rs.getInt(1)));
                 pieChartData.add(new PieChart.Data("factures < 500dt : %"+String.valueOf(100* Float.valueOf(rs.getInt(2))/Float.valueOf(rs.getInt(1)+rs.getInt(2))), rs.getInt(2)));

            

            return pieChartData;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
       
    }
    
}