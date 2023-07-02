package com.example.aspire.mini.service;

import com.example.aspire.mini.bean.LoanRequest;
import com.example.aspire.mini.bean.RepaymentRequest;
import com.example.aspire.mini.entity.Loan;
import com.example.aspire.mini.entity.Repayment;
import com.example.aspire.mini.exception.LoanNotFoundException;

import java.util.List;

public interface LoanService {

    Loan createLoan(LoanRequest loanRequest);

    Loan approveLoan(Long loanId) throws LoanNotFoundException;

    List<Loan> getAllLoansByCustomerId(Long customerId);

    Repayment addRepayment(RepaymentRequest repaymentRequest) throws LoanNotFoundException;

    Loan getLoanById(Long loanId);
}
