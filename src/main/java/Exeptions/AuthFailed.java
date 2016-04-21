package Exeptions;

/**
 * Created by Вадим on 21.04.2016.
 */
public class AuthFailed extends Exception {
    public AuthFailed(String message){
        super(message);
    }
}
