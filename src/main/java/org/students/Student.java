package org.students;

/**
 * Created by Вадим on 10.05.2016.
 *
 */
public class Student {

    private String name;
    private String mail;
    private String phone;
    private String vk_id;
    private String messageText;

    private boolean isSendPhone = false;
    private boolean isSendVk = false;
    private boolean isSendMail = false;




    public Student(String name, String mail, String phone, String vk_id) {
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.vk_id = vk_id;

    }


    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public String getVk_id() {
        return vk_id;
    }


    public void setMessageText(String messageTest) {
        this.messageText = messageTest;
    }

    public String getMessageText() {
        return messageText;
    }


    public void setIsSendPhone() {
        this.isSendPhone = true;
    }

    public boolean getIsSendPhone(){
        return isSendPhone;
    }


    public void setIsSendVk() {
        this.isSendVk = true;
    }

    public boolean getIsSendVk(){
        return isSendVk;
    }


    public void setIsSendMail() {
        this.isSendMail = true;
    }

    public boolean getIsSendMail(){
        return isSendMail;
    }

}
