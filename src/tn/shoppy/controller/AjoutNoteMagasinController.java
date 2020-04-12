/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.controller;

import tn.shoppy.model.Note;
import tn.shoppy.model.Magasin;
import tn.shoppy.services.Interaction_Notes;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import utils.testSession;

/**
 * FXML Controller class
 *
 * @author os
 */
public class AjoutNoteMagasinController implements Initializable {

    @FXML
    private Text nom_text;
    @FXML
    private Button ajouter_button;
    @FXML
    private Button annuler_button;
    @FXML
    private TextArea review;
    @FXML
    private MenuButton note;
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
    private int magasin_id=3;
private int NOTE=-1;
    /**
     * Initializes the controller class.
     */
    @Override
   public void initialize(URL url, ResourceBundle rb) {
        // TODO
        updateFields(new Magasin());
        
        // PASSAGE D'INFORMATIONS ENTRE SCENES
}

   


    

    private void updateFields(Magasin m){
       
        //                  MISE A JOUR TEXTS
        
        nom_text.setText(m.getNom());
        

    }
    @FXML
    private void setNote(ActionEvent event) {
        
        NOTE=Integer.valueOf(String.valueOf(((MenuItem) event.getSource()).getId().charAt(1)));
         note.setText(String.valueOf(NOTE));
                 System.out.println(NOTE);

        
}
    
    
   

    @FXML
    private void ajouterNote(ActionEvent event) {
        Interaction_Notes.ajouter(new Note(testSession.getSession(),NOTE,1,magasin_id,review.getText()));
        
    }

    
    
    
    @FXML
    private void Annuler(ActionEvent event) {
    }

   

}
