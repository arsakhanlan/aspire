package com.example.aspire.mini.entity;

import com.example.aspire.mini.enums.RepaymentStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "repayments")
public class Repayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private RepaymentStatus status;

    // Every repayment is associated with a loan and a scheduledRepayment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduled_repayment_id")
    private ScheduledRepayment scheduledRepayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private Loan loan;
}