package controller;

import email.SendMails;
import parsing.students.Student;
import phone.Phone;
import vk.Vk;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * Created by aleksejpluhin on 12.04.16.
 */
public class Controller {
    private ArrayList<Student> list;
    private boolean isSendVk;
    private boolean isSendMail;
    private boolean isSendPhone;


    public Controller(ArrayList<Student> list, boolean isSendVk, boolean isSendMail, boolean isSendPhone) {
        this.list = list;
        this.isSendVk = isSendVk;
        this.isSendMail = isSendMail;
        this.isSendPhone = isSendPhone;
    }

    public void send() throws UnsupportedEncodingException, MessagingException {
        if(isSendMail) {
            SendMails.Send(list);
        }
        if(isSendVk) {
            Vk.sendVk(list);
        }
        if(isSendPhone) {
            Phone.sendPhone(list);
        }
    }


}
