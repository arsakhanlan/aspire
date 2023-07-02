package com.example.aspire.mini.utils;

import com.example.aspire.mini.bean.RepaymentRequest;
import com.example.aspire.mini.entity.Loan;
import com.example.aspire.mini.entity.Repayment;
import com.example.aspire.mini.entity.ScheduledRepayment;

// Here, we are using strategy design pattern to make available different modes of payment.

public interface RepaymentStrategy {
    Repayment addRepayment(Loan loan, RepaymentRequest repaymentRequest, ScheduledRepayment scheduledRepayment);
}
