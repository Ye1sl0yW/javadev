/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.core;


import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;
import utils.Interaction_SMS;
/**
 *
 * @author os
 */
public class testAPI {
    public static void main(String args[]) {
        
 Interaction_SMS.sendSMS("21653133667", "Présentation API SMS");
System.out.println("OK");



    }
}
