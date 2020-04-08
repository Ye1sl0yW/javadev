/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author anas fattoum
 */
public class Product {
    private int id;
    private int id_magasin;
    private String nom;
    private int quantite;
    private String description;
    private double prix;
    private String marque;
    //private String image_name;
    private Date updated_at;
    private List<Integer> categoriesID;
    private String categoriesString;

    public Product() {
        this(0,0,"default product",0,"product description here",0,"brand");
    }

    public Product(int id,int id_magasin, String nom, int quantite, String description, double prix, String marque) {
        this.id = id;
        this.id_magasin = id_magasin;
        this.nom = nom;
        this.quantite = quantite;
        this.description = description;
        this.prix = prix;
        this.marque = marque;
        updated_at = new java.sql.Date(System.currentTimeMillis());
        this.categoriesID = new ArrayList<>();
        categoriesString = "Pas de catégorie.";
    }


    /**
     * Methods 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", id_magasin=" + id_magasin + ", nom=" + nom + 
                ", quantite=" + quantite + ", description=" + description + 
                ", prix=" + prix + ", marque=" + marque + "ID catégories" + categoriesID + categoriesString +'}';
    }
    
    
    /**
     * Getters & setters
     */

    public int getId() {
        return id;
    }

    public List<Integer> getCategoriesID() {
        return categoriesID;
    }

    public void setCategoriesID(ArrayList<Integer> categoriesID) {
        this.categoriesID = categoriesID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_magasin() {
        return id_magasin;
    }

    public void setId_magasin(int id_magasin) {
        this.id_magasin = id_magasin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getCategoriesString() {
        return categoriesString;
    }

    public void setCategoriesString(String categoriesString) {
        this.categoriesString = categoriesString;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
