package jFrames;

import controller.Controller;
import parsing.*;
import parsing.students.Student;
import parsing.students.Impl.ParserImpl;
import email.*;


import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static parsing.students.Impl.ParserImpl.*;

public class jFrame extends JFrame {
    private static String messageText;
    private static String fileName;
    private static String themeText;
    private boolean isMail;
    private boolean isVk;
    private boolean isPhone;
    FileInputStream fis;

    public jFrame() {
        super("Тестовый парсер");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initjFrame();
    }

    public static String getThemeText() {
        return themeText;
    }

    public static String getMessageText() {
        return messageText;
    }

    private static String getFileName() {
        return fileName;
    }

    private void initjFrame() {

        JMenuBar menuBar = new JMenuBar();
        JMenu FileMenu = new JMenu("Настройки");
        menuBar.add(FileMenu);
        JMenuItem settingsButton = new JMenuItem("Открыть");
        FileMenu.add(settingsButton);
        this.setJMenuBar(menuBar);

        JLabel str1 = new JLabel("Введите тему сообщения:");
        JTextField themeField = new JTextField();
        themeField.setAutoscrolls(true);

        JLabel str2 = new JLabel("Введите текст сообщения:");
        JTextArea messageField = new JTextArea(15, 5);
        messageField.setLineWrap(true);
        messageField.setWrapStyleWord(true);


        final JLabel label = new JLabel("Выбранный файл");
        label.setAlignmentX(LEFT_ALIGNMENT);

        JButton chooseFileButton = new JButton("Выбрать файл");
        chooseFileButton.setAlignmentX(LEFT_ALIGNMENT);

        JButton sendMailsButton = new JButton("Сделать рассылку");
        sendMailsButton.setAlignmentX(CENTER_ALIGNMENT);

        chooseFileButton.addActionListener(e -> {
            JFileChooser fileset = new JFileChooser(new File("."));
            fileset.setAcceptAllFileFilterUsed(false);
            fileset.addChoosableFileFilter(new FileTypeFilter(".xlsx", "Microsoft Excel Documents"));
            int ret = fileset.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileset.getSelectedFile();
                label.setText(file.getName());
                fileName = (file.getAbsolutePath());
                try {
                    fis = new FileInputStream(file.getAbsolutePath());
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        sendMailsButton.addActionListener(e -> {
            themeText = themeField.getText();
            messageText = messageField.getText();
            ArrayList<Student> list;
            try {
                list = ParserImpl.getStudents(fis, 1, 2, 3, 4, 5);
                //Lists.SortList(Parsing.parse(jFrame.getFileName()));
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this,
                        "Выберите документ с данными.",
                        "Не выбран документ.",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            //    SendMails.Send(list);
            try {
                new Controller(list, false, true, false).send();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (MessagingException e1) {
                e1.printStackTrace();
            }
            JOptionPane.showMessageDialog(this,
                    "Рассылка выполнена",
                    "Готово!",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        settingsButton.addActionListener(e -> new Settings());

        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(str1, BorderLayout.NORTH);
        topPanel.add(themeField, BorderLayout.SOUTH);

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BorderLayout());
        centralPanel.add(str2, BorderLayout.NORTH);
        centralPanel.add(messageField, BorderLayout.SOUTH);


        JPanel bottomNorthPanel = new JPanel();
        bottomNorthPanel.setLayout(new GridLayout(1, 2));

        Box bottomNorthLeft = Box.createVerticalBox();
        Box bottonNorthRight = Box.createVerticalBox();

        bottomNorthLeft.add(chooseFileButton);
        bottonNorthRight.add(label);

        bottomNorthPanel.add(bottomNorthLeft);
        bottomNorthPanel.add(bottonNorthRight);

        JPanel bottomSouthPanel = new JPanel();
        bottomSouthPanel.setLayout(new BorderLayout());

        bottomSouthPanel.add(bottomNorthPanel, BorderLayout.NORTH);
        bottomSouthPanel.add(sendMailsButton, BorderLayout.SOUTH);


        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centralPanel);
        panel.add(bottomSouthPanel, BorderLayout.SOUTH);

        this.add(panel);
        this.pack();
        this.setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}


class FileTypeFilter extends FileFilter {
    private String extension;
    private String description;

    FileTypeFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    public boolean accept(File file) {
        return file.isDirectory() || file.getName().endsWith(extension);
    }

    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }
}