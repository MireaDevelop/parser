package Exeptions;

/**
 * Created by Вадим on 23.04.2016.
 */
public class NotConfirmLogin extends Exception {
    public NotConfirmLogin(String message){
        super(message);
    }
}
