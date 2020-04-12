/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.controller;

import tn.shoppy.model.Portfolio;
import java.sql.*;
import tn.shoppy.services.Interaction_Portfolios;
import tn.shoppy.services.Interaction_Users;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import tn.shoppy.utils.HA.InputCheck;

/**
 * FXML Controller class
 *
 * @author os
 */
public class PortfoliosController implements Initializable {

    @FXML
    private TableView<Portfolio> tableView;
    @FXML
    private TableColumn<Portfolio, Integer> id_col;
    @FXML
    private TableColumn<Portfolio, Integer> user_id_col;
    @FXML
    private TableColumn<Portfolio, Integer> montant_col;
    @FXML
    private TableColumn<Portfolio, Integer> user_col;
    @FXML
    private TextField search_input;
    @FXML
    private MenuButton search_col;
    @FXML
    private MenuItem search_col_id;
    @FXML
    private MenuItem search_col_uid;
    @FXML
    private MenuItem search_col_m;

    private ObservableList<Portfolio> tablist= FXCollections.observableArrayList();
    private String searchcol;
    @FXML
    private Text montant_text;
    @FXML
    private Text id_text;
    @FXML
    private Button ajouter_button;
    @FXML
    private Button modifier_button;
    @FXML
    private Button supprimer_button;
    @FXML
    private MenuButton user_id_field;
    @FXML
    private Text user_id_text;
    @FXML
    private Text user_text;
    private int user_id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
         user_id_col.setCellValueFactory(new PropertyValueFactory<>("user_id"));
         user_col.setCellValueFactory(new PropertyValueFactory<>("username"));
         montant_col.setCellValueFactory(new PropertyValueFactory<>("montant"));
         resetTable();
         
         
         tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
    @Override
    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
        //Check whether item is selected and set value of selected item to Label
        if(tableView.getSelectionModel().getSelectedItem() != null) 
        {    
           TableViewSelectionModel selectionModel = tableView.getSelectionModel();
           Portfolio t = (Portfolio) selectionModel.getSelectedItem();
           updateFields(t);
        }
         }
     });
         
         
    }    

    
    
    
    
    
    
    
    @FXML
    private void search(KeyEvent event) {
        ResultSet r=null;
        if (searchcol=="montant") {

            if (!search_input.getText().toString().equals("")){
            String montant=search_input.getText().toString();
             r=Interaction_Portfolios.searchPortfoliosByMontant(montant); // REVERIFIER MYSQL WILDCARD % BUG
            }
            else 
            {

                resetTable();
                return;
            }
        }
        if (searchcol=="user_id") {

            if (!search_input.getText().toString().equals("")){
            String user=search_input.getText().toString();
             r=Interaction_Portfolios.searchPortfoliosByUser(user); // REVERIFIER MYSQL WILDCARD % BUG
            }
            else 
            {

                resetTable();
                return;
            }
        }
        else{
             r=Interaction_Portfolios.searchPortfoliosBy(searchcol,search_input.getText());
        }
        updateTable(r);
    }
    
    
    @FXML
    private void setSearchCol(ActionEvent event) {
         switch(((MenuItem) event.getSource()).getId()){
            case "search_col_uid": searchcol="user_id"; break;
            case "search_col_m": searchcol="montant"; break;
            
        }
        search_col.setText(searchcol);
    }
    
    
    
    void resetTable(){
        ResultSet r=Interaction_Portfolios.getAllPortfolios();

        updateTable(r);
    }
    
    void updateTable(ResultSet r){
        tablist.clear();
        try{
         while(r.next()){
             Portfolio p=new Portfolio(r.getInt("id"),r.getInt("user_id"));
             p=new Portfolio(p.getId(),p.getUser_id(),Interaction_Portfolios.getMontant(p));
             p.setUsername(Interaction_Users.getUser(p.getId()));
             tablist.add(p);
         }
         tableView.setItems(tablist);
    }    
        catch(SQLException e){

        }
        setUsers();
        
    }

    
     private void updateFields(Portfolio t){
        //                  MISE A JOUR FIELDS
        user_id_field.setText(Interaction_Users.getUser(t.getId()));
        user_id=t.getUser_id();
        System.out.println(user_id);
        
        //                  MISE A JOUR TEXTS
        
        id_text.setText(String.valueOf(t.getId()));
        user_id_text.setText(String.valueOf(t.getUser_id()));;
        user_text.setText(t.getUsername());;
        montant_text.setText(String.valueOf(t.getMontant()));;

    }
    
    void setUsers(){
    user_id_field.getItems().clear();
    ResultSet r = tn.shoppy.services.Interaction_Users.getAllUsersnoPortfolio();
    try{
        
    while(r.next()){
        String username = r.getString("username"); //extract button text, adapt the String to the columnname that you are interested in
        MenuItem menuItem = new MenuItem(username);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            String username=((MenuItem) e.getSource()).getText();
            
            
                user_id=Interaction_Users.getUserID(username);

        user_id_field.setText(username);
                        }
});
            // add event handlers, etc, as needed..
            user_id_field.getItems().add(menuItem);

    }
    
    }
    
    
    catch(SQLException s){
        
    }
    
    
    
    
    
    
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void ajouterPortfolio(ActionEvent event) {
        //checkFields();
        Portfolio t=new Portfolio(user_id);
        Interaction_Portfolios.ajouter(t);

        resetTable();

    }

    @FXML
    private void modifierPortfolio(ActionEvent event) {
        //checkFields();
        
        Portfolio t=new Portfolio(Integer.valueOf(id_text.getText()),user_id);
                Interaction_Portfolios.modifier(t);
                resetTable();
    }

    @FXML
    private void supprimerPortfolio(ActionEvent event) {
        Interaction_Portfolios.supprimer(Integer.valueOf(id_text.getText()));

                resetTable();
    }
    
    
    
    private boolean checkFields(){
        if(InputCheck.testNumberInput(user_id_field.getText())) return true;
        messageErreur("Entrées incorrectes !");
        return false;
            
    }
    private void messageErreur(String s) {
new Alert(Alert.AlertType.ERROR, s).showAndWait();  
    }
}
