package tn.shoppy.model.omar;

import javafx.beans.property.StringProperty;

/**
 * Model class for a shop (Magasin)
 * @author Haroun
 */
public class Shop {
//TODO: add the user class !
//    private User seller; 
    private int id;
    private String nom;
    private int taille_stock;
    private int matricule_fiscal;
    
    /**
     * Default constructor
     */
    public Shop()
    {
        this(1,"Default_name",0);
    }
    

    public Shop(int id, String nom, int matriculeFiscal)
    {
        this.id = id;
        this.nom = nom;
        this.matricule_fiscal = matriculeFiscal;
        this.taille_stock = 0;
    }
    
    public Shop(String nom, int matriculeFiscal)
    {
        this.id = 1;
        this.nom = nom;
        this.matricule_fiscal = matriculeFiscal;
        this.taille_stock = 0;
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
        final Shop other = (Shop) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Shop{" + "id=" + id + ", nom=" + nom + ", taille_stock=" + taille_stock + ", matricule_fiscal=" + matricule_fiscal + '}';
    }
    
    /**
     * Attributes properties
     */
    
    //TODO convert attributes to properties 
    
    /**
     * Getters & setters
     */
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTaille_stock() {
        return taille_stock;
    }

    public void setTaille_stock(int taille_stock) {
        this.taille_stock = taille_stock;
    }

    public int getMatricule_fiscal() {
        return matricule_fiscal;
    }

    public void setMatricule_fiscal(int matricule_fiscal) {
        this.matricule_fiscal = matricule_fiscal;
    }

    
    
    
    
}
