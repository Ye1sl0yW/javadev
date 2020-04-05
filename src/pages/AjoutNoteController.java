/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;
/**
 * FXML Controller class
 *
 * @author os
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import InteractionDB.Interaction_Notes;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import utils.InputCheck;
import Entities.Note;
import Entities.Product;
import utils.testSession;




public class AjoutNoteController implements Initializable {

    
    private int product_id=1;
    @FXML private Text nom_text;
    @FXML private Text description_text;
    @FXML private Text marque_text;
    @FXML private Text prix_text;

    
    @FXML
    private MenuItem n0;
    @FXML
    private MenuItem n1;
    @FXML
    private MenuItem n2;
    @FXML
    private MenuItem n3;
    @FXML
    private MenuItem n4;
    @FXML
    private MenuItem n5;
    
    @FXML
    private Button ajouter_button;
    @FXML
    private Button annuler_button;
    @FXML
    private TextArea review;
    @FXML
    private MenuButton note;
    private int NOTE=-1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        updateFields(new Product());
        
        // PASSAGE D'INFORMATIONS ENTRE SCENES
}

   


    

    private void updateFields(Product p){
       
        //                  MISE A JOUR TEXTS
        
        nom_text.setText(p.getNom());
        description_text.setText(p.getDescription());;
        prix_text.setText(String.valueOf(p.getPrix()));;
        marque_text.setText(p.getMarque());

    }
    @FXML
    private void setNote(ActionEvent event) {
        
        NOTE=Integer.valueOf(String.valueOf(((MenuItem) event.getSource()).getId().charAt(1)));
         note.setText(String.valueOf(NOTE));
                 System.out.println(NOTE);

        
}
    
    
   

    @FXML
    private void ajouterNote(ActionEvent event) {
        Interaction_Notes.ajouter(new Note(testSession.getSession(),NOTE,0,product_id,review.getText()));
        
    }

    
    
    
    @FXML
    private void Annuler(ActionEvent event) {
    }

   

  
}
