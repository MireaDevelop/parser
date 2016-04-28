package Sms;

import Exeptions.*;
import parsing.students.Student;

import java.util.ArrayList;

/**
 * Created by Вадим on 20.04.2016.
 */
public class SendSms extends Thread{

    private String service;
    private ArrayList<Student> list;
    private String login;
    private String password;
    private String message;
    private String from;

    SendSms(String service){
        this.service = service;
        this.list = list;
        this.login = login;
        this.password = password;
        this.message = message;
        this.from = from;
    }

    @Override
    public void run() {
       switch (service){
           case "SmsRu": {
               SmsRu smsRu = new SmsRu(list,login,password,message);
            //   smsRu.CreateGroup();
           }
       }
    }
}
