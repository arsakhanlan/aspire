package com.example.aspire.mini.exception;

public class CustomerNotFoundException extends IllegalArgumentException{
    public CustomerNotFoundException(String s){
        super(s);
    }
}
