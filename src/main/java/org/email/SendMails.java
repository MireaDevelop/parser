package org.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class SendMails {


    private static final String ENCODING = "UTF-8";

    public SendMails() {

    }


    /*public  void sendMail(ArrayList<Student> list) throws MessagingException, UnsupportedEncodingException {
        Student student;
        for (Student alist : list) {
            student = alist;
            String subject = jFrame.getThemeText();
            String content = "Привет, " + student.getName() + "\n" + jFrame.getMessageText();
            String smtpHost =Settings.getSmtpHost();
            String addressFrom =Settings.getAddressFrom();
            String addressTo = student.getMail();
            String login = Settings.getLogin();
            String password =Settings.getPassword();
            String smtpPort =Settings.getSmtpPort();
            sendSimpleMessage(login, password, addressFrom, addressTo, content, subject, smtpPort, smtpHost);
        }
    }*/

    private static void sendSimpleMessage(String login, String password, String from, String to, String content, String subject, String smtpPort, String smtpHost)
            throws MessagingException, UnsupportedEncodingException {
        Authenticator auth = new MyAuthenticator(login, password);

        Properties props = System.getProperties();
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.mime.charset", ENCODING);
        Session session = Session.getDefaultInstance(props, auth);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setText(content);
        Transport.send(msg);
    }
}

class MyAuthenticator extends Authenticator {
    private String user;
    private String password;

    MyAuthenticator(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        String user = this.user;
        String password = this.password;
        return new PasswordAuthentication(user, password);
    }
}