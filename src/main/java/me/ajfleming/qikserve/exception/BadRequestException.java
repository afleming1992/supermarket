package me.ajfleming.qikserve.exception;

/**
 * Created by andrew on 21/01/17.
 */
public class BadRequestException extends Exception{

    public BadRequestException(String message)
    {
        super(message);
    }
}
