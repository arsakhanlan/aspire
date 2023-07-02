package com.example.aspire.mini.utils;

import com.example.aspire.mini.entity.Loan;
import com.example.aspire.mini.enums.LoanStatus;
import com.example.aspire.mini.exception.LoanNotFoundException;
import com.example.aspire.mini.repository.LoanRepository;
import lombok.SneakyThrows;

// This is a singleton class to loan approval requests. Only one instance of this class is created and reused.

public class LoanApprovalManager {

    private final LoanRepository loanRepository;
    private static LoanApprovalManager instance;

    private LoanApprovalManager(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
        // Private constructor to prevent direct instantiation
    }

    public static LoanApprovalManager getInstance(LoanRepository loanRepository) {
        if (instance == null) {

            // synchronized makes creation of object of LoanApprovalManager thread-safe

            synchronized (LoanApprovalManager.class) {
                if (instance == null) {
                    instance = new LoanApprovalManager(loanRepository);
                }
            }
        }
        return instance;
    }

    @SneakyThrows
    public Loan approveLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with ID: " + loanId));
        loan.setStatus(LoanStatus.APPROVED);
        return loanRepository.save(loan);
    }
}

