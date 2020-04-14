/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.controller.gestionAccounts;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import tn.shoppy.utils.gestionAccounts.SessionUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.shoppy.utils.HA.ConnectionDB;
import java.sql.Connection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author hamdi
 */
public class FrontFXMLController implements Initializable {

    @FXML
    private AnchorPane facebook;
    @FXML
    private AnchorPane mail;
    @FXML
    private AnchorPane twitter;
    @FXML
    private AnchorPane linkedin;
    @FXML
    private AnchorPane flickr;
    @FXML
    private AnchorPane yahoo;
    @FXML
    private AnchorPane skype;
   @FXML
    private Label username;
   
   
   
   @FXML
    private JFXButton noter;
   
   
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      username.setText(SessionUser.getUser().getFirstname() + "****** " + SessionUser.getUser().getLastname());
      
      
      
      noter.setOnAction(ev -> {
            Parent root;
            System.out.println("TEST");
            try {
                ConnectionDB cbd = ConnectionDB.getInstance();
        Connection cnx = cbd.getCnx();
                root = FXMLLoader.load(getClass().getResource("/tn/shoppy/view/ajoutnote.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Shoppy Desktop");
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/tn/shoppy/resources/images/logo.png")));

                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
//        primaryStage.setResizable(false);          
                primaryStage.show();
            } catch (IOException ex) {
            }

        });
    }    

    @FXML
    private void switchStage(MouseEvent event) {
    }
    
}
