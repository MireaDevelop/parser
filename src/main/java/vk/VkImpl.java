package vk;

import parsing.students.Student;

import java.util.ArrayList;

/**
 * Created by aleksejpluhin on 12.04.16.
 */
public class VkImpl implements Runnable, Vk {
    private ArrayList<Student> students;
    private String accessToken;
    private String id;

    private static String API_VERSION = "5.50";
    private static final String API_REQUEST = "https://api.vk.com/method/message.send"
            + "?{PARAMETERS}"
            + "&access_token={ACCESS_TOKEN}"
            + "&v=" + API_VERSION;

    public VkImpl() {
    }

    public void sendVk(ArrayList<Student> students) {
        this.students = students;
        this.run();
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void sendMessage(Student student) throws Exception {
      Parametrs parametrs = new Parametrs();
      if(student.getVk_id().replaceAll("[0-9]","").length() == 0 ) {
          parametrs.setParams("user_id", id);
          parametrs.setParams("message", student.getMessageText());
      } else {
          parametrs.setParams("domain", student.getVk_id());
          parametrs.setParams("message", student.getMessageText());
      }
      String urlRequest = invokeApi(parametrs);



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
