/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.controller.gestionAccounts;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hamdi
 */
import javafx.scene.image.Image;
import tn.shoppy.utils.HA.ConnectionDB;

public class MenuController implements Initializable {

    @FXML
    private JFXButton portfolios;
    @FXML
    private JFXButton associations;
    @FXML
    private JFXButton annonces;
    @FXML
    private JFXButton produits;
    @FXML
    private JFXButton points;
    @FXML
    private JFXButton reclamations;
    @FXML
    private JFXButton challenges;
    @FXML
    private JFXButton utilisateurs;
    @FXML
    private JFXButton dashbord;
    @FXML
    private JFXButton sms;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        associations.setOnAction(ev -> {
            Parent home_page_parent;
            try {
                home_page_parent = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/gestionAccounts/Association.fxml"));
                Scene home_page_scene = new Scene(home_page_parent);
                Stage app_stage = (Stage) ((Node) ev.getSource()).getScene().getWindow();
                app_stage.hide(); //optional
                app_stage.setScene(home_page_scene);
                app_stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        annonces.setOnAction(ev -> {
            Parent home_page_parent;
            try {
                AnchorPane p = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/gestionAccounts/AnnonceStat.fxml"));

                Scene home_page_scene = new Scene(p);
                Stage app_stage = (Stage) ((Node) ev.getSource()).getScene().getWindow();
                app_stage.hide(); //optional
                app_stage.setScene(home_page_scene);
                app_stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        utilisateurs.setOnAction(ev -> {
            Parent home_page_parent;
            try {
                AnchorPane p = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/gestionAccounts/AdminUserFXMLv2.fxml"));

                Scene home_page_scene = new Scene(p);
                Stage app_stage = (Stage) ((Node) ev.getSource()).getScene().getWindow();
                app_stage.hide(); //optional
                app_stage.setScene(home_page_scene);
                app_stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        produits.setOnAction(ev -> {
            Parent root;
            System.out.println("TEST");
            try {
                ConnectionDB cbd = ConnectionDB.getInstance();
        Connection cnx = cbd.getCnx();
                root = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/Dashboard.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Shoppy Desktop");
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/tn/shoppy/resources/images/logo.png")));

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
//        primaryStage.setResizable(false);          
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        portfolios.setOnAction(ev -> {
            Parent root;
            System.out.println("TEST");
            try {
                ConnectionDB cbd = ConnectionDB.getInstance();
        Connection cnx = cbd.getCnx();
                root = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/portfolios.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Shoppy Desktop");
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/tn/shoppy/resources/images/logo.png")));

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
//        primaryStage.setResizable(false);          
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        points.setOnAction(ev -> {
            Parent root;
            System.out.println("TEST");
            try {
                ConnectionDB cbd = ConnectionDB.getInstance();
        Connection cnx = cbd.getCnx();
                root = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/points.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Shoppy Desktop");
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/tn/shoppy/resources/images/logo.png")));

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
//        primaryStage.setResizable(false);          
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        sms.setOnAction(ev -> {
            Parent root;
            System.out.println("TEST");
            try {
                ConnectionDB cbd = ConnectionDB.getInstance();
        Connection cnx = cbd.getCnx();
                root = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/envoisms.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Shoppy Desktop");
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/tn/shoppy/resources/images/logo.png")));

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
//        primaryStage.setResizable(false);          
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    @FXML
    private void clickevent(ActionEvent event) {
    }

}
