package org.exeptions;

/**
 * Created by Вадим on 06.05.2016.
 */
public class NotConfirmSenderName extends Exception {
    public NotConfirmSenderName (String message){
        super(message);
    }
}
