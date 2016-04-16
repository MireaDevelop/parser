package vk;

import parsing.students.Student;

import java.util.ArrayList;

/**
 * Created by aleksejpluhin on 16.04.16.
 */
public interface Vk {

     void sendMessage(Student student) throws Exception;
     String invokeApi(Parametrs params) throws Exception;
     void sendVk(ArrayList<Student> students);
     void setAccessToken(String accessToken);
     void setId(String id);
}
