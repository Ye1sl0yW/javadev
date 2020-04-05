/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import tn.shoppy.model.Order;
import tn.shoppy.model.Shop;
import tn.shoppy.services.OrderService;
import tn.shoppy.services.ShopService;
import tn.shoppy.utils.ConnectionDB;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import static java.awt.Color.red;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javax.xml.parsers.DocumentBuilder;
import static jdk.nashorn.internal.objects.NativeMath.random;
import tn.shoppy.model.OrderStat;
import tn.shoppy.utils.InputCheck;

/**
 *
 * @author asus
 */
public class OrderController implements Initializable {

    Connection cnx = ConnectionDB.getCnx();

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Float> prixTotalColumn;
    @FXML
    private TableColumn<Order, Integer> qteTotalColumn;
    @FXML
    private TableColumn<Order, Integer> idColumn;
    @FXML
     private TableColumn<Order, Integer> IdAch;
     @FXML
     private TableColumn<Order, LocalDateTime> date;
    @FXML
    private TableColumn<Order, String> AdresseLivColumn;
    @FXML
    private Label searchOrderLabel;
    @FXML
    private TextField searchOrderField;

    @FXML
    private ImageView shopHelpImage;
    
    private Tooltip helpTooltip;
    
    private ObservableList<Order> orderData = FXCollections.observableArrayList();
    
    @FXML
    private Button searchOrderButton;
    @FXML
    private Button CreatePdfButton;
    private PieChart pie;
    @FXML
    private TextField addOrderQuantityField;
    @FXML
    private TextField addOrderTotalField;
    @FXML
    private Button addOrderButton;
    @FXML
    private TextField addOrderAdressField;
    @FXML
    private TextField updateCommandeAdress;
    @FXML
    private TextField updateCommandeQuantity;
    @FXML
    private TextField updateCommandeTotal;
    @FXML
    private Button updateOrderButton;
    @FXML
    private Button Archivage;
    @FXML
    private TextField OrderCreatedDate;
    
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        List<Order> orderList = new ArrayList<>();
        OrderService orderService = OrderService.getInstance();
        orderList = orderService.getAllOrders();
        orderData.clear();
        orderData.addAll(orderList);
         orderTable.setItems(orderData);

 
    
        if (!orderList.isEmpty()) {
           
            idColumn.setCellValueFactory(new PropertyValueFactory<>("idCmd"));
            prixTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
            qteTotalColumn.setCellValueFactory(new PropertyValueFactory<>("QteTot"));
            AdresseLivColumn.setCellValueFactory(new PropertyValueFactory<>("adresseLiv"));
            IdAch.setCellValueFactory(new PropertyValueFactory<>("id_Acheteur"));
           
            searchOrderLabel.setText("Résultat : " + orderList.size() + " ligne(s).");
        } else {
            searchOrderLabel.setText("");
            orderTable.setPlaceholder(new Label("Il n'y a aucun commnade dans la base de données. Veuillez en rajouter! "));
        }

        helpTooltip = new Tooltip("Vous êtes dans l'onglet de gestion de commande.\n"
                + "Ici, vous pouvez visualiser la liste des commandes shoppy,\n "
                + "en rechercher par id,quantite et biensûr par total.\n"
                + "Il est aussi possible d'en ajouter de nouveaux ou de modifier une commande existant.\n"
                + "Il est aussi posible de supprimer un ou plusieurs commande.\n"
                + "Pour la sélection multiple, il vous suffit de maintenir la touche Ctrl de votre clavier\n"
                + "enfoncée lors du clic de sélection.");
//        helpToltip.show();
        Tooltip.install(shopHelpImage, helpTooltip);
         

    }
 //*ajouter commande//
    @FXML
    public void addOrderAction() {
       OrderService orderService = OrderService.getInstance();
        InputCheck inputCheck = InputCheck.getInstance();
        String quantite = addOrderQuantityField.getText();
        String total = addOrderTotalField.getText();
        String adresse =addOrderAdressField.getText() ;
        
        boolean result = false;
        
        if (inputCheck.testTextInput(adresse) && inputCheck.testNumberInput(quantite)&& inputCheck.testFloatInput(total))
        {
            int intQuantite = Integer.parseInt(quantite);
               float Inttotal = Float.parseFloat(total);
        /* use  add method */  result=  orderService.addOrder(new Order(intQuantite,Inttotal,adresse));
        result=true;
        }
        else
        {
               JOptionPane.showMessageDialog(null, "verifier votre valeurs des champs");
           
            System.out.println(" Wrong input format !");
        }
        if (result)
        {
             JOptionPane.showMessageDialog(null, " Commande Ajoutée avec succés ");
            refreshTableData();
            
            System.out.println("Succès de l'ajout du cmd !");
        }
        else
        {   JOptionPane.showMessageDialog(null, "Echec de l'ajout du commande");
            System.out.println("Echec de l'ajout du magasin !");
            
        }

    }
    /**
     * ********refresh********
     */
    public void refreshTableData() {
        List<Order> orderList = new ArrayList<>();
        OrderService orderService = OrderService.getInstance();
        orderList = orderService.getAllOrders();
                   

        orderData.clear();
        orderData.addAll(orderList);
        orderTable.setItems(orderData);
        searchOrderLabel.setText("Résultat : " + orderList.size() + " ligne(s).");
    }

    //**************D**************//.
    @FXML
    public void deleteOrderAction() {
        ObservableList<Order> selectedItems = orderTable.getSelectionModel().getSelectedItems();
        System.out.println(selectedItems);
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr(e) de vouloir supprimer ces " + selectedItems.size() + " éléments de la base de données ?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            OrderService orderService = OrderService.getInstance();
            for (Order order : selectedItems) {
                orderService.deleteOrder(order);
            }
            refreshTableData();
            a.close();
        } else {
            a.close();
        }
    }

    //************ SEARCH *********************//
    @FXML
    public void searchOrderAction() {
        List<Order> resultList = new ArrayList<>();
        OrderService orderService = OrderService.getInstance();
        String input = searchOrderField.getText();
        if (input.length() > 0) {
            resultList = orderService.findOrders(input);
            orderData.clear();
            orderData.addAll(resultList);
            orderTable.setItems(orderData);
            searchOrderLabel.setText("Résultat : " + resultList.size() + " ligne(s).");
        } else {
            refreshTableData();
        }
    }

    @FXML
    public void typingSearchOrderAction(KeyEvent event) {
        List<Order> resultList = new ArrayList<>();
        OrderService orderService = OrderService.getInstance();
        String input = searchOrderField.getText();
        if (input.length() > 0) {
            resultList = orderService.findOrders(input);
            orderData.clear();
            orderData.addAll(resultList);
            orderTable.setItems(orderData);
            searchOrderLabel.setText("Résultat : " + resultList.size() + " ligne(s).");
        } else {
            refreshTableData();
        }
    }


     //********************* U **************************//
    @FXML
    public void selectOneOrderAction(KeyEvent keyEvent) {
        Order order = (Order) orderTable.getSelectionModel().getSelectedItem();
        if(order != null)
        {
            fillUpdateForm(order);
        }
    }
    @FXML
    public void clickOneOrderAction() {
        Order order = (Order) orderTable.getSelectionModel().getSelectedItem();
        if(order != null)
        {
            fillUpdateForm(order);
        }
    }
   
    @FXML
    public void fillUpdateForm(Order order)
    {
//Build formatter
 DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
 
//Format LocalDateTime
String formattedDateTime = order.getDateCreation().format(formatter);
 
//Verify

      updateCommandeQuantity.setText(String.valueOf(order.getQteTot()));
      updateCommandeTotal.setText(String.valueOf(order.getTotal()));   
      updateCommandeAdress.setText(order.getAdresseLiv());   
      
      OrderCreatedDate.setText(formattedDateTime);  
      System.out.println("Formatted LocalDateTime : " + formattedDateTime);
        
    }
    @FXML
    public void updateOrderAction() {
        
        Order selection = orderTable.getSelectionModel().getSelectedItem();
        InputCheck inputCheck = InputCheck.getInstance();
        if (selection != null)
        {  
            Order order = new Order();
            order.setIdCmd(selection.getIdCmd());
          
            String newAdr = updateCommandeAdress.getText();
            String newQt = updateCommandeQuantity.getText();
             String newTot = updateCommandeTotal.getText();   
          
            Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir modifier le magasin: "+order.getIdCmd()+" de la base de données ?",ButtonType.YES,ButtonType.NO);
            a.showAndWait();
            if(a.getResult()==ButtonType.YES){
                if(inputCheck.testNumberInput(newQt) && inputCheck.testTextInput(newAdr) && inputCheck.testFloatInput(newTot) )
                {
                    order.setAdresseLiv(newAdr);
                    order.setQteTot(Integer.parseInt(newQt));
                      order.setTotal(Float.parseFloat(newTot));
                    OrderService orderService = OrderService.getInstance();
                    orderService.updateOrder(order);
                    a.close();
                }
                else
                {
                    Alert inputAlert = new Alert(Alert.AlertType.ERROR,"Le format de données saisi est incorrect.",ButtonType.OK);
                }
            }else{
            a.close();
            }
        }
        else
        {   
            updateCommandeAdress.setText("");
            updateCommandeQuantity.setText("");  
            updateCommandeTotal.setText("");  
            Alert a=new Alert(Alert.AlertType.WARNING,"Aucune séléction !",ButtonType.CLOSE); 
            a.showAndWait();
        }
        refreshTableData();
    }
    /*
     @FXML
    public void addOrderAction() {
        ShopService shopService = ShopService.getInstance();
        InputCheck inputCheck = InputCheck.getInstance();
        String name = addMagasinNameField.getText();
        String taxID = addMagasinFiscalityField.getText();
        boolean result = false;
        
        if (inputCheck.testTextInput(name) && inputCheck.testNumberInput(taxID))
        {
            int intTaxID = Integer.parseInt(taxID);
            result = shopService.addShop(new Shop(name,intTaxID));
        }
        else
        {
            System.out.println("WIP : Error dialog => Wrong input format !");
        }
        if (result)
        {
            refreshTableData();
            System.out.println("Succès de l'ajout du magasin !");
        }
        else
        {
            System.out.println("Echec de l'ajout du magasin !");
            
        }

    }
    
    
    */
    
    
    @FXML
    private void CreatePdfAction(ActionEvent event) {
        float total=0 ; 
           float[] columnWidths =  {1.5f, 2f, 5f, 2f};
         Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
   Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);    
        ObservableList<Order> selectedItems = orderTable.getSelectionModel().getSelectedItems();
           Order Selecteditem= orderTable.getSelectionModel().getSelectedItem();
                  if (!Selecteditem.getClass().equals(selectedItems.getClass())) {}
                try {
            Document document = new Document();
double i=random(1000);
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\asus\\Desktop\\order"+Selecteditem.getIdCmd()+".pdf"));
            document.open();
            PdfPTable table = new PdfPTable(columnWidths);
            
//            table.setWidthPercentage(100);
            table.setTotalWidth(300f);
            table.getDefaultCell().setBorder(2);
           // table.addCell("id");
    insertCell(table, " No", com.itextpdf.text.Element.ALIGN_RIGHT, 1, bfBold12);
    insertCell(table, "quantite", com.itextpdf.text.Element.ALIGN_LEFT, 1, bfBold12);
    insertCell(table, "produit", com.itextpdf.text.Element.ALIGN_LEFT, 1, bfBold12);
    insertCell(table, "TotalLigne", com.itextpdf.text.Element.ALIGN_RIGHT, 1, bfBold12);
    
    table.setHeaderRows(1);
        /*    table.addCell("id_product");
            table.addCell("qte");
            table.addCell("totalLigne");
     */  

            com.itextpdf.text.Paragraph p = new com.itextpdf.text.Paragraph();
            p.add("Facture num"+Selecteditem.getIdCmd());
            p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(p);
        Image    img=Image.getInstance("D:\\Downloads\\javadev-master\\src\\tn\\shoppy\\resources\\images\\logo.png");
                    img.scaleAbsolute(28, 28);
            //document.add(com.itextpdf.text.Image.getInstance("D:\\Downloads\\javadev-master\\src\\tn\\shoppy\\resources\\images\\logo.png"));
            document.add(img);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoppy?useUnicode=true" +
        "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&" +
        "serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
            
        int  a=Selecteditem.getIdCmd();
        

            ResultSet rs = st.executeQuery("SELECT id,id_product,qte,totalLigne FROM `ligne_cmd` WHERE  id_cmd = " + a);
           
            int b=1; while (rs.next()) {
              //  table.addCell(rs.getString("id"));
             
              int c =rs.getInt("id_product");
                   float nv = rs.getFloat("totalLigne");
                    total=total+nv;
             Statement stt = con.createStatement();
              ResultSet rss = stt.executeQuery("SELECT nom, description FROM `produit` WHERE  id = " + c);
              if(rss.next()){
  String detail = rss.getString(1)+ "  ";
  String detail1 = rss.getString(2);
    String detail2 = detail+detail1;


                   insertCell(table, Integer.toString(b), com.itextpdf.text.Element.ALIGN_RIGHT, 1, bfBold12);
                   insertCell(table, rs.getString("qte"), com.itextpdf.text.Element.ALIGN_LEFT, 1, bfBold12);
                   insertCell(table,detail2, com.itextpdf.text.Element.ALIGN_LEFT, 1, bfBold12);
                   insertCell(table, rs.getString("totalLigne"), com.itextpdf.text.Element.ALIGN_RIGHT, 1, bfBold12);
                   b++; 

              /*   table.addCell(rs.getString("id_product"));
                   table.addCell(rs.getString("qte"));
                   table.addCell(rs.getString("totalLigne"));
            */}}
            document.add(table);
      
           com.itextpdf.text.Paragraph t = new com.itextpdf.text.Paragraph();
            Font ff = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(red)); 
            t.add(new Chunk("total ttc : "+total+"  TND ",ff));
            t.setSpacingBefore(20);
            t.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(t);
            
            JOptionPane.showMessageDialog(
                    null, " données exportées en pdf avec succés ");
            document.close();

        } catch (Exception e) {

            System.out.println("Error PDF");
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
            
     

    }
private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
   
  //create a new cell with the specified Text and Font
  PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
  //set the cell alignment
  cell.setHorizontalAlignment(align);
  //set the cell column span in case you want to merge two or more cells
  cell.setColspan(colspan);
  //in case there is no text and you wan to create an empty row
  if(text.trim().equalsIgnoreCase("")){
   cell.setMinimumHeight(10f);
  }
  //add the call to the table
  table.addCell(cell);
   
 }
    private void stat(MouseEvent event) {
        OrderStat c=new OrderStat();
        pie.setData(c.Stats());
    }
 @FXML
    public void ArchiverCmdAction() {
        Order selection = orderTable.getSelectionModel().getSelectedItem();
    
        if (selection != null)
        {    
            Order t = new Order();
            t.setIdCmd(selection.getIdCmd());
            t.setAdresseLiv(selection.getAdresseLiv());
            Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir archiver la commande: "+t.getIdCmd()+" de la base de données ?",ButtonType.YES,ButtonType.NO);
            a.showAndWait();
            if(a.getResult()==ButtonType.YES){
               
                   
                   
                    OrderService orderService =OrderService.getInstance();
                    orderService.archiveOrder(t);
                    a.close();
                    JOptionPane.showMessageDialog(
                    null, " archivage fait avec succés ");
           
                
             
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



}
