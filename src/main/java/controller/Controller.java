package controller;

import email.SendMails;
import parsing.students.Student;
import phone.Phone;
import vk.Vk;
import vk.VkImpl;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringJoiner;


/**
 * Created by aleksejpluhin on 12.04.16.
 */
public class Controller {
    private ArrayList<Student> list;
    private boolean isSendVk = false;
    private boolean isSendMail = false;
    private boolean isSendPhone = false;
    private Vk vk = new VkImpl();
    private SendMails mail = new SendMails();
    private Phone phone = new Phone();
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
            mail.sendMail(list);
        }
        if(isSendVk) {
            vk.sendVk(list);
        }
        if(isSendPhone) {
            phone.sendPhone(list);
        }
    }

    private void generateMessage(ArrayList<Student> list) {
        //Генерация именовонаго сообщения
        for(Student student : list) {
            student.setMessageText("");
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

    public void setVkId(String id) {
        vk.setId(id);
    }

    public void setVkAcessToken(String token) {
        vk.setAccessToken(token);
    }

    public void setMail(SendMails mail) {
      //Установка настроек
    }

    public void setPhone(Phone phone) {
        //Установка настроек
    }
}
