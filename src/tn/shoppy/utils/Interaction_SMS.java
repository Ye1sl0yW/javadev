/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.messages.TextMessage;
/**
 *
 * @author os
 */


public class Interaction_SMS {
    private static final String APIKEY="f481785d";
    private static final String APISECRET="gFEEEF16fegkilpid00AZncaX";
    static private final NexmoClient client = NexmoClient.builder()
        .apiKey(APIKEY)
        .apiSecret(APISECRET)
        .build();
    
    
    public static boolean sendSMS(String number, String text){
        SmsSubmissionResponse responses = client.getSmsClient().submitMessage(new TextMessage(
        "SHOPPY",
        number,
        text));
        
        if (responses!=null) return true;
        return false;
    }
    public static boolean sendCODE(String number, String context,String code){
        SmsSubmissionResponse responses = client.getSmsClient().submitMessage(new TextMessage(
        "SHOPPY",
        number,
        context+ "Votre CODE est : " + code));
        
        if (responses!=null) return true;
        return false;
    }
    
}
