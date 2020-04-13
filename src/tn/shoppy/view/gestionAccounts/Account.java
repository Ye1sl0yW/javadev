package tn.shoppy.view.gestionAccounts;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Account extends RecursiveTreeObject<Account> {


    StringProperty name;
    StringProperty job;
    StringProperty age;
    StringProperty gender;
    StringProperty username;

    public Account(int id, String username, String role, String email, String gender) {
        this.username = new SimpleStringProperty(username);
        this.job = new SimpleStringProperty(role);
        this.email = new SimpleStringProperty(email);
        this.id=id;
        this.gender= new SimpleStringProperty(gender);
    }

    StringProperty email;



    StringProperty password;
    int id;

    public Account(StringProperty name, StringProperty job, StringProperty age, StringProperty gender, StringProperty username, StringProperty email, StringProperty password, int id) {
        this.name = name;
        this.job = job;
        this.age = age;
        this.gender = gender;
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
    }
    public Account(String name , String job , String age , String gender){

        this.name=new SimpleStringProperty(name);
        this.job=new SimpleStringProperty(job);
        this.age=new SimpleStringProperty(age);
        this.gender=new SimpleStringProperty(gender);
    }

    public Account(String s, String checkRole, String email, String username, String male) {

    }


    public String getName() {
        return name.get();
    }


    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name =new SimpleStringProperty(name);
    }

    public String getJob() {
        return job.toString();
    }

    public StringProperty jobProperty() {
        return job;
    }

    public void setJob(String job) {
        this.job=new SimpleStringProperty(job);
    }

    public String getAge() {
        return age.get();
    }

    public StringProperty ageProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age=new SimpleStringProperty(age);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender=new SimpleStringProperty(gender);
    }

    public int getId() {
        return id; }
    public void setId(int id) {
    this.id = id; }
    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password=new SimpleStringProperty(password);
    }
    public String getUsername() {
        return username.toString();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username=new SimpleStringProperty(username);
    }


}