/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author os
 */
public class PortfoliosPresentation extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root=FXMLLoader.load(getClass().getResource("/tn/shoppy/view/portfolios.fxml"));
        Scene s = new Scene(root);
        stage.setTitle("TESTING");
        stage.setScene(s);
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
    
}
