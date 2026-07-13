package com.jashu.shopping_website.exception;

public class DuplicateOperationException extends RuntimeException {

    public DuplicateOperationException(String msg){
        super(msg);
    }
}
