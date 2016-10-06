package org.exeptions;

/**
 * Created by Вадим on 21.04.2016.
 */
public class NotEnoughMoney extends Exception {
    public NotEnoughMoney(String message){
        super(message);
        message = "";
    }
}
