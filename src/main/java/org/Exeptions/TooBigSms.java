package org.exeptions;

/**
 * Created by Вадим on 21.04.2016.
 */
public class TooBigSms extends Exception {
    public TooBigSms(String message){
        super(message);
    }
}
