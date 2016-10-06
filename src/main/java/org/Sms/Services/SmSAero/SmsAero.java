package org.Sms.Services.SmSAero;

import org.Sms.Services.SmsService;
import org.exeptions.*;
import org.students.Impl.ParserImpl;
import org.students.Parser;
import org.students.Student;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.codec.digest.DigestUtils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.net.URLDecoder;


/**
 * Created by Вадим on 24.04.2016.
 * Для отправки сообщений вызвать метод SendMsg и перехватить соответстующие исключения;
 * Сообщения отправляются по одному;
 *
 */
public final class SmsAero extends SmsService {

   private static ArrayList<Student> list;

    public static void main(String[] args) {

        list= new ArrayList<>();
        list.add(new Student("","","",""));
        SmsAero smsAero = new SmsAero(list,"vadim0872@mail.ru","82lxy0c1","Проверка стоимости сообщения.","MireaDev",true);//8
        System.out.println(smsAero.password);
        String s;
        //smsAero.from = "MireaDev";
        s = smsAero.message;
        try {
            smsAero.checkbill();
        } catch (ServiceNotWork serviceNotWork) {
            serviceNotWork.printStackTrace();
        } catch (NotConfirmLogin notConfirmLogin) {
            notConfirmLogin.printStackTrace();
        } catch (AuthFailed authFailed) {
            authFailed.printStackTrace();
        } catch (TooBigSms tooBigSms) {
            tooBigSms.printStackTrace();
        } catch (NonText nonText) {
            nonText.printStackTrace();
        } catch (NotConfirmSenderName notConfirmSenderName) {
            notConfirmSenderName.printStackTrace();
        } catch (NotEnoughMoney notEnoughMoney) {
            notEnoughMoney.printStackTrace();
        }
        System.out.println(s);
        try {
            s = URLDecoder.decode(smsAero.message, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(s);
        try {
            //smsAero.Send("79175364142");
            try {
                smsAero.checkbill();
            } catch (NotConfirmSenderName notConfirmSenderName) {
                notConfirmSenderName.printStackTrace();
            } catch (NotEnoughMoney notEnoughMoney) {
                notEnoughMoney.printStackTrace();
            }
        } catch (ServiceNotWork serviceNotWork) {
            serviceNotWork.printStackTrace();
        } catch (NonText nonText) {
            nonText.printStackTrace();
        } catch (AuthFailed authFailed) {
            authFailed.printStackTrace();
        } catch (NotConfirmLogin notConfirmLogin) {
            notConfirmLogin.printStackTrace();
        } catch (TooBigSms tooBigSms) {
            tooBigSms.printStackTrace();
        }
       /* Response response = smsAero.sendrequest("http://gate.smsaero.ru/send/?user=vadim0872@mail.ru&password=50d9fb15e0b5e11953be7f03f379880&to=79175364142&textПроверка стоимости сообщения. Тест, тест, тест, надо набрать больше 70 символов кириллицы, а лучше с запасом!=&from=NEWS&type=3&answer=json");
        System.out.println(response.id);
        System.out.println(response.result);
        Response response1 = smsAero.sendrequest("https://gate.smsaero.ru/checktarif/?user=vadim0872@mail.ru&password=50d9fb15e0b5e11953be7f03f379880c&answer=json");
        System.out.println(response1.reason.Direct_channel);
        System.out.println(response1.result);*/

    }


    public SmsAero(ArrayList<Student> list, String login, String password, String message,String from, boolean sureEnoughtMoney) {
        super(list, login, DigestUtils.md5Hex(password), message,from, sureEnoughtMoney);
    }


    public void SendMsg() throws ServiceNotWork, AuthFailed, NotConfirmLogin, TooBigSms, NonText,
            NotEnoughMoney, NotConfirmSenderName {

        if (SureEnoughtMoney || checkbill()) {
            for (Student student : list) {
                if (Send(student.getPhone())){
                    student.setIsSendPhone();
                }
            }
        } else throw new  NotEnoughMoney("Недостаточно средств на счете");
    }




    @Override
    protected boolean Send(String to ) throws ServiceNotWork, NonText, AuthFailed, NotConfirmLogin, TooBigSms,
            NotConfirmSenderName, NotEnoughMoney {
        final String request = "https://gate.smsaero.ru/send/"
                + "?user="+ login
                + "&password=" + password
                + "&to="+ to
                + "&text="+ message
                + "&from="+ from
                + "&type=2&answer=json";

        Response response = sendrequest(request);

        if (response.getResult().equals("accepted")){
            return true;
        } else {
            throwError(response.getReason());
            return false;
        }
    }

    @Override
    protected boolean checkbill() throws ServiceNotWork, NotConfirmLogin, AuthFailed, TooBigSms, NonText,
            NotConfirmSenderName, NotEnoughMoney {

        double balance = 0;
        double cost = 0;
        int size = 0;

        Response responsecost,responsebalance;

        try {
            size = URLDecoder.decode(message, "utf-8").length() / 70 + 1;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        final String requestbalance = "https://gate.smsaero.ru/balance/?user=" +login
                + "&password="+password
                + "&answer=json";
        final String requestcost= "https://gate.smsaero.ru/checktarif/?user=" + login
                + "&password=" + password
                + "&answer=json";


        responsecost = sendrequest(requestcost);

        if (responsecost.getResult().equals("accepted")) {
            cost = Double.parseDouble(responsecost.getDirect_channel());
        } else{
            throwError(responsecost.getReason());
        }

        responsebalance = sendrequest(requestbalance);

        if (responsebalance.getResult().equals("accepted")){
            balance = Double.parseDouble(responsebalance.getBalance());
        } else {
            throwError(responsebalance.getReason());
        }

        if (balance > cost * size * list.size()){
            return true;
        } else {
            return false;
        }
    }



    @Override
    protected Response sendrequest(String urlToRead) {

        URL url;
        HttpsURLConnection connection;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder("");

        try {
            url = new URL(urlToRead);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Response.class, new SmsAeroDeserializer());
        Gson gson = builder.create();
        Response response = gson.fromJson(result.toString(), Response.class);

        return response;
    }

    @Override
    protected void throwError(String error) throws ServiceNotWork, AuthFailed, NotConfirmLogin, NonText, TooBigSms, NotConfirmSenderName, NotEnoughMoney {
        switch (error){
            case "incorrect user or password":
                throw new AuthFailed("Неправильный логин или пароль");

            case "incorrect sender name":
                throw  new NotConfirmSenderName("Подпись не подтверждена");

            case "no credits":
                throw new NotEnoughMoney("Недостаточно средств на счете");

        }
    }




}



