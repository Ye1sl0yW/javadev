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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import static java.awt.Color.gray;
import static java.awt.Color.red;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import java.util.*;  
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;
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
    private TableColumn<Order, LocalDateTime> IdAch;
    @FXML
    private TableColumn<Order, String> AdresseLivColumn;
    @FXML
    private Label searchOrderLabel;
    @FXML
    private TextField searchOrderField;
    
    @FXML
    private ImageView shopHelpImage;
    @FXML
    private Tooltip helpTooltip;
    @FXML
    private ObservableList<Order> orderData = FXCollections.observableArrayList();
    
    @FXML
    private Button searchOrderButton;
    @FXML
    private Button CreatePdfButton;
    @FXML
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
    @FXML
    private Button delete;
   


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        List<Order> orderList = new ArrayList<>();
        OrderService orderService = OrderService.getInstance();
        orderList = orderService.getAllOrders();
        orderData.clear();
        orderData.addAll(orderList);
         orderTable.setItems(orderData);

int a=100;
 String c="opm";
    
    
        if (!orderList.isEmpty()) {
           
            idColumn.setCellValueFactory(new PropertyValueFactory<>("idCmd"));
            prixTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
            qteTotalColumn.setCellValueFactory(new PropertyValueFactory<>("QteTot"));
            AdresseLivColumn.setCellValueFactory(new PropertyValueFactory<>("adresseLiv"));
             IdAch.setCellValueFactory(new PropertyValueFactory<>("DateCreation"));
     
        
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
            System.out.println("Echec de l'ajout du commande !");
            
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
   
    public void fillUpdateForm(Order order)
    { 
        String c =";";
          int a =order.getId_Acheteur();   
           try { 
                Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoppy?useUnicode=true" +
        "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&" +
        "serverTimezone=UTC", "root", "");
            Statement st = con.createStatement();
         
         
            ResultSet rs = st.executeQuery("SELECT nom,prenom,tel FROM `users` WHERE  id = " + a);
       
            int b=1; while (rs.next()) {
              //  table.addCell(rs.getString("id"));
             
              c =rs.getString("nom"); 
              c+=" ";
       c +=rs.getString("prenom");  
        c+=" ";
          c +=rs.getString("tel"); 
            } }
            catch (Exception e) {
                    System.out.println("tn.shoppy.controller.OrderController.initialize()");
                    }
//Build formatter
 DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
 
//Format LocalDateTime
String formattedDateTime = order.getDateCreation().format(formatter);
 
//Verify

      updateCommandeQuantity.setText(String.valueOf(order.getQteTot()));
      updateCommandeTotal.setText(String.valueOf(order.getTotal()));   
      updateCommandeAdress.setText(order.getAdresseLiv());   
      
      OrderCreatedDate.setText(c);  
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
                }else
        {
               JOptionPane.showMessageDialog(null, "verifier votre valeurs des champs");
           
            System.out.println(" Wrong input format !");
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
        String client1="no one";
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
            p.add("Facture num "+Selecteditem.getIdCmd());
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
         String ad=   Selecteditem.getAdresseLiv();
        int  a=Selecteditem.getIdCmd();
         int x=Selecteditem.getId_Acheteur();
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

              /*  newi tahrko el pc f aklek
                   table.addCell(rs.getString("id_product"));
                   table.addCell(rs.getString("qte"));
                   table.addCell(rs.getString("totalLigne"));
            */}
                Statement stt1 = con.createStatement();
             ResultSet rxx = stt1.executeQuery("SELECT nom,prenom,tel FROM `users` WHERE  id = " + x);
               if(rxx.next()){
           // les  3 cmds loulin teskhahomch
        
           String client= rxx.getString(1)+" "+rxx.getString(2)+" \n \n  Tel :"+rxx.getInt(3)+" \n \n Adresse Livraison: "+ad;
            client1=client;
            }}
            document.add(table);
      
           com.itextpdf.text.Paragraph t = new com.itextpdf.text.Paragraph();
            Font ff = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(red)); 
            t.add(new Chunk("total ttc : "+total+"  TND ",ff));
            t.setSpacingBefore(20);
            t.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(t);
            int aa=1;
               com.itextpdf.text.Paragraph t1 = new com.itextpdf.text.Paragraph();
            Font ff1 = new Font(FontFamily.TIMES_ROMAN, 15, Font.BOLD, new BaseColor(gray)); 
            t1.add(new Chunk("Client : "+client1,ff1));
            t1.setSpacingBefore(40);
            t1.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(t1);
            
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


@FXML
    public void SendMailAction() {
         ObservableList<Order> selectedItems = orderTable.getSelectionModel().getSelectedItems();
           Order Selecteditem= orderTable.getSelectionModel().getSelectedItem();
                  if (!Selecteditem.getClass().equals(selectedItems.getClass())) {}
               try{
        
        String to="testmail1235omar@gmail.com";//change accordingly  
  final String user="testmail1235omar@gmail.com";//change accordingly  
  final String password="123456omar";//change accordingly  
  //1) get the session object     
 
  Properties properties = System.getProperties();  
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false"); 
  
  Session session = Session.getDefaultInstance(properties,  
   new javax.mail.Authenticator() {  
   protected PasswordAuthentication getPasswordAuthentication() {  
   return new PasswordAuthentication(user,password);  
   }  
  });  
     
  //2) compose message     
  try{  
    MimeMessage message = new MimeMessage(session);  
    message.setFrom(new InternetAddress(user));  
    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
    message.setSubject("Shoppy");  
      
    //3) create MimeBodyPart object and set your message text     
    BodyPart messageBodyPart1 = new MimeBodyPart();  
    messageBodyPart1.setText("Bonjour Cher Client(e)  \n ci-jointe ta facture suite au dernier achat  \n merci pour votre fidelité \n Cordialement");  
      
    //4) create new MimeBodyPart object and set DataHandler object to this object      
    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
  
    String filename1 = "D:\\order8.pdf";//change accordingly  
     String filename ="C:\\Users\\asus\\Desktop\\order"+Selecteditem.getIdCmd()+".pdf";
    DataSource source = new FileDataSource(filename);  
    messageBodyPart2.setDataHandler(new DataHandler(source));  
    messageBodyPart2.setFileName("order"+Selecteditem.getIdCmd()+".pdf");  
     
     
    //5) create Multipart object and add MimeBodyPart objects to this object      
    Multipart multipart = new MimeMultipart();  
    multipart.addBodyPart(messageBodyPart1);  
    multipart.addBodyPart(messageBodyPart2);  
  
    //6) set the multiplart object to the message object  
    message.setContent(multipart );  
     
    //7) send message  
    Transport.send(message);  
   
  JOptionPane.showMessageDialog(
                    null, " mail envoyé avec succés ");
   }catch (MessagingException ex) {ex.printStackTrace();}  
               }catch (Exception e) {

            System.out.println("Error PDF");
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void toViewGestEquip(ActionEvent event) {
    }

    @FXML
    private void toViewUserAdmin(ActionEvent event) {
    }

    @FXML
    private void toViewStock(ActionEvent event) {
    }

    @FXML
    private void toViewComptaAdmin(ActionEvent event) {
    }

    @FXML
    private void toViewAvis(ActionEvent event) {
    }

    @FXML
    private void toViewRec(ActionEvent event) {
    }

    @FXML
    private void toViewMark(ActionEvent event) {
    }

    @FXML
    private void logout(ActionEvent event) {
    }

    @FXML
    private void AjouterCarteFidelite(ActionEvent event) {
    }

    @FXML
    private void ComboChange(ActionEvent event) {
    }

    @FXML
    private void Affecter(ActionEvent event) {
    }

    @FXML
    private void selectModifier(ActionEvent event) {
    }

    @FXML
    private void selectAjouter(ActionEvent event) {
    }

    @FXML
    private void selectAffecter(ActionEvent event) {
    }

    @FXML
    private void Modifier(ActionEvent event) {
    }

    @FXML
    private void Acheter(ActionEvent event) {
    }

    @FXML
    private void use_Livraison(ActionEvent event) {
    }

    @FXML
    private void Use_Cf(ActionEvent event) {
    }

    @FXML
    private void goPanier(Event event) {
    }

    @FXML
    private void AfficherCommande(Event event) {
    }

    @FXML
    private void AddProduct(Event event) {
    }

    @FXML
    private void afficherLivraion(Event event) {
    }

}
