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
    private boolean isSendVk = false;
    private boolean isSendMail = false;
    private boolean isSendPhone = false;
    private String message;
    private String themeText;

    public Controller(ArrayList<Student> list) {
        this.list = list;
    }

    public Controller() {

    }

    public void send() throws UnsupportedEncodingException, MessagingException {
        generateMessage(list);
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

    private void generateMessage(ArrayList<Student> list) {
        //Генерация именовонаго сообщения
        for(Student student : list) {
            student.setMessageTest("");
        }
    }

    public void setSendVk() {
        isSendVk = true;
    }

    public void setSendMail() {
        isSendMail = true;
    }

    public void setSendPhone() {
        isSendPhone = true;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setThemeText(String themeText) {
        this.themeText = themeText;
    }

    public void setList(ArrayList<Student> list) {
        this.list = list;
    }
}
