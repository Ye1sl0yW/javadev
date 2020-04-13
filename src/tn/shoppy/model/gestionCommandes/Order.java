/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.model.gestionCommandes;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Objects;
import javax.print.attribute.standard.DateTimeAtCreation;


/**
 *
 * @author asus
 */
public class Order {
    private int idCmd ; 
    private int QteTot;
    private float total ; 
private String  adresseLiv;
    private int id_Acheteur ; 
    private LocalDateTime DateCreation=now();
    private List<OrderLine> orderLine = new ArrayList<OrderLine> ();

    @Override
    public String toString() {
        return "Order{" + "idCmd=" + idCmd + ", QteTot=" + QteTot + ", total=" + total + ", adresseLiv=" + adresseLiv + ", id_Acheteur=" + id_Acheteur + ", DateCreation=" + DateCreation + ", orderLine=" + orderLine + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.idCmd;
        hash = 37 * hash + this.QteTot;
        hash = 37 * hash + Float.floatToIntBits(this.total);
        hash = 37 * hash + Objects.hashCode(this.adresseLiv);
        hash = 37 * hash + this.id_Acheteur;
        hash = 37 * hash + Objects.hashCode(this.DateCreation);
        hash = 37 * hash + Objects.hashCode(this.orderLine);
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
        final Order other = (Order) obj;
        if (this.idCmd != other.idCmd) {
            return false;
        }
        if (this.QteTot != other.QteTot) {
            return false;
        }
        if (Float.floatToIntBits(this.total) != Float.floatToIntBits(other.total)) {
            return false;
        }
        if (this.id_Acheteur != other.id_Acheteur) {
            return false;
        }
        if (!Objects.equals(this.adresseLiv, other.adresseLiv)) {
            return false;
        }
        if (!Objects.equals(this.DateCreation, other.DateCreation)) {
            return false;
        }
        if (!Objects.equals(this.orderLine, other.orderLine)) {
            return false;
        }
        return true;
    }

    
    public Order(int idCmd, int QteTot, float total, String adresseLiv, int id_Acheteur, LocalDateTime DateCreation) {
        this.idCmd = idCmd;
        this.QteTot = QteTot;
        this.total = total;
        this.adresseLiv = adresseLiv;
        this.id_Acheteur = id_Acheteur;
        this.DateCreation = DateCreation;
    }
 public Order(int idCmd, int QteTot, float total, String adresseLiv, int id_Acheteur) {
        this.idCmd = idCmd;
        this.QteTot = QteTot;
        this.total = total;
        this.adresseLiv = adresseLiv;
        this.id_Acheteur = id_Acheteur;
    }
 
    public Order() {
       this.adresseLiv=new String();
    }
    public Order(int QteTot, float total) {
        this.QteTot = QteTot;
        this.total = total;
    }

    public Order(int QteTot, float total, String adresseLiv) {
        this.QteTot = QteTot;
        this.total = total;
        this.adresseLiv = adresseLiv;
    }

    public Order(int idCmd, int QteTot, float total, String adresseLiv) {
        this.idCmd = idCmd;
        this.QteTot = QteTot;
        this.total = total;
        this.adresseLiv = adresseLiv;
    }

    public Order( int QteTot, float total, String adresseLiv,LocalDateTime DateCreation) {
      this.QteTot = QteTot;
        this.total = total;
        this.adresseLiv = adresseLiv;
         this.DateCreation   = now(); 
    }

    public int getIdCmd() {
        return idCmd;
    }

    public void setIdCmd(int idCmd) {
        this.idCmd = idCmd;
    }
       public int getId_Acheteur() {
        return id_Acheteur;
        
    }

    public void setId_Acheteur(int id_Acheteur) {
        this.id_Acheteur = id_Acheteur;
    }

    public LocalDateTime getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(LocalDateTime DateCreation) {
        this.DateCreation = DateCreation;
    }


    public int getQteTot() {
        return QteTot;
    }

    public void setQteTot(int QteTot) {
        this.QteTot = QteTot;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getAdresseLiv() {
        return adresseLiv;
    }

    public void setAdresseLiv(String adresseLiv) {
        this.adresseLiv = adresseLiv;
    }
}
