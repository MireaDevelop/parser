package org.vk;

import org.students.Student;

import java.util.ArrayList;

/**
 * Created by aleksejpluhin on 16.04.16.
 */
public interface Vk {

     void sendMessage(Student student) throws Exception;
     String invokeApi(Parametrs params) throws Exception;

     void setVkStudents(ArrayList<Student> students);
     void setAccessToken(String accessToken);

}
