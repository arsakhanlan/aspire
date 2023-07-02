package com.example.aspire.mini.entity;

import com.example.aspire.mini.enums.LoanStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private int term;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    // Loan has a many-to-one mapping with customer table
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

}
