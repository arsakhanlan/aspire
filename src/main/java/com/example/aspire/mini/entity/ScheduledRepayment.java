package com.example.aspire.mini.entity;

import com.example.aspire.mini.enums.RepaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "scheduled_repayments")
@Getter
@Setter
public class ScheduledRepayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private LocalDate dueDate;

    // Every scheduledRepayment is associated with a loan
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Enumerated(EnumType.STRING)
    private RepaymentStatus status;
}