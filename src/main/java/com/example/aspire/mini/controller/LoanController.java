package com.example.aspire.mini.controller;

import com.example.aspire.mini.bean.LoanRequest;
import com.example.aspire.mini.bean.RepaymentRequest;
import com.example.aspire.mini.entity.Loan;
import com.example.aspire.mini.entity.Repayment;
import com.example.aspire.mini.exception.LoanNotFoundException;
import com.example.aspire.mini.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/create")
    public ResponseEntity<Loan> createLoan(@RequestBody LoanRequest loanRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(loanRequest));
    }

    @PutMapping("/{loanId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Loan> approveLoan(@PathVariable Long loanId) throws LoanNotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(loanService.approveLoan(loanId));
    }

    @GetMapping("/{customerId}/getLoans")
    public ResponseEntity<List<Loan>> getLoans(@PathVariable Long customerId){
        return ResponseEntity.status(HttpStatus.FOUND).body(loanService.getAllLoansByCustomerId(customerId));
    }

    @PostMapping("/repayment")
    public ResponseEntity<Repayment> addRepayment(@RequestBody RepaymentRequest repaymentRequest){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(loanService.addRepayment(repaymentRequest));
    }
}

