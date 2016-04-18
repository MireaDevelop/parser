package org.emails;

import org.controllers.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

public class SendMails {
    private static final String ENCODING = "UTF-8";

    public static void Send(List<String> names, List<String> emailes) throws MessagingException, UnsupportedEncodingException {
        for (int i = 0; i < names.size(); i++) {
            //String subject = jFrame.getThemeText();
            //String content = "Привет, " + names.get(i) + "\n" + jFrame.getMessageText();
            String smtpHost = settingsController.getSmtpHost();
            String addressFrom = settingsController.getAddressFrom();
            String addressTo = emailes.get(i);
            String login = settingsController.getLoginField();
            String password = settingsController.getPasswordField();
            String smtpPort = settingsController.getSmtpPort();
            //sendSimpleMessage(login, password, addressFrom, addressTo, content, subject, smtpPort, smtpHost);
        }
    }

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