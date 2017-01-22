package me.ajfleming.qikserve.exception;


/**
 *  Class: BasketException
 *  Purpose: An Simple Exception to differeniate Basket Calculation Errors.
 *  Author: Andrew Fleming
 */
public class BasketException extends Exception {

    public BasketException(String message)
    {
        super(message);
    }

}
