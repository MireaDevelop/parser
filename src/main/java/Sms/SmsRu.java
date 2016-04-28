package Sms;

import Exeptions.*;
import parsing.students.Student;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Вадим on 20.04.2016.
 */
public class SmsRu extends SmsServices {


    SmsRu(ArrayList<Student> list, String login, String password, String message){
        super(list,login,password,message);
    }


    public void SendMsg() throws ServiceNotWork, AuthFailed, NotConfirmLogin, TooBigSms, NonText, NotEnoughMoney {

        String numbers="";
        int count=0;
        int n = 0;
        final int max = 100;

        if (checkbill()) {

            for (int i=0; i<list.size();i++) {
                if (count == max) {
                    if (Send(numbers)) {
                        for (int j = i-count; j < i ; j++) {
                            list.get(j).setIsSendPhone();
                        }
                    }
                    count = 0;
                    numbers = "";
                }

                if (count < max) {
                    numbers += list.get(i).getPhone()+ ",";
                    count++;
                    n = i;
                }
            }
        if (Send(numbers)){
            for (int j = n-count; j <n ; j++) {
                list.get(j).setIsSendPhone();
            }
        }
        } else {
            throw new NotEnoughMoney("Недостаточно средств на счете");
        }

    }


    @Override
    public boolean Send(String to) throws ServiceNotWork, NonText, AuthFailed, NotConfirmLogin, TooBigSms {

        ArrayList<String> response;

        final String request = "http://sms.ru/sms/send?login=" + login + "&password=" + password + "&to=" + to + "&text=" + message;//"&test=1"
        response = sendrequest(request);

        if (response.get(0) == "100"){
            return true;
        } if (response.get(0) != "100"){
            throwError(response.get(0));
            return false;
        }

        return false;
    }

    protected boolean checkbill() throws ServiceNotWork, NotConfirmLogin, AuthFailed, TooBigSms, NonText {
        ArrayList<String> responecost,responsebalance;
        double balance = 0;
        double cost = 0;
        final String requestbalance = "http://sms.ru/my/balance?login=" + login + "&password=" + password;
        final String requsestcost = "http://sms.ru/sms/cost?login=" + login + "&password=" + password + "&to=" + list.get(0).getPhone() + "&text=" + message;
        //final String testcost = "http://sms.ru/sms/cost?login=" + login + "&password=" + password + "&to=" + "79156666666" + "&text=" + message;

        responecost = sendrequest(requsestcost);
        responsebalance =sendrequest(requestbalance);

        if (responecost.get(0) == "100") {
            cost = Double.parseDouble(responecost.get(1));
        }   else if (responecost.get(0) != "100"){
            throwError(responecost.get(0));
        }

        if (responsebalance.get(0) == "100"){
            balance = Double.parseDouble(responsebalance.get(1));
        } else if (responsebalance.get(0) != "100"){
            throwError(responsebalance.get(0));
        }

        responecost = null;
        responsebalance = null;

        if (balance > cost *list.size()){
            return true;
        } else return false;

    }



    protected ArrayList<String> sendrequest(String urlToRead) {
        URL url;
        HttpURLConnection connection;
        BufferedReader rd;
        String line;
        ArrayList<String> list = new ArrayList<>();
        try {
            url = new URL(urlToRead);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = rd.readLine()) != null) {
                list.add(line);
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    protected void throwError(String error) throws ServiceNotWork, AuthFailed, NotConfirmLogin, NonText, TooBigSms {
        switch (error){
            case "203":
                throw new NonText("Нет текста");

            case "205":
                throw new TooBigSms("Слишком большое сообщение(превышает 8 смс)");

            case  "220":
                throw new ServiceNotWork("Сервис временно недоступен");

            case "301":
                throw new AuthFailed("Неправильный логин или пароль");

            case "302":
                throw new NotConfirmLogin("Аккаунт не подтвержден");
        }
    }
}
