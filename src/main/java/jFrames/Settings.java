package jFrames;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Settings extends JFrame {
    private static String smtpHost;
    private static String addressFrom;
    private static String login;
    private static String password;
    private static String domain;
    private JComboBox<String> domainBox= new JComboBox<>();
    public static String getPassword() {
        return password;
    }

    Settings() {
        super("Настройки");
        setBounds(300, 300, 250, 400);
        initSettings();
    }

    private static String getDomain() {
        return domain;
    }

    public static String getSmtpHost() {
        return smtpHost;
    }

    public static String getSmtpPort() {
        return "25";
    }


    public static String getLogin() {
        return login;
    }

    public static String getAddressFrom() {
        return addressFrom;
    }

    public static void getSettings() {
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);
            smtpHost = property.getProperty("smtpHost");
            addressFrom = property.getProperty("addressFrom");
            login = property.getProperty("login");
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }

    private static void setSettings(String login, String smtpHost, String addressFrom) {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream("src/main/resources/config.properties");
            prop.setProperty("login", login);
            prop.setProperty("smtpHost", smtpHost);
            prop.setProperty("addressFrom", addressFrom);
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initSettings() {

        String hosts[] = {"","Mail", "Rambler"};
        JComboBox<String> hostsBox = new JComboBox<>(hosts);

        domainBox.setEditable(true);

       // String mailDomens[] = {"@mail.ru", "@inbox.ru", "@list.ru", "@bk.ru"};

        JLabel loginStr = new JLabel("Логин");
        JLabel passwordStr = new JLabel("Пароль");

        JTextField loginField = new JTextField(getLogin());
        JPasswordField passwordField = new JPasswordField();

        JButton saveButton = new JButton("Сохранить");
        JButton exitButton = new JButton("Выйти");


        hostsBox.addActionListener(e -> setBoxType(hostsBox));

    domainBox.addActionListener(e -> {
            domain = (String)domainBox.getSelectedItem();
            switch((String)domainBox.getSelectedItem()){
                case "@mail.ru": smtpHost = "smtp.mail.ru"; break;
                case "@inbox.ru": smtpHost = "smtp.inbox.ru";break;
                case "@list.ru": smtpHost = "smtp.list.ru";break;
                case "@bk.ru": smtpHost = "smtp.bk.ru";break;
                case "@rambler.ru": smtpHost = "smtp.rambler.ru";break;
//                case "@inbox.ru": domain = "smtp.inbox.ru";break;
//                case "@inbox.ru": domain = "smtp.inbox.ru";break;
            }
        });

        saveButton.addActionListener(e -> {
            login = loginField.getText();
            password = passwordField.getText();
            addressFrom = getLogin() + getDomain();
            setSettings(getLogin(), getSmtpHost(), getAddressFrom());
        });
        exitButton.addActionListener(e -> this.setVisible(false));

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));
        Box topLeft = Box.createVerticalBox();
        Box topRight = Box.createVerticalBox();
        topLeft.add(Box.createVerticalStrut(5));
        topLeft.add(new JLabel("Почтовый сервис:"), BorderLayout.NORTH);
        topLeft.add(Box.createVerticalStrut(5));
        topLeft.add(new JLabel("Домен:"), BorderLayout.SOUTH);
        topRight.add(hostsBox, BorderLayout.NORTH);
        topRight.add(domainBox, BorderLayout.SOUTH);
        topPanel.add(topLeft);
        topPanel.add(topRight);

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout(1, 2));
        Box centralLeft = Box.createVerticalBox();
        Box centralRight = Box.createVerticalBox();
        centralLeft.add(loginStr, BorderLayout.NORTH);
        centralLeft.add(Box.createVerticalStrut(5));
        centralLeft.add(passwordStr, BorderLayout.SOUTH);
        centralRight.add(loginField, BorderLayout.NORTH);
        centralRight.add(passwordField, BorderLayout.SOUTH);
        centralPanel.add(centralLeft, BorderLayout.NORTH);
        centralPanel.add(centralRight, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(saveButton, BorderLayout.WEST);
        bottomPanel.add(exitButton, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centralPanel);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        this.add(panel);
        pack();
        this.setResizable(false);
        setLocationRelativeTo(getOwner());
        setVisible(true);


    }
    private void setBoxType(JComboBox<String> hostsBox) {
        domainBox.removeAllItems();

        String host = (String) hostsBox.getSelectedItem();
        switch (host) {
            case "Mail":
                String mailDomens[] = {"","@mail.ru", "@inbox.ru", "@list.ru", "@bk.ru"};
                for (String mailDomen : mailDomens) {
                    domainBox.addItem(mailDomen);
                }
                break;
            case "Rambler":
                String ramblerDomens[] = {"","@rambler.ru"};
                for (String ramblerDomen : ramblerDomens) {
                    domainBox.addItem(ramblerDomen);
                }
                break;
        }
    }
}
