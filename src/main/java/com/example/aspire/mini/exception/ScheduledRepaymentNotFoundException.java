package com.example.aspire.mini.exception;

import org.springframework.data.crossstore.ChangeSetPersister;

public class ScheduledRepaymentNotFoundException extends IllegalArgumentException {
    public ScheduledRepaymentNotFoundException(String s){
        super(s);
    }
}
