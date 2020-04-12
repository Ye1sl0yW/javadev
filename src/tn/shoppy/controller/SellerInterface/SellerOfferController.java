package tn.shoppy.controller.SellerInterface;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import tn.shoppy.controller.dashboard.shop_management.ShopController;
import tn.shoppy.model.Offer;
import tn.shoppy.model.Shop;
import tn.shoppy.services.shop_management.ExcelExport;
import tn.shoppy.services.shop_management.OfferService;
import tn.shoppy.services.shop_management.ShopService;
import tn.shoppy.utils.HA.ConnectionDB;
import tn.shoppy.utils.HA.InputCheck;

/**
 *
 * @author Haroun
 */
public class SellerOfferController implements Initializable {

    private Shop sessionShop;
    Connection cnx = ConnectionDB.getCnx();

    /**
     * UI attributes
     */
    @FXML
    private TableView<Offer> offerTable;
    @FXML
    private TableColumn<Offer, String> offerNameColumn;
    @FXML
    private TableColumn<Offer, String> offerRateStringColumn;
    @FXML
    private TableColumn<Offer, String> offerDescriptionColumn;
    @FXML
    private TableColumn<Offer, Date> offerStartDateColumn;
    @FXML
    private TableColumn<Offer, Date> offerEndDateColumn;

    private ObservableList<Offer> offerData = FXCollections.observableArrayList();

    @FXML
    private TextField addOfferNameField;
    @FXML
    private TextField addOfferRateField;
    @FXML
    private TextArea addOfferDescriptionArea;
    @FXML
    private DatePicker addOfferStartDatePicker;
    @FXML
    private DatePicker addOfferEndDatePicker;

    @FXML
    private TextField updateOfferNameField;
    @FXML
    private TextField updateOfferRateField;
    @FXML
    private TextArea updateOfferDescriptionArea;
    @FXML
    private DatePicker updateOfferStartDatePicker;
    @FXML
    private DatePicker updateOfferEndDatePicker;

    @FXML
    private TextField searchOfferField;
    @FXML
    private Label searchOfferLabel;

    @FXML
    private ImageView offerHelpImage;
    private Tooltip helpTooltip;

    public SellerOfferController(Shop sessionShop){
        this.sessionShop = sessionShop;
    }
    public SellerOfferController(){
        this.sessionShop = ShopController.sessionShop;
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        offerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        System.out.println(sessionShop);
        addOfferStartDatePicker.setValue(Optional.ofNullable(addOfferStartDatePicker.getValue()).orElse(LocalDate.now()));
        addOfferEndDatePicker.setValue(Optional.ofNullable(addOfferEndDatePicker.getValue()).orElse(LocalDate.now()));

        List<Offer> offerList = new ArrayList<>();
        OfferService offerService = OfferService.getInstance();
        offerList = offerService.getAllOffersForOneShop(sessionShop);

        offerData.clear();
        if (offerList != null) {
            offerData.addAll(offerList);
            offerNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            offerRateStringColumn.setCellValueFactory(new PropertyValueFactory<>("rateAsString"));
            offerDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            offerStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
            offerEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
            offerTable.setItems(offerData);System.out.println(offerTable);
            searchOfferLabel.setText("Résultat : " + offerList.size() + " ligne(s).");
        } else {
            searchOfferLabel.setText("Aucun résultat.");
            offerTable.setPlaceholder(new Label("Il n'y a aucune offre dans la base de données. Veuillez en rajouter! "));
        }

        helpTooltip = new Tooltip("Vous êtes dans l'onglet de getion des offres.\n"
                + "Ici, vous pouvez visualiser la liste des offres des partenaires de Shoppy,\n "
                + "en rechercher par identifiant, nom et biensûr par identifiant de magasin.\n"
                + "Il est aussi possible d'en ajouter de nouvelles ou de modifier une existante.\n"
                + "Il est aussi posible d'en supprimer une ou plusieures.\n"
                + "Pour la sélection multiple, il vous suffit de maintenir la touche Ctrl de votre clavier\n"
                + "enfoncée lors du clic de sélection.");
        Tooltip.install(offerHelpImage, helpTooltip);
    }

    /**
     * Session shop operations
     */
    public void setSessionShop(Shop shop) {
        this.sessionShop = shop;
//        refreshTableData();
    }

    public Shop getSessionShop() {
        return this.sessionShop;
    }

    //********************* C **************************//
    @FXML
    public void addOfferAction() {
        OfferService offerService = OfferService.getInstance();
        InputCheck inputCheck = InputCheck.getInstance();
        String name = addOfferNameField.getText();
        String rate = addOfferRateField.getText();
        String description = addOfferDescriptionArea.getText();
        Date startDate = Date.valueOf(addOfferStartDatePicker.getValue());
        Date endDate = Date.valueOf(addOfferEndDatePicker.getValue());
        boolean result = false;

        if (inputCheck.testTextInput(name) && inputCheck.testDoubleInput(rate)
                && (inputCheck.testFutureDate(startDate, endDate))) {
            Double rateDouble = Double.parseDouble(rate);
            result = offerService.addOffer(new Offer(0, sessionShop.getId(), rateDouble, name, description, startDate, endDate));
        } else {
            System.out.println("WIP : Error dialog => Wrong input format !");
        }
        if (result) {
            offerData.clear();
            refreshTableData();
            System.out.println("Succès de l'ajout de l'offre !");
        } else {
            System.out.println("Echec de l'ajout de l'offre !");

        }

    }

    //********************* R **************************//
    public void refreshTableData() {
        List<Offer> offerList = new ArrayList<>();
        OfferService offerService = OfferService.getInstance();
        offerList = offerService.getAllOffersForOneShop(sessionShop);
        offerData.clear();
        if (offerList != null) {
            offerData.addAll(offerList);
            offerTable.setItems(offerData);
            searchOfferLabel.setText("Résultat : " + offerList.size() + " ligne(s).");
        } else {
            searchOfferLabel.setText("Aucun résultat.");
            offerTable.setPlaceholder(new Label("Il n'y a aucune offre dans la base de données. Veuillez en rajouter! "));
        }
    }
        
    //********************* U **************************//
    @FXML
    public void selectOneOfferAction(KeyEvent keyEvent) {
        Offer offer = (Offer) offerTable.getSelectionModel().getSelectedItem();
        if (offer != null) {
            fillUpdateForm(offer);
        }
    }

    @FXML
    public void clickOneShopAction() {
        Offer offer = (Offer) offerTable.getSelectionModel().getSelectedItem();
        if (offer != null) {
            fillUpdateForm(offer);
        }
    }

    public void fillUpdateForm(Offer offer) {
        ShopService shopService = ShopService.getInstance();

        updateOfferNameField.setText(offer.getNom());
        updateOfferRateField.setText(String.valueOf(offer.getTaux()));
        updateOfferDescriptionArea.setText(offer.getDescription());
        updateOfferStartDatePicker.setValue(LocalDate.parse(offer.getDate_debut().toString()));
        updateOfferEndDatePicker.setValue(LocalDate.parse(offer.getDate_fin().toString()));

    }

    @FXML
    public void updateOfferAction() {
        Offer selection = offerTable.getSelectionModel().getSelectedItem();
        InputCheck inputCheck = InputCheck.getInstance();
        if (selection != null) {
            Offer offer = new Offer();
            offer.setId(selection.getId());
            String newName = updateOfferNameField.getText();
            String newRate = updateOfferRateField.getText();
            String newDescription = updateOfferDescriptionArea.getText();
            Date newStartDate = Date.valueOf(updateOfferStartDatePicker.getValue());
            Date newEndDate = Date.valueOf(updateOfferEndDatePicker.getValue());

            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr(e) de vouloir modifier le magasin: " + selection.getNom() + " de la base de données ?", ButtonType.YES, ButtonType.NO);
            a.showAndWait();

            if (a.getResult() == ButtonType.YES) {
                if (inputCheck.testTextInput(newName) && inputCheck.testDoubleInput(newRate)
                        && (inputCheck.testFutureDate(newStartDate, newEndDate))) {
                    offer.setNom(newName);
                    offer.setTaux(Double.parseDouble(newRate));
                    offer.setDescription(newDescription);
                    offer.setDate_debut(newStartDate);
                    offer.setDate_fin(newEndDate);
                    offer.setId_magasin(sessionShop.getId());

                    OfferService offerService = OfferService.getInstance();
                    offerService.updateOffer(offer);
                    refreshTableData();
                    a.close();
                } else {
                    Alert inputAlert = new Alert(Alert.AlertType.ERROR, "Le format de données saisi est incorrect.", ButtonType.OK);
                }
            } else {
                a.close();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING, "Aucune séléction !", ButtonType.CLOSE);
            a.showAndWait();
        }
        refreshTableData();
    }

    //********************* D **************************//
    @FXML
    public void deleteOfferAction() {
        ObservableList<Offer> selectedItems = offerTable.getSelectionModel().getSelectedItems();
        System.out.println(selectedItems);
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr(e) de vouloir supprimer ces " + selectedItems.size() + " éléments de la base de données ?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            OfferService offerService = OfferService.getInstance();
            for (Offer offer : selectedItems) {
                offerService.deleteOffer(offer);
            }
            refreshTableData();
            a.close();
        } else {
            a.close();
        }
    }

    //************ SEARCH *********************//
    public void searchOfferAction() {
        List<Offer> resultList = new ArrayList<>();
        OfferService offerService = OfferService.getInstance();
        String input = searchOfferField.getText();
        if (input.length() > 0) {
            resultList = offerService.findOfferByNameOrDescription(input);
            offerData.clear();
            offerData.addAll(resultList);
            offerTable.setItems(offerData);
            searchOfferLabel.setText("Résultat : " + resultList.size() + " ligne(s).");
        } else {
            refreshTableData();
        }
    }

    public void typingSearchOfferAction() {
        List<Offer> resultList = new ArrayList<>();
        OfferService offerService = OfferService.getInstance();
        String input = searchOfferField.getText();
        if (input.length() > 0) {
            resultList = offerService.findOfferByNameOrDescription(input);
            offerData.clear();
            offerData.addAll(resultList);
            searchOfferLabel.setText("Résultat : " + resultList.size() + " ligne(s).");
            offerTable.setItems(offerData);
        } else {
            refreshTableData();
        }
    }

    public void exportToExcelAction() {
        ExcelExport exporter = new ExcelExport();
        String fileName = sessionShop.getNom()+" - Offer table - " + LocalDate.now().toString();
        exporter.export(offerTable, fileName);
    }
}
