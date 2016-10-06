package org.Sms.Services;

import org.exeptions.*;
import org.students.Student;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Вадим on 20.04.2016.
 */
public abstract class SmsService {

   protected ArrayList<Student> list;

   protected String login;
   protected String password;
   protected String message;
   protected String from;

   protected boolean SureEnoughtMoney;

   public SmsService(ArrayList<Student> list, String login, String password, String message, String from, boolean SureEnoughtMoney) {
      this.list = list;
      this.login = login;
      this.password = password;
      this.from = from;
      this.SureEnoughtMoney = SureEnoughtMoney;

      try {
         this.message = URLEncoder.encode(message, "utf-8");
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }
   }


   protected abstract boolean Send(String to) throws ServiceNotWork, NonText, AuthFailed, NotConfirmLogin, TooBigSms, NotConfirmSenderName, NotEnoughMoney;


   public void SendMsg() throws ServiceNotWork, AuthFailed, NotConfirmLogin, TooBigSms, NonText, NotEnoughMoney, NotConfirmSenderName {

   }

   protected abstract void throwError(String error) throws ServiceNotWork, AuthFailed, NotConfirmLogin, NonText, TooBigSms, NotConfirmSenderName, NotEnoughMoney;

   protected abstract boolean checkbill() throws ServiceNotWork, NotConfirmLogin, AuthFailed, TooBigSms, NonText, NotConfirmSenderName, NotEnoughMoney;

   protected Object sendrequest(String urlToRead){
      URL url;
      HttpURLConnection connection;
      BufferedReader rd;
      String line;
      StringBuilder result= new StringBuilder("");

      try {
         url = new URL(urlToRead);
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("GET");

         rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         while ((line = rd.readLine()) != null) {
            result.append(line);
         }
         rd.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return result.toString();
   }


}
