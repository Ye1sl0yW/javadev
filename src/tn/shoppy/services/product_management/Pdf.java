package tn.shoppy.services.product_management;

import tn.shoppy.model.Product;

import com.sun.glass.ui.Size;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Cell;
import javafx.scene.control.TableView;

/**
 *
 * @author ASUS
 */
public class Pdf {

    public static void main(String[] args) {
        Pdf.PdfListeProduits();
    }

    public static void PdfListeProduits() {
        Document doc = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\Public\\Downloads\\products.pdf"));
        } catch (Exception ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        Paragraph title = new Paragraph("Products List Report", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.RED));
        Paragraph para = new Paragraph("---------------------------------------------------------------------------------------------------------------------");
        Paragraph date = new Paragraph(new Date().toString());
        doc.open();
        doc.addTitle("Liste de produits");
        try {
            doc.add(title);
                        doc.add(date);
            doc.add(para);
        } catch (DocumentException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }

        doc.addHeader("Liste produit", "Liste de produits");
        try {

            ProductService gp = new ProductService();
            ResultSet rs = gp.pdf();
            float[] columnWidths = {4f, 4f,4f, 4f,4f,4f,4f,4f,4f};
            PdfPTable table = new PdfPTable(columnWidths);
            
            table.setWidthPercentage(110f);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cell = new PdfPCell(new Paragraph("Product List"));
            cell.setColspan(14);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.GRAY);
            table.addCell(cell);
            table.addCell("Id produit");
            table.addCell("id_magasin");
            table.addCell("quantité stock");
            table.addCell("nom");
            table.addCell("description");
            table.addCell("prix");
            table.addCell("marque");
            table.addCell("Image");
            table.addCell("date");
            while (rs.next()) {
                table.addCell(String.valueOf(rs.getInt(1)));
                table.addCell(String.valueOf(rs.getInt(2)));
                table.addCell(String.valueOf(rs.getInt(3)));
                table.addCell(rs.getString(4));
                table.addCell(rs.getString(5));
                table.addCell(String.valueOf(rs.getDouble(6)));
                table.addCell(rs.getString(7));
                table.addCell(rs.getString(8));
                table.addCell(String.valueOf(rs.getDate(9)));
            }
            doc.add(table);
        } catch (Exception ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        doc.close();
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("C:/Users/Public/Downloads/products.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }

    }

}
