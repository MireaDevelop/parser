package org.Sms;

import org.exeptions.*;
import org.students.Student;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Вадим on 20.04.2016.
 */
public abstract class SmsServices {

   protected ArrayList<Student> list;
   protected String login;
   protected String password;
   protected String message;

   public SmsServices(ArrayList<Student> list, String login, String password, String message) {
      this.list = list;
      this.login = login;
      this.password = password;
      this.message = URLEncoder.encode(message);
   }


   protected abstract boolean Send(String to) throws ServiceNotWork, NonText, AuthFailed, NotConfirmLogin, TooBigSms;


   public void SendMsg() throws ServiceNotWork, AuthFailed, NotConfirmLogin, TooBigSms, NonText, NotEnoughMoney{

   }

   protected abstract void throwError(String error) throws ServiceNotWork, AuthFailed, NotConfirmLogin, NonText, TooBigSms;

   protected abstract boolean checkbill() throws ServiceNotWork, NotConfirmLogin, AuthFailed, TooBigSms, NonText;

   protected Object sendrequest(String urlToRead){
      URL url;
      HttpURLConnection connection;
      BufferedReader rd;
      String line;
      String result="";
      ArrayList<String> list = new ArrayList<>();
      try {
         url = new URL(urlToRead);
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("GET");

         rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         while ((line = rd.readLine()) != null) {
            result +=line;
         }
         rd.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return result;
   }



}
