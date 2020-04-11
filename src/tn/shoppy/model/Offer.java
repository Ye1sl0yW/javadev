/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.model;

import java.sql.Date;
import tn.shoppy.services.OfferService;


/**
 *
 * @author Haroun
 */
public class Offer {
    /**
     * Attributes
     */
    private int id;
    private int id_magasin;
    private String shopName;
    private double taux;
    private String rateAsString;
    private String nom;
    private String description;
    private Date date_debut;
    private Date date_fin;
    
    /**
     * Constructors
     */
    public Offer(int id, int shopID, double rate, String name, String description, Date startDate, Date endDate )
    {
        this.id = id;
        id_magasin = shopID;
        shopName = OfferService.getInstance().getShopName(shopID);
        taux = rate;
        rateAsString = String.valueOf(rate*100)+ "%";
        nom = name;
        this.description = description;
        date_debut = startDate;
        date_fin = endDate;
    }
    
    public Offer()
    {
        this(0,0,0.0,"default_name","empty_offer",new java.sql.Date(System.currentTimeMillis()),new java.sql.Date(System.currentTimeMillis()));
    }

    /**
     * Overrides 
     */

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
        final Offer other = (Offer) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Offer{" +  " taux=" + taux + ", nom=" + nom + ", description=" + description + ", date_debut=" + date_debut + ", date_fin=" + date_fin + '}';
    }
    
            
    /**
     * Getters & Setters
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_magasin() {
        return id_magasin;
    }

    public void setId_magasin(int id_magasin) {
        this.id_magasin = id_magasin;
        this.setShopName(OfferService.getInstance().getShopName(this));
    }

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
        this.rateAsString = String.valueOf(100*taux)+" %";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRateAsString() {
        return rateAsString;
    }

    public void setRateAsString(String rateAsString) {
        this.rateAsString = rateAsString;
    }

    
    
}
