package com.jashu.shopping_website.exception;

public class DuplicateEntryException extends RuntimeException{

    public DuplicateEntryException(String msg){
        super(msg);
    }
}
