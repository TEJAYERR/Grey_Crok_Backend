package com.jashu.shopping_website.exception;

public class UnauthorizedOperationException extends RuntimeException {

    public UnauthorizedOperationException(String message){
        super(message);
    }
}
