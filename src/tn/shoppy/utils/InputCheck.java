/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.utils;

/**
 *
 * @author Haroun
 */
public class InputCheck {
/**
 * Consutructor and Singleton Design Pattern
 */
    
    private static InputCheck inputCheckInstance;
    
    public static InputCheck getInstance()
    {   //Singleton Design Pattern
        if (inputCheckInstance==null)
        {
            inputCheckInstance = new InputCheck();
        }
        return inputCheckInstance;  
    }
    
    
    
 
    public boolean testTextInput(String a) {
        try{
            if (a.length() == 0 || testNumberInput(a) || testFloatInput(a) || a.contains("@")) 
            {
                return false;
            }
        }
        catch(java.lang.NullPointerException e){
            System.out.println("Error : testTextInput");
            return false;
        }
        return true;
    }
   
    public boolean testNumberInput(String a) {
        return (a.matches("^[0-9]*") && a.length()>0);
    }
    
    public boolean testFloatInput (String a) {
        try
        {
            Float i = Float.parseFloat(a);
            return true;
        }
        catch ( NumberFormatException e )
        {
            return false;
        }
    }

    public boolean testDoubleInput(String newPrice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
