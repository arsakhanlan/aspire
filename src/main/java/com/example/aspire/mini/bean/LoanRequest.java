package com.example.aspire.mini.bean;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
public class LoanRequest {

    private Integer term;
    private BigDecimal amount;
    private Long customerId;

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
