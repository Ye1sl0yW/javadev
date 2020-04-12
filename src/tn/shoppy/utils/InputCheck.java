/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author os
 */
public class InputCheck {
    
    public static boolean IsInt(String s){
        try{
            Integer.parseInt(s);
                }
        
        catch(NullPointerException | NumberFormatException e){
            return false;
        }
        return true;
    }
    
    public static boolean IsFloat(String s){
        try{
            Float.parseFloat(s);
                }
        
        catch(NullPointerException | NumberFormatException e){
            return false;
        }
        return true;
    }
    
    public static boolean IsDate(String s){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            dateFormat.parse(s.trim());
                }
        
        catch(ParseException e){
            return false;
        }
        return true;
    }
   /* public static boolean IsFutureDate(String s){
        if(IsDate(s) ){
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date d;
            try {
                d = format.parse(s);
            } catch (ParseException ex) {
                        return false;            }
            Date now=new Date();
            if (now.compareTo(d)<=0) return true;
        }
        return false;
    }
    */
    
    public static boolean IsFutureDate(Date d){
               
            Date now=new Date();
        return now.compareTo(d)<=0;
    }
}
