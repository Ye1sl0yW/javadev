/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.controller;
/**
 * FXML Controller class
 *
 * @author os
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import tn.shoppy.model.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.shoppy.services.Interaction_Points;
import tn.shoppy.services.Interaction_Portfolios;
import tn.shoppy.services.Interaction_Users;
import static java.lang.Thread.sleep;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import utils.InputCheck;


public class PointsController implements Initializable {

    @FXML private TableView<Ticket> tableView;
    @FXML private TableColumn<Ticket, Integer> id_col;
    @FXML private TableColumn<Ticket, Integer> portfolio_id_col;
    @FXML private TableColumn<Ticket, Integer> montant_col;
    @FXML private TableColumn<Ticket, Date> date_exp_col;
    @FXML private TableColumn<Ticket, String> user_col;
    @FXML private TextField search_input;
    @FXML private MenuButton search_col;
    @FXML private MenuItem search_col_id;
    @FXML private MenuItem search_col_pid;
    @FXML private MenuItem search_col_m;
    @FXML private MenuItem search_col_d;
    @FXML private TextField portfolio_id_field;
    @FXML private TextField montant_field;
    @FXML private DatePicker date_field;
  @FXML
    private MenuButton user_id_field;
    @FXML private Text id_text;
    @FXML private Text portfolio_id_text;
    @FXML private Text montant_text;
    @FXML private Text date_text;
    @FXML private Text user;
    private String username;
    private int user_id;
    private int pid;
    
    
    private ObservableList<Ticket> tablist= FXCollections.observableArrayList();
    private String searchcol;
    @FXML
    private Button ajouter_button;
    @FXML
    private Button modifier_button;
    @FXML
    private Button supprimer_button;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
         portfolio_id_col.setCellValueFactory(new PropertyValueFactory<>("portfolio_id"));
         montant_col.setCellValueFactory(new PropertyValueFactory<>("montant"));
         date_exp_col.setCellValueFactory(new PropertyValueFactory<>("date_exp"));
         user_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        resetTable();
        
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
    @Override
    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
        //Check whether item is selected and set value of selected item to Label
        if(tableView.getSelectionModel().getSelectedItem() != null) 
        {    
           TableViewSelectionModel selectionModel = tableView.getSelectionModel();
           Ticket t = (Ticket) selectionModel.getSelectedItem();
           updateFields(t);
        }
         }
     });
}

    @FXML
    private void search(KeyEvent event) {
        ResultSet r = null;
        if (searchcol=="portfolio_id") {

            if (!search_input.getText().toString().equals("")){
            String user=search_input.getText().toString();
            
             r=Interaction_Points.searchTicketsByUsername(user); // REVERIFIER MYSQL WILDCARD % BUG
            }
            else 
            {

                resetTable();
                return;
            }
        }
        else{
        r=Interaction_Points.searchTicketsBy(searchcol,search_input.getText());
        }
                updateTable(r);

    }
 
     
    void resetTable(){
        ResultSet r=Interaction_Points.getAllTickets();
        
        updateTable(r);
    }
    
    void updateTable(ResultSet r){
        tablist.clear();
        try{
         while(r.next()){
             Ticket t =new Ticket(r.getInt("id"),r.getInt("portfolio_id"),r.getInt("montant"),r.getDate("date_exp"));
             t.setUsername(Interaction_Users.getUser(t.getPortfolio_id()));
             tablist.add(t);
         }
         tableView.setItems(tablist);
    }    
        catch(SQLException e){
            
        }
        setUsers();
    }


    @FXML
    private void setSearchCol(ActionEvent event) {
        switch(((MenuItem) event.getSource()).getId()){
            case "search_col_pid": searchcol="portfolio_id"; break;
            case "search_col_m": searchcol="montant"; break;
            case "search_col_d": searchcol="date_exp"; break;
            
        }
        search_col.setText(searchcol);
}

    private void updateFields(Ticket t){
        //                  MISE A JOUR FIELDS
        portfolio_id_field.setText(String.valueOf(t.getPortfolio_id()));
        montant_field.setText(String.valueOf(t.getMontant()));
        date_field.setValue(t.getDate_exp().toLocalDate());
        
        username=Interaction_Users.getUser(t.getId());
        user_id_field.setText(username);
        pid=t.getPortfolio_id();
        //                  MISE A JOUR TEXTS
        
        id_text.setText(String.valueOf(t.getId()));
        portfolio_id_text.setText(String.valueOf(t.getPortfolio_id()));;
        montant_text.setText(String.valueOf(t.getMontant()));;
        date_text.setText(t.getDate_exp().toString());
        user.setText(Interaction_Users.getUser(t.getPortfolio_id()));
    }
    
    
    
    
    @FXML
    private void ajouterPoints(ActionEvent event) {
        
        if (!checkFields()) return;
        Ticket t=new Ticket(Integer.valueOf(pid),Integer.valueOf(montant_field.getText()),Date.valueOf(date_field.getValue()));
        Interaction_Points.ajouter(t);
        
        resetTable();
        
        
    }

    @FXML
    private void modifierPoints(ActionEvent event) {
        if (!checkFields()) return;
                Ticket t=new Ticket(Integer.valueOf(id_text.getText()),pid,Integer.valueOf(montant_field.getText()),Date.valueOf(date_field.getValue()));
                Interaction_Points.modifier(t);
                resetTable();

    }

    @FXML
    private void supprimerPoints(ActionEvent event) {
               
                Interaction_Points.supprimer(Integer.valueOf(id_text.getText()));

                resetTable();

    }

    private boolean checkFields(){
        if(InputCheck.IsInt(portfolio_id_field.getText()) && InputCheck.IsInt(montant_field.getText()) && InputCheck.IsFutureDate(Date.valueOf(date_field.getValue()))) return true;
        messageErreur("Entrées incorrectes !");
        return false;
            
    }
    private void messageErreur(String s) {
new Alert(Alert.AlertType.ERROR, s).showAndWait();  
    }


  void setUsers(){
    user_id_field.getItems().clear();
    ResultSet r = tn.shoppy.services.Interaction_Users.getAllUserswithPortfolio();
    try{
        
    while(r.next()){
        String username = r.getString("username"); //extract button text, adapt the String to the columnname that you are interested in
        MenuItem menuItem = new MenuItem(username);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            String username=((MenuItem) e.getSource()).getText();
                            user_id=Interaction_Users.getUserID(username);
                            try{
                               ResultSet r= Interaction_Portfolios.getPortfoliosBy("user_id", String.valueOf(user_id));
                               r.next();
                               
                            pid=(r.getInt("id"));

        user_id_field.setText(username);
                            }
                            
                            catch(SQLException s){
                                
                            }
                        }
});
            // add event handlers, etc, as needed..
            user_id_field.getItems().add(menuItem);

    }
    
    }
    
    
    catch(SQLException s){
        
    }
  }
}
