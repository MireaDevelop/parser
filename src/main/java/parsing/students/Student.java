package parsing.students;

/**
 * Created by aleksejpluhin on 06.04.16.
 */
public class Student {
    private String name;
    private String surname;
    private String mail;
    private String phone;
    private String vk_id;
    private boolean isSendPhone = false;
    private boolean isSendVk = false;
    private boolean isSendMail = false;
    private String messageText;


    public Student(String name, String surname, String mail, String phone, String vk_id) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.phone = phone;
        this.vk_id = vk_id;

    }

    public void setMessageText(String messageTest) {
        this.messageText = messageTest;
    }

    public String getName() {
        return name;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getSurname() {
        return surname;
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

    public void setIsSendPhone() {
        this.isSendPhone = true;
    }

    public void setIsSendVk() {
        this.isSendVk = true;
    }

    public void setIsSendMail() {
        this.isSendMail = true;
    }

    public boolean getIsSendPhone(){
        return isSendPhone;
    }

    public boolean getIsSendVk(){
        return isSendVk;
    }

    public boolean getIsSendMail(){
        return isSendMail;
    }

    @Override
    public String toString() {
        return "Студент: " + name + " " + surname +
                " Почта " + isSendMail + " Вконтакте " + isSendVk +
                " Телефон " + isSendPhone;
    }
}
