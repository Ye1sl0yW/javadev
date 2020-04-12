package tn.shoppy.model;

import javafx.beans.property.StringProperty;
import tn.shoppy.services.shop_management.ShopService;

/**
 * Model class for a shop (Magasin)
 * @author Haroun
 */
public class Shop {
    private int id;
    private Integer id_vendeur;
    private String sellerName;
    private String nom;
    private int taille_stock;
    private int matricule_fiscal;
    
    /**
     * Default constructor
     */
    public Shop()
    {
        this(1,null,"Default_name",0);
    }
    

    public Shop(int id, Integer id_vendeur,String nom, int matriculeFiscal)
    {
        this.id = id;
        this.id_vendeur=id_vendeur;
        this.sellerName="Pas de responsable";
        if (id_vendeur != null){
            this.sellerName=ShopService.getInstance().getShopSellerName(this);
        }
        this.nom = nom;
        this.matricule_fiscal = matriculeFiscal;
        this.taille_stock = 0;
    }
    
    public Shop(String nom, int matriculeFiscal)
    {
        this.id = 1;
        this.id_vendeur=null;
        this.sellerName="Pas de responsable";
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
        return nom +"  =>  Responsable: "+this.getId_vendeur()+ ", mattricule fiscal: " + matricule_fiscal;
    }

    
    /**
     * Getters & setters
     */
    
        
    public Integer getId_vendeur() {
        if (this.id_vendeur != null)
        {
            return id_vendeur;
        }
        else{
            return 0;
        }
    }
    
    public void setId_vendeur(int id_vendeur) {
        this.id_vendeur = id_vendeur;
    }

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

    public String getSellerName() {
        return ShopService.getInstance().getShopSellerName(this);
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
        
    }

    
    
    
    
}
