package me.ajfleming.qikserve.exception;

/**
 *  Class: BadRequestException
 *  Purpose: An Simple Exception to help differentiate when the API should return the HTTP Status of BAD Request (400)
 *  Author: Andrew Fleming
 */

public class BadRequestException extends Exception{

    public BadRequestException(String message)
    {
        super(message);
    }
}
