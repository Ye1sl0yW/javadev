package tn.shoppy.services.shop_management;

import javafx.scene.control.TableView;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Haroun
 */
public class ExcelExport<T> {
public void export(TableView<T> tableView,String fileName){

        HSSFWorkbook hssfWorkbook=new HSSFWorkbook();
        HSSFSheet hssfSheet=  hssfWorkbook.createSheet(fileName); //Ajouter parametre pour le nom + date pour le fichier dans chaquebouton
        HSSFRow firstRow= hssfSheet.createRow(0);

        ///set titles of columns
        for (int i=0; i<tableView.getColumns().size();i++){

            firstRow.createCell(i).setCellValue(tableView.getColumns().get(i).getText());

        }


        for (int row=0; row<tableView.getItems().size();row++){

            HSSFRow hssfRow= hssfSheet.createRow(row+1);

            for (int col=0; col<tableView.getColumns().size(); col++){

                Object celValue = tableView.getColumns().get(col).getCellObservableValue(row).getValue();

                try {
                    if (celValue != null && Double.parseDouble(celValue.toString()) != 0.0) {
                        hssfRow.createCell(col).setCellValue(Double.parseDouble(celValue.toString()));
                    }
                } catch (  NumberFormatException e ){

                    hssfRow.createCell(col).setCellValue(celValue.toString());
                }

            }

        }

        //save excel file and close the workbook
        try {
            hssfWorkbook.write(new FileOutputStream(fileName+".xls"));
            hssfWorkbook.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
