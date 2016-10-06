package org.Sms;

import org.Sms.Services.SmSAero.Response;
import org.Sms.Services.SmSAero.SmsAero;
import org.Sms.Services.SmSRu.SmsRu;
import org.Sms.Services.SmsService;
import org.exeptions.*;
import org.students.Student;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Вадим on 20.04.2016.
 */
public class SendSms extends Thread {

    private String service;
    private String login;
    private String password;
    private String message;
    private String from;

    private ArrayList<Student> list;
    private ConcurrentHashMap<String, String> errormap;

    private boolean SureEnoughtMoney;
    private boolean finish = false;

    SendSms(String service, String login, String password, String message, String from,
            ArrayList<Student> list, ConcurrentHashMap<String, String> errormap, boolean SureEnoughtMoney) {

        this.service = service;

        this.list = list;
        this.errormap = errormap;

        this.login = login;
        this.password = password;
        this.message = message;
        this.from = from;

        this.SureEnoughtMoney = SureEnoughtMoney;
    }


    @Override
    public void run() {
        SmsService smsService = null;
        switch (service) {
            case "SmsRu": {
                smsService = new SmsRu(list, login, password, message, from, SureEnoughtMoney);
            }

            case "SmsAero": {
                smsService = new SmsAero(list, login, password, message, from, SureEnoughtMoney);

            }
        }
        try {
            smsService.SendMsg();
        } catch (ServiceNotWork serviceNotWork) {
            errormap.put("sms", serviceNotWork.getMessage());

        } catch (AuthFailed authFailed) {
            errormap.put("sms", authFailed.getMessage());

        } catch (NotConfirmLogin notConfirmLogin) {
            errormap.put("sms", notConfirmLogin.getMessage());

        } catch (TooBigSms tooBigSms) {
            errormap.put("sms", tooBigSms.getMessage());

        } catch (NonText nonText) {
            errormap.put("sms", nonText.getMessage());

        } catch (NotEnoughMoney notEnoughMoney) {
            errormap.put("sms", notEnoughMoney.getMessage());

        } catch (NotConfirmSenderName notConfirmSenderName) {
            errormap.put("sms", notConfirmSenderName.getMessage());
        }
        finish = true;
    }

    public boolean isFinish() {
        return finish;
    }

    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();
        SmsRu smsRu = new SmsRu(list,"","","","",true);
    }
}
