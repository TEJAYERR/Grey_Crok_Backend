package com.jashu.shopping_website.exception;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException(String msg){
        super(msg);
    }
}
