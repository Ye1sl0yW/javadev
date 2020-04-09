package tn.shoppy.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import tn.shoppy.model.Category;
import tn.shoppy.utils.ConnectionDB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import tn.shoppy.services.CategoryService;
import tn.shoppy.utils.InputCheck;

/**
 *
 * @author anas fattoum
 */
public class CategoryController implements Initializable{
    
    Connection cnx = ConnectionDB.getCnx();
    
    /**
     * UI elements
     */
    @FXML
    private TableView<Category> categoryTable;
    @FXML
    private TableColumn<Category, Integer> categoryIdColumn;
    @FXML
    private TableColumn<Category, String> categoryNameColumn;
   
    
    @FXML
    private TextField addCategoryNameField;

    
    @FXML
    private TextField updateCategoryNameField;

    @FXML
    private Label searchCategoryLabel;
    private ObservableList<Category> categoryData = FXCollections.observableArrayList();

    @FXML
    private TextField searchCategoryField;

    @FXML
    private ImageView productHelpImage;
    private Tooltip helpTooltip;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        List<Category> categoryList = new ArrayList<>();
        CategoryService categoryService = CategoryService.getInstance();
        categoryList = categoryService.getAllCategory();
        categoryData.clear();
        if (categoryList != null) {
            categoryData.addAll(categoryList);
            categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));


           

            categoryTable.setItems(categoryData);
            searchCategoryLabel.setText("Résultat : " + categoryList.size() + " ligne(s).");
        } else {
            searchCategoryLabel.setText("Aucun résultat.");
            categoryTable.setPlaceholder(new Label("Il n'y a aucune catégorie dans la base de données. Veuillez en rajouter! "));
        }
        

        helpTooltip = new Tooltip("Vous êtes dans l'onglet de getion des categories.\n"
                + "Ici, vous pouvez visualiser la liste des produits des partenaires de Shoppy,\n "
                + "en rechercher par identifiant, nom .\n"
                + "Il est aussi possible d'en ajouter de nouvelles ou de modifier celles existantes.\n"
                + "Il est aussi posible d'en supprimer un ou plusieures.\n"
                + "Pour la sélection multiple, il vous suffit de maintenir la touche Ctrl de votre clavier\n"
                + "enfoncée lors du clic de sélection.");
        Tooltip.install(productHelpImage, helpTooltip);
    }

     //********************* C **************************//
    @FXML
    public void addCategoryAction() throws SQLException {
        CategoryService categoryService = CategoryService.getInstance();
        InputCheck inputCheck = InputCheck.getInstance();
        String name = addCategoryNameField.getText();
 

        boolean result = false;

        if (inputCheck.testTextInput(name)) {

    
            Category c = new Category(0,name);
            System.out.println(c);
            result = categoryService.addCategory(c) ;
        } 
        else 
        {
            System.out.println("WIP : Error dialog => Wrong input format !");
        }
        if (result) {
            categoryData.clear();
            refreshTableData();
            System.out.println("Succès de l'ajout de la catégorie !");
        } else {
            System.out.println("Echec de l'ajout de la catégorie !");

        }

    }
    
    //********************* R **************************//
    public void refreshTableData() {
        List<Category> categoryList = new ArrayList<>();
        CategoryService categoryService = CategoryService.getInstance();
        categoryList = categoryService.getAllCategory();
        categoryData.clear();
        if (categoryList != null) {
            categoryData.addAll(categoryList);
            categoryTable.setItems(categoryData);
            searchCategoryLabel.setText("Résultat : " + categoryList.size() + " ligne(s).");
        } else {
            searchCategoryLabel.setText("Aucun résultat.");
            categoryTable.setPlaceholder(new Label("Il n'y a aucune catégorie dans la base de données. Veuillez en rajouter! "));
        }
    }
    
         //********************* U **************************//
        
    @FXML
    public void selectOneCategoryAction(KeyEvent keyEvent) {
        Category category = (Category) categoryTable.getSelectionModel().getSelectedItem();
        if(category != null)
        {
            fillUpdateForm(category);
        }
    }
    @FXML
    public void clickOneCategoryAction() {
        Category category = (Category) categoryTable.getSelectionModel().getSelectedItem();
        if(category != null)
        {
            fillUpdateForm(category);
        }
    }
    
    @FXML
    public void updateCategoryAction() {
        Category selection = categoryTable.getSelectionModel().getSelectedItem();
        InputCheck inputCheck = InputCheck.getInstance();
        if (selection != null)
        {   
            Category category = new Category();
            category.setId(selection.getId());
            String newName = updateCategoryNameField.getText();

            
            Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir modifier la catégoorie: "+selection.getNom()+" de la base de données ?",ButtonType.YES,ButtonType.NO);
            a.showAndWait();
            
            if(a.getResult()==ButtonType.YES){
                if (inputCheck.testTextInput(newName)) 
       
                {
                    category.setNom(newName);
         
                    
                    CategoryService categoryService = CategoryService.getInstance();
                    categoryService.updateCategory(category);
                    refreshTableData();
                    a.close();
                }
                else
                {
                    Alert inputAlert = new Alert(Alert.AlertType.ERROR,"Le format de données saisi est incorrect.",ButtonType.OK);
                    inputAlert.showAndWait();
                }
            }else{
            a.close();
            }
        }
        else
        {   
            Alert a=new Alert(Alert.AlertType.WARNING,"Aucune séléction !",ButtonType.CLOSE); 
            a.showAndWait();
        }
        refreshTableData();
    }    
        
    //********************* D **************************//
    @FXML
    public void deleteCategoryAction() {
        ObservableList<Category> selectedItems = categoryTable.getSelectionModel().getSelectedItems();
        System.out.println(selectedItems);
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr(e) de vouloir supprimer ces " + selectedItems.size() + " éléments de la base de données ?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            CategoryService categoryService = CategoryService.getInstance();
            for (Category c : selectedItems) {
                categoryService.deleteCategory(c);
            }
            refreshTableData();
            a.close();
        } else {
            a.close();
        }
    }

    private void fillUpdateForm(Category category) {
        updateCategoryNameField.setText(category.getNom());
    }
    
        //************ SEARCH *********************//
    
     public void searchCategoryAction() {
        List<Category> resultList = new ArrayList<>();
        CategoryService categoryService = CategoryService.getInstance();
        String input = searchCategoryField.getText();
        if(input.length()>0)
        {
            resultList = categoryService.findCategoryByName(input);
            categoryData.clear();
            categoryData.addAll(resultList);
            categoryTable.setItems(categoryData);   
            searchCategoryLabel.setText("Résultat : "+resultList.size()+" ligne(s).");
        }
        else
        {
            refreshTableData();
        }
    }
     
     public void typingSearchCategoryAction(KeyEvent event) {
        List<Category> resultList = new ArrayList<>();
        CategoryService categoryService = CategoryService.getInstance();
        String input = searchCategoryField.getText();
        if(input.length()>0)
        {
            resultList = categoryService.findCategoryByName(input);
            categoryData.clear();
            categoryData.addAll(resultList);
            searchCategoryLabel.setText("Résultat : "+resultList.size()+" ligne(s).");
            categoryTable.setItems(categoryData);   
        }
        else
        {
            refreshTableData();
        }
    }
    
    
    
    
}
