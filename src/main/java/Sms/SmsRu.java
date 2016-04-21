package Sms;

import parsing.students.Student;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Вадим on 20.04.2016.
 */
public class SmsRu implements SmsServices {

    private ArrayList<Student> list;
    private String login;
    private String password;
    private String message;
    private String from;

   

    SmsRu(ArrayList<Student> list, String login, String password, String message, String from){
        this.list = list;
        this.login = login;
        this.password = password;
        this.message="";
        for (int i = 0; i <message.length() ; i++) {
            if (message.charAt(i) == ' '){
               this.message += "%20";
            } else {
                this.message+= message.charAt(i);
            }
        }
        this.from = from;
        System.out.println(this.message);
    }

    @Override
    public void CreateGroup() {

        String numbers="";
        int count=0;
        final int max = 100;

        if (checkbill()) {

            for (int i=0; i<list.size();i++) {
                if (count == max) {
                    Send(numbers);
                    count = 0;
                    numbers = "";
                }
                if (count < max) {
                    numbers += list.get(i).getPhone();
                    count++;
                }
            }
        Send(numbers);
        }

    }


    @Override
    public void Send(String to) {

        String result;
        final String request = "http://sms.ru/sms/send?login=" + login +
                "&password=" + password + "&to=" + to + "&text=" + message;//"&test=1"
        result = sendrequest(request);
        //System.out.println(result);

    }

    public boolean checkbill() {
        String result;
        final String balance = "http://sms.ru/my/balance?login=" + login + "&password=" + password;
        //final String cost = "http://sms.ru/sms/cost?login=" + login + "&password=" + password + "&to=" + list.get(0).getPhone() + "&text=" + message;
        final String testcost = "http://sms.ru/sms/cost?login=" + login + "&password="
                + password + "&to=" + "79156666666" + "&text=" + message;
        result = sendrequest(testcost);


        //if (requestcost * list.getsize() < requestbalance) {
        return true;

        //else return false;
    }

    public String sendrequest(String urlToRead) {
        URL url;
        HttpURLConnection connection;
        BufferedReader rd;
        String line;
        String r = "";
        String result = "";
        try {
            url = new URL(urlToRead);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            //sleep(30);
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        /*line = "";
        for (int i = 0; i <result.length() ; i++) {
            if (line.length() !=3){
                line += result.charAt(i);
            } else {
                list.add(line);
                line="";
            }
        }*/;
        return result;
    }
}
