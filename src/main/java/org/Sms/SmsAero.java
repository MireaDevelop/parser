package org.Sms;

import org.exeptions.*;
import org.students.Student;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;


/**
 * Created by Вадим on 24.04.2016.
 * Для отправки сообщений вызвать метод SendMsg и перехватить соответстующие исключения;
 * Сообщения отправляются по одному;
 *
 */
public final class SmsAero extends SmsServices {

   


    public SmsAero(ArrayList<Student> list, String login, String password, String message,String from) {
        super(list, login, DigestUtils.md5Hex(password), message,from);
    }


    public void SendMsg() throws ServiceNotWork, AuthFailed, NotConfirmLogin, TooBigSms, NonText,
            NotEnoughMoney, NotConfirmSenderName {

        if (checkbill()) {
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
        final String request = "https://gate.smsaero.ru/send/?user="+ login
                +"&password=" + password
                + "&to="+ to
                +"&text="+ message
                + "&from="+ from
                +"&type=2&answer=json";

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
        int size = URLDecoder.decode(message).length() / 70 + 1;

        Response responsecost,responsebalance;

        final String requestbalance = "https://gate.smsaero.ru/balance/?user=" +login
                + "&password="+password
                + "&answer=json";
        final String requestcost="https://gate.smsaero.ru/checktarif/?user=" + login
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
        HttpURLConnection connection;
        BufferedReader rd;
        String line;
        String result="";

        try {
            url = new URL(urlToRead);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result +=line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Response.class, new SmsAeroDeserializer());
        Gson gson = builder.create();
        Response response = gson.fromJson(result, Response.class);

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
class Response{

    private String result;
    private String reason;
    private String Direct_channel;
    private String Digital_channel;
    private String balance;
    private String id;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDigital_channel() {
        return Digital_channel;
    }

    public void setDigital_channel(String digital_channel) {
        Digital_channel = digital_channel;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirect_channel() {
        return Direct_channel;
    }

    public void setDirect_channel(String direct_channel) {
        Direct_channel = direct_channel;
    }

}


