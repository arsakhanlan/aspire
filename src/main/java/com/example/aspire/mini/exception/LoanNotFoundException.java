package com.example.aspire.mini.exception;

import org.springframework.data.crossstore.ChangeSetPersister;

public class LoanNotFoundException extends IllegalArgumentException {
    public LoanNotFoundException(String s) {
        super(s);
    }
}
