/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.utils.HA;

import java.sql.Date;

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

    public static boolean testNumberInput(String a) {
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
    
    public boolean testDoubleInput (String a) {
        try
        {
            Double i = Double.parseDouble(a);
            return true;
        }
        catch ( NumberFormatException e )
        {
            return false;
        }
    }    
    
    /**
     * Tests id 'date' is in the future.
     * @param date
     * @return true if the parameter is equal or superior to current date
     *         false otherwise.
     */
    public static boolean testFutureDate(Date date)
    {
        return (date.compareTo(new Date(System.currentTimeMillis())) >= 0 );
    }
    
    /**
     * Tests if date1 is after date2
     * @param date1
     * @param date2
     * @return true: if date1 is after or equal to date2
     *         false: if date1 is before date2. 
     */
    public boolean testFutureDate(Date date1, Date date2)
    {
        return (date1.compareTo(date2) <= 0 );
    }
    
}


