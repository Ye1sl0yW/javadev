package tn.shoppy.controller.gestionAccounts;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.shoppy.utils.HA.ConnectionDB;

/**
 *
 * @author hamdi
 */
public class MainDashboardController implements Initializable{

    @FXML
    private ImageView StaffMgmt;
    @FXML
    private ImageView SchoolInfo;
    @FXML
    private ImageView LibraryMgmt;
    @FXML
    private ImageView EventMgmt;
    @FXML
    private ImageView InventoryMgmt;

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }
        @FXML
        private ImageView StudentMgmt;

        @FXML
        private JFXButton btnSchoolInfo;

        @FXML
        private JFXButton btnStudentMgmt;

        @FXML
        private JFXButton btnStaffMgmt;


        @FXML
        private JFXButton btnLibraryMgmt;

        @FXML
        private JFXButton btnEventMgmt;

        @FXML
        private JFXButton btnInventory;


        @FXML
        private JFXButton btnUserSettings;

//        @FXML
//        private TextField username;


        @FXML
        private AnchorPane root;

        @FXML
        void setBtnUserSettings(ActionEvent event) {

                try {
                        AnchorPane user = FXMLLoader.load(getClass().getResource(("/tn/shoppy/view/gestionAccounts/MoncompteFXML.fxml")));
                        root.getChildren().setAll(user);
                }catch(IOException e){
                        System.out.println(e);
                }

        }

        void setBtnSchoolInfo(ActionEvent event) {
                try {
                        AnchorPane user = FXMLLoader.load(getClass().getResource(("/tn/shoppy/view/gestionAccounts/Association.fxml")));
                        root.getChildren().setAll(user);
                }catch(IOException e){
                        System.out.println(e);
                }
        }

        @FXML
        void setBtnStudentMgmt(ActionEvent event) {

                try {
                        AnchorPane user = FXMLLoader.load(getClass().getResource(("/tn/shoppy/view/gestionAccounts/AnnonceAffFXML.fxml")));
                        root.getChildren().setAll(user);
                }catch(IOException e){
                        System.out.println(e);
                }

                /*try {
                        Parent root = FXMLLoader.load(getClass().getResource("/sms/view/fxml/StudentManagement.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Student Management");
                        stage.setScene(new Scene(root));
                        stage.getIcons().add(new Image(getClass().getResourceAsStream("/sms/other/img/HikmaLogo.jpg")));
                        stage.show();

                }catch(IOException e){
                        System.out.println(e);
                }*/
        }
        @FXML
        void btnStaffMgmt(ActionEvent event){
            Parent root;
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

        
/*
                try {
                        AnchorPane user = FXMLLoader.load(getClass().getResource(("/sms/view/fxml/StaffManagement.fxml")));
                        root.getChildren().setAll(user);
                }catch(IOException e){
                        System.out.println(e);
                }

                Parent root = null;
                try {
                        root = FXMLLoader.load(getClass().getResource("/sms/view/fxml/StaffManagement.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("School Information");
                        stage.setScene(new Scene(root));
                        stage.getIcons().add(new Image(getClass().getResourceAsStream("/sms/other/img/HikmaLogo.jpg")));
                        stage.show();

                } catch (IOException e) {
                        e.printStackTrace();
                }*/
        }

        @FXML
        void btnEventMgmt(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Management System");
                alert.setHeaderText(null);
                alert.setContentText("Sorry..! This feature currently Unavailable for this Version.");
                alert.showAndWait();
        }

        void btnExamMgmt(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(" Management System");
                alert.setHeaderText(null);
                alert.setContentText("Sorry..! This feature currently Unavailable for this Version.");
                alert.showAndWait();
        }

        @FXML
        void btnInventory(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Management System");
                alert.setHeaderText(null);
                alert.setContentText("Sorry..! This feature currently Unavailable for this Version.");
                alert.showAndWait();
        }

        @FXML
        void btnLibraryMgmt(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(" Management System");
                alert.setHeaderText(null);
                alert.setContentText("Sorry..! This feature currently Unavailable for this Version.");
                alert.showAndWait();
        }

        void btnNoticeMgmt(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Management System");
                alert.setHeaderText(null);
                alert.setContentText("Sorry..! This feature currently Unavailable for this Version.");
                alert.showAndWait();
        }

}

