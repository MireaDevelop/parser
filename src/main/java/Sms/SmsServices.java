package Sms;

import parsing.students.Student;

import java.util.ArrayList;

/**
 * Created by Вадим on 20.04.2016.
 */
public interface SmsServices {
   public abstract void CreateGroup();
   public abstract void Send(String to);
}
