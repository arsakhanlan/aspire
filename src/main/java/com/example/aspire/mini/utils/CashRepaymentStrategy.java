package com.example.aspire.mini.utils;

import com.example.aspire.mini.bean.RepaymentRequest;
import com.example.aspire.mini.entity.Loan;
import com.example.aspire.mini.entity.Repayment;
import com.example.aspire.mini.entity.ScheduledRepayment;
import com.example.aspire.mini.enums.RepaymentStatus;

public class CashRepaymentStrategy implements RepaymentStrategy{

    @Override
    public Repayment addRepayment(Loan loan, RepaymentRequest repaymentRequest, ScheduledRepayment scheduledRepayment) {
        System.out.println("Paying with Cash");
        Repayment repayment = new Repayment();
        repayment.setLoan(loan);
        repayment.setScheduledRepayment(scheduledRepayment);
        repayment.setAmount(repaymentRequest.getAmount());
        repayment.setStatus(RepaymentStatus.PAID);
        return repayment;
    }
}
