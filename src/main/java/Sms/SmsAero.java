package Sms;

import Exeptions.*;
import org.apache.commons.codec.digest.DigestUtils;
import parsing.students.Student;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by Вадим on 24.04.2016.
 */
public class SmsAero extends SmsServices {

    private static ArrayList<Student> list;

    public static void main(String[] args) {
        SmsAero smsAero = new SmsAero(list,"qwery","qwery","Вот такое письмо я хочу увидеть!@#$%^&*()_+");
        String s;
        s = smsAero.message;
        System.out.println(s);
        s = URLDecoder.decode(smsAero.message);
        System.out.println(s);
    }


    public SmsAero(ArrayList<Student> list, String login, String password, String message) {
        super(list, login, DigestUtils.md5Hex(password), message);
        //this.password = DigestUtils.md5Hex(password);
    }


    public void SendMsg() throws ServiceNotWork, AuthFailed, NotConfirmLogin, TooBigSms, NonText, NotEnoughMoney {

        if (checkbill()) {
            for (Student student : list) {
                if (Send(student.getPhone())){
                    student.setIsSendPhone();
                }
            }
        } else throw new  NotEnoughMoney("Недостаточно средств на счете");
    }




    @Override
    protected boolean Send(String to )throws ServiceNotWork, NonText, AuthFailed, NotConfirmLogin, TooBigSms{
        final String request = "https://gate.smsaero.ru/send/?user="+ login +"&password="+ password+ "&to="+ to +"&text="+ message+ "&answer=json";
        sendrequest(request);
        return true;
    }

    @Override
    protected boolean checkbill() throws ServiceNotWork, NotConfirmLogin, AuthFailed, TooBigSms, NonText {
        double balance = 0;
        double cost = 0;
        final String requestbalance = "https://gate.smsaero.ru/balance/?user="+login+"&password="+password+"&answer=json";
        final String requestcost="https://gate.smsaero.ru/checktarif/?user="+login+"&password="+password+"&answer=json";

        return false;
    }



    @Override
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

    @Override
    protected void throwError(String error) throws ServiceNotWork, AuthFailed, NotConfirmLogin, NonText, TooBigSms {

    }
}
