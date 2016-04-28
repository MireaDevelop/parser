package org.exeptions;

/**
 * Created by Вадим on 21.04.2016.
 */
public class ServiceNotWork extends Exception {
    public ServiceNotWork(String message){
        super(message);
    }
}
