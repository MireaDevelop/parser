package org.Sms.Services.SmSRu;


import org.Sms.Services.SmsService;
import org.apache.commons.codec.digest.DigestUtils;
import org.exeptions.*;
import org.students.Student;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Вадим on 20.04.2016.
 * Для отправки сообщений вызвать метод SendMsg и перехватить соответстующие исключения;
 * Сообщения отправляются пачками, до 100шт за раз;
 * Если поле from не было заполненно, т.е. = "", то автоматически вставится номер(он же логин) отправителя;
 */

public final class SmsRu extends SmsService {

    private String token;
    private String hexpassword;


    public SmsRu(ArrayList<Student> list, String login, String password, String message, String from, boolean sureEnoughtMoney){
        super(list,login,password,message,from,sureEnoughtMoney);
        if (from.equals("")){
            this.from = login;
        }
        getToken();
    }



    public void SendMsg() throws ServiceNotWork, AuthFailed, NotConfirmLogin, TooBigSms, NonText,
            NotEnoughMoney, NotConfirmSenderName {

        StringBuilder numbers = new StringBuilder("");

        int count=0;
        int n = 0;
        final int max = 100;

        if (SureEnoughtMoney || checkbill()) {

            for (int i=0; i<list.size();i++) {
                if (count == max) {

                    numbers.deleteCharAt(numbers.lastIndexOf(","));

                    if (Send(numbers.toString())) {
                        for (int j = i-count; j < i ; j++) {
                            list.get(j).setIsSendPhone();
                        }
                    }
                    count = 0;
                    numbers = new StringBuilder("");
                }

                if (count < max) {
                    numbers.append(list.get(i).getPhone() + ",");
                    count++;
                    n = i;
                }
            }

            numbers.deleteCharAt(numbers.lastIndexOf(","));

            if (Send(numbers.toString())){
                for (int j = n-count; j <n ; j++) {
                    list.get(j).setIsSendPhone();
                }
            }

        } else {
            throw new NotEnoughMoney("Недостаточно средств на счете");
        }

    }


    @Override
    protected boolean Send(String to) throws ServiceNotWork, NonText, AuthFailed, NotConfirmLogin, TooBigSms,
            NotConfirmSenderName, NotEnoughMoney {

        ArrayList<String> response;



        final String request = "http://sms.ru/sms/send"
                + "?login=" + login
                + "&token=" + token
                + "&sha512=" + hexpassword
                + "&to=" + to
                + "&text=" + message;//+"&test=1";

        checkToken();
        response = sendrequest(request);

        if (response.get(0).equals("100")){
            return true;
        } else{
            throwError(response.get(0));
            return false;
        }
    }

    protected boolean checkbill() throws ServiceNotWork, NotConfirmLogin, AuthFailed, TooBigSms, NonText,
            NotConfirmSenderName, NotEnoughMoney {

        ArrayList<String> responecost,responsebalance;

        double balance = 0;
        double cost = 0;

        final String requestbalance = "http://sms.ru/my/balance"
                + "?login=" + login
                + "&token="+ token
                + "&sha512="+ hexpassword;

        final String requsestcost = "http://sms.ru/sms/cost"
                + "?login=" + login
                + "&token="+ token
                + "&sha512="+ hexpassword
                + "&to=" + list.get(0).getPhone()
                + "&text=" + message;

        checkToken();
        responecost = sendrequest(requsestcost);
        responsebalance =sendrequest(requestbalance);

        if (responecost.get(0).equals("100")) {
            cost = Double.parseDouble(responecost.get(1));
        }   else {
            throwError(responecost.get(0));
        }

        if (responsebalance.get(0).equals("100")){
            balance = Double.parseDouble(responsebalance.get(1));
        } else {
            throwError(responsebalance.get(0));
        }

        if (balance > cost *list.size()){
            return true;
        } else
            return false;
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

    protected void checkToken() throws ServiceNotWork, AuthFailed, NotEnoughMoney, NotConfirmSenderName, NonText, NotConfirmLogin, TooBigSms {

        final String get_token = "http://sms.ru/auth/get_token";

        final String check = "http://sms.ru/auth/check"
                + "?login=" + login
                + "&token="+ token
                + "&sha512="+ hexpassword;

        ArrayList<String> responseCheck = sendrequest(check);

        if (responseCheck.get(0).equals("100"))
            return;

        else if (responseCheck.get(0).equals("300")){

            ArrayList<String> response = sendrequest(get_token);
            token = response.get(0);
            hexpassword = DigestUtils.sha512Hex(password + token);
        }
        else {
            throwError(responseCheck.get(0));
        }
    }

    protected void getToken(){

        final String get_token = "http://sms.ru/auth/get_token";
        ArrayList<String> response = sendrequest(get_token);
        token = response.get(0);
        hexpassword = DigestUtils.sha512Hex(password + token);
    }

    protected void throwError(String error) throws ServiceNotWork, AuthFailed, NotConfirmLogin, NonText, TooBigSms,
            NotConfirmSenderName, NotEnoughMoney {
        switch (error){
            case "201":
                throw new NotEnoughMoney("Недостаточно средств на счете");

            case "203":
                throw new NonText("Нет текста");

            case "204":
                throw  new NotConfirmSenderName("Подпись не подтверждена");

            case "205":
                throw new TooBigSms("Слишком большое сообщение(превышает 8 смс)");

            case "220":
                throw new ServiceNotWork("Сервис временно недоступен");

            case "301":
                throw new AuthFailed("Неправильный логин или пароль");

            case "302":
                throw new NotConfirmLogin("Аккаунт не подтвержден");
        }
    }

}
