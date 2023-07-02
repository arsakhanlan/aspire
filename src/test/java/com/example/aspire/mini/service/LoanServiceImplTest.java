package com.example.aspire.mini.service;

import com.example.aspire.mini.bean.LoanRequest;
import com.example.aspire.mini.bean.RepaymentRequest;
import com.example.aspire.mini.entity.Customer;
import com.example.aspire.mini.entity.Loan;
import com.example.aspire.mini.entity.ScheduledRepayment;
import com.example.aspire.mini.enums.LoanStatus;
import com.example.aspire.mini.enums.RepaymentStatus;
import com.example.aspire.mini.exception.ScheduledRepaymentNotFoundException;
import com.example.aspire.mini.repository.CustomerRepository;
import com.example.aspire.mini.repository.LoanRepository;
import com.example.aspire.mini.repository.RepaymentRepository;
import com.example.aspire.mini.repository.ScheduledRepaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {LoanServiceImpl.class})
@ExtendWith(SpringExtension.class)
class LoanServiceImplTest {
    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private LoanRepository loanRepository;

    @Autowired
    private LoanServiceImpl loanServiceImpl;

    @MockBean
    private RepaymentRepository repaymentRepository;

    @MockBean
    private ScheduledRepaymentRepository scheduledRepaymentRepository;

    @Test
    void testCreateLoan() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Name");

        Loan loan = new Loan();
        loan.setAmount(BigDecimal.valueOf(1L));
        loan.setCustomer(customer);
        loan.setId(1L);
        loan.setStatus(LoanStatus.PENDING);
        loan.setTerm(1);
        when(loanRepository.save(Mockito.any())).thenReturn(loan);

        Customer customer2 = new Customer();
        customer2.setId(1L);
        customer2.setName("Name");

        Loan loan2 = new Loan();
        loan2.setAmount(BigDecimal.valueOf(1L));
        loan2.setCustomer(customer2);
        loan2.setId(1L);
        loan2.setStatus(LoanStatus.PENDING);
        loan2.setTerm(1);

        ScheduledRepayment scheduledRepayment = new ScheduledRepayment();
        scheduledRepayment.setAmount(BigDecimal.valueOf(1L));
        scheduledRepayment.setDueDate(LocalDate.of(1970, 1, 1));
        scheduledRepayment.setId(1L);
        scheduledRepayment.setLoan(loan2);
        scheduledRepayment.setStatus(RepaymentStatus.PENDING);
        when(scheduledRepaymentRepository.save(Mockito.any())).thenReturn(scheduledRepayment);

        Customer customer3 = new Customer();
        customer3.setId(1L);
        customer3.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer3);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setAmount(BigDecimal.valueOf(1L));
        loanRequest.setCustomerId(1L);
        loanRequest.setTerm(1);
        Loan actualCreateLoanResult = loanServiceImpl.createLoan(loanRequest);
        assertSame(loan, actualCreateLoanResult);
        assertEquals("1", actualCreateLoanResult.getAmount().toString());
        verify(loanRepository).save(Mockito.<Loan>any());
        verify(scheduledRepaymentRepository).save(Mockito.<ScheduledRepayment>any());
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetAllLoansByCustomerId() {
        ArrayList<Loan> loanList = new ArrayList<>();
        when(loanRepository.findByCustomer(Mockito.<Customer>any())).thenReturn(loanList);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        List<Loan> actualAllLoansByCustomerId = loanServiceImpl.getAllLoansByCustomerId(1L);
        assertSame(loanList, actualAllLoansByCustomerId);
        assertTrue(actualAllLoansByCustomerId.isEmpty());
        verify(loanRepository).findByCustomer(Mockito.any());
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testAddRepayment() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Name");

        Loan loan = new Loan();
        loan.setAmount(BigDecimal.valueOf(1L));
        loan.setCustomer(customer);
        loan.setId(1L);
        loan.setStatus(LoanStatus.PENDING);
        loan.setTerm(1);
        Optional<Loan> ofResult = Optional.of(loan);
        when(loanRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(scheduledRepaymentRepository.findByLoanIdAndStatus(Mockito.<Long>any(), Mockito.<RepaymentStatus>any()))
                .thenReturn(new ArrayList<>());

        RepaymentRequest repaymentRequest = new RepaymentRequest();
        repaymentRequest.setAmount(BigDecimal.valueOf(1L));
        repaymentRequest.setLoanId(1L);
        assertThrows(ScheduledRepaymentNotFoundException.class, () -> loanServiceImpl.addRepayment(repaymentRequest));
        verify(loanRepository).findById(Mockito.<Long>any());
        verify(scheduledRepaymentRepository).findByLoanIdAndStatus(Mockito.<Long>any(), Mockito.<RepaymentStatus>any());
    }

    @Test
    void testGetLoanById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Name");

        Loan loan = new Loan();
        loan.setAmount(BigDecimal.valueOf(1L));
        loan.setCustomer(customer);
        loan.setId(1L);
        loan.setStatus(LoanStatus.PENDING);
        loan.setTerm(1);
        Optional<Loan> ofResult = Optional.of(loan);
        when(loanRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Loan actualLoanById = loanServiceImpl.getLoanById(1L);
        assertSame(loan, actualLoanById);
        assertEquals("1", actualLoanById.getAmount().toString());
        verify(loanRepository).findById(Mockito.<Long>any());
    }
}

