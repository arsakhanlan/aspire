package com.example.aspire.mini.repository;

import com.example.aspire.mini.entity.ScheduledRepayment;
import com.example.aspire.mini.enums.RepaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledRepaymentRepository extends JpaRepository<ScheduledRepayment, Long> {
    List<ScheduledRepayment> findByLoanId(Long loanId);
    List<ScheduledRepayment> findByLoanIdAndStatus(Long loanId, RepaymentStatus status);

    int countByLoanIdAndStatus(Long loanId, RepaymentStatus status);
}