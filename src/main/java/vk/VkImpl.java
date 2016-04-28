package vk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import parsing.students.Student;

import java.util.ArrayList;

/**
 * Created by aleksejpluhin on 12.04.16.
 */
public class VkImpl implements Vk, Runnable {
    private static String API_VERSION = "5.50";
    private static final String API_REQUEST = "https://api.vk.com/method/messages.send"
            + "?{PARAMETERS}"
            + "&access_token={ACCESS_TOKEN}"
            + "&v=" + API_VERSION;
    private ArrayList<Student> students;
    private String accessToken;

    public VkImpl() {
    }

    public void setVkStudents(ArrayList<Student> students) {
        this.students = students;

    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }



    public void sendMessage(Student student) throws Exception {
      Parametrs parametrs = new Parametrs();
        if (student.getVk_id().length() == 0 || student.getVk_id() == null) {
            return;
        }
      if(student.getVk_id().replaceAll("[0-9]","").length() == 0 ) {
          parametrs.setParams("user_id", student.getVk_id());
          parametrs.setParams("message", student.getMessageText());
      } else {
          parametrs.setParams("domain", student.getVk_id());
          parametrs.setParams("message", student.getMessageText());
      }
      String urlRequest = invokeApi(parametrs);

        Document document = Jsoup.connect(String.valueOf(urlRequest)).ignoreContentType(true).post();
        if(document.html().contains("response")) {
            student.setIsSendVk();
        } else if(document.html().contains("error")) {
            System.out.println(document.html());
        }



    }

     public String invokeApi(Parametrs params) throws Exception {
        final String parameters = (params == null) ? "" : params.build();
        String reqUrl = API_REQUEST
                .replace("{ACCESS_TOKEN}", accessToken)
                .replace("{PARAMETERS}&", parameters);
        try {
            return reqUrl;
        } catch (Exception e) {
            throw new Exception();
        }

    }


    @Override
    public void run() {
        for(Student student : students) {
            try {
                sendMessage(student);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
