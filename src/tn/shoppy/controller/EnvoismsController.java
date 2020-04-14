/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import tn.shoppy.services.Interaction_Users;
import tn.shoppy.utils.Interaction_SMS;

/**
 * FXML Controller class
 *
 * @author os
 */
public class EnvoismsController implements Initializable {

    @FXML
    private Button envoyer_button;
    @FXML
    private MenuButton user_id_field;
    @FXML
    private TextArea message;
    private String number;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUsers();
        // TODO
    }

    @FXML
    private void envoyer_sms(ActionEvent event) {
        Interaction_SMS.sendSMS("21653133667", message.getText());
    }

    void setUsers() {
        user_id_field.getItems().clear();
        ResultSet r = tn.shoppy.services.Interaction_Users.getAllUsers();
        try {

            while (r.next()) {
                String username = r.getString("username"); 
                MenuItem menuItem = new MenuItem(username);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        String username = ((MenuItem) e.getSource()).getText();

                        number = Interaction_Users.getNumber(username);

                        user_id_field.setText(username);
                    }
                });
                // add event handlers, etc, as needed..
                user_id_field.getItems().add(menuItem);

            }

        } catch (SQLException s) {

        }
    }
}