package com.example.aspire.mini.repository;

import com.example.aspire.mini.entity.Customer;
import com.example.aspire.mini.entity.Loan;
import com.example.aspire.mini.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByCustomer(Customer customer);

    Loan findByIdAndStatus(Long loanId, LoanStatus loanStatus);
}
