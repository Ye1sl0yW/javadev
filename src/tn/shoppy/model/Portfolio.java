/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.model;

/**
 *
 * @author os
 */
public class Portfolio {
    
    private int id;
    private int user_id;
    private int montant;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public int getMontant() {
        return montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public Portfolio( int user_id){
        this.user_id=user_id;
    }
    public Portfolio(int id, int user_id){
        this.id=id;
        this.user_id=user_id;
    }
    public Portfolio(int id, int user_id,int montant){
        this.id=id;
        this.user_id=user_id;
        this.montant=montant;
    }
    
    @Override
    public String toString(){
        return "ID: " + id + "\nUID: "+user_id ;
    }
    /*
    @Override
    public boolean equals(Object o){
        return (o.getClass()==getClass() && ((Portfolio)o).getId()==getId());
    }
*/
}
