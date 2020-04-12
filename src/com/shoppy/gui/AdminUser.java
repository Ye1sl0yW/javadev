package com.shoppy.gui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.shoppy.entities.FosUser;
import com.shoppy.services.FosUserServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

public class AdminUser implements Initializable{


    @FXML
    private TreeTableColumn<Account, String> ageCol;

    @FXML
    private JFXTextField nameTF;

    @FXML
    private Label genderLB;

    @FXML
    private JFXTextField jobTF;

    @FXML
    private TreeTableColumn<Account, String> jobCol;

    @FXML
    private TreeTableColumn<Account, String> genderCol;

    @FXML
    private TreeTableColumn<Account, String> emailCol;

    @FXML
    private JFXTextField searchTF;

    @FXML
    private JFXTreeTableView<Account> treeTableView;

    @FXML
    private JFXComboBox<String> genderCombo;

    @FXML
    private Label ageLB;

    @FXML
    private JFXTextField ageTF;

    @FXML
    private TreeTableColumn<Account, String> nameCol;

    @FXML
    private Label jobLB;

    @FXML
    private Label nameLB;

    ObservableList<Account> list;


    ObservableList<FosUser> accountList;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        genderCombo.getItems().addAll("Male","Female");

        nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Account, String> param) {
                return param.getValue().getValue().name;
            }
        });

        jobCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Account, String> param) {
                return param.getValue().getValue().job;
            }
        });


        ageCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Account, String> param) {
                return param.getValue().getValue().age;
            }

        });


        genderCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Account, String> param) {
                return param.getValue().getValue().gender;
            }
        });

        emailCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Account, String> param) {
                return param.getValue().getValue().email;
            }
        });


        list= FXCollections.observableArrayList();

        TreeItem<Account> root=new RecursiveTreeItem<Account>(list, RecursiveTreeObject ::getChildren);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);

        list.addAll(new Account("admin","admin","hamdi.gtari@esprit.tn","Male"));
        list.addAll(new Account("client","client","hamdigtari@yahoo.com","Female"));
        list.addAll(new Account("client2","client","hamdi.gtari@yahoo.com","Homme"));
        list.addAll(new Account("client3","client","shoppy@yopmail.com","Homme"));
        list.addAll(new Account("client4","client","shoppy2@yopmail.com","Homme"));


        FosUserServices fs = new FosUserServices();

 /*      AccountService productService = AccountService.getInstance();
        accountList = fs.findAll();

        if (accountList != null) {

            for (FosUser acc : accountList) {

                list.addAll(new Account(acc.getId(),acc.getUsername(),fs.CheckRole(acc),acc.getEmail(),acc.getGender()));
            }


        }
*/
        searchTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeTableView.setPredicate(new Predicate<TreeItem<Account>>() {
                    @Override
                    public boolean test(TreeItem<Account> modelTreeItem) {
                        return modelTreeItem.getValue().name.getValue().contains(newValue) |modelTreeItem.getValue().age.getValue().contains(newValue) ;
                    }
                });
            }
        });


        treeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showDetails(newValue);
        });

    }


    @FXML
    void  addAction(ActionEvent event){

        list.addAll(new Account(nameTF.getText(),jobTF.getText(),ageTF.getText(),genderCombo.getSelectionModel().getSelectedItem()));

    }


    @FXML
    void  deleteAction(ActionEvent event){
        int index=treeTableView.getSelectionModel().getSelectedIndex();
        list.remove(index);
        clearFields();
    }

    public  void  showDetails(TreeItem<Account> treeItem){

        nameTF.setText(treeItem.getValue().getName());
        nameLB.setText(treeItem.getValue().getName());

        jobTF.setText(treeItem.getValue().getJob());
        jobLB.setText(treeItem.getValue().getJob());

        ageTF.setText(treeItem.getValue().getAge());
        ageLB.setText(treeItem.getValue().getAge());

        genderCombo.getSelectionModel().select(treeItem.getValue().getGender());
        genderLB.setText(treeItem.getValue().getGender());

    }


    public void clearFields(){
        nameTF.setText("");
        nameLB.setText("");
        jobTF.setText("");
        jobLB.setText("");
        ageTF.setText("");
        ageLB.setText("");
        genderLB.setText("");
        genderCombo.getSelectionModel().select("");
    }

    @FXML
    void clearAction(ActionEvent event){
        clearFields();
    }

    @FXML
    void editAction(ActionEvent event){

        TreeItem <Account> treeItem=treeTableView.getSelectionModel().getSelectedItem();

        Account m=new Account(nameTF.getText(),jobTF.getText(),ageTF.getText(),genderCombo.getSelectionModel().getSelectedItem());

        treeItem.setValue(m);

    }

}
