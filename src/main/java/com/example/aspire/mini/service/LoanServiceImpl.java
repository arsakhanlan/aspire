package com.example.aspire.mini.service;

import com.example.aspire.mini.bean.LoanRequest;
import com.example.aspire.mini.bean.RepaymentRequest;
import com.example.aspire.mini.entity.Customer;
import com.example.aspire.mini.entity.Loan;
import com.example.aspire.mini.entity.Repayment;
import com.example.aspire.mini.entity.ScheduledRepayment;
import com.example.aspire.mini.enums.LoanStatus;
import com.example.aspire.mini.enums.RepaymentStatus;
import com.example.aspire.mini.exception.CustomerNotFoundException;
import com.example.aspire.mini.exception.LoanNotFoundException;
import com.example.aspire.mini.exception.ScheduledRepaymentNotFoundException;
import com.example.aspire.mini.repository.CustomerRepository;
import com.example.aspire.mini.repository.LoanRepository;
import com.example.aspire.mini.repository.RepaymentRepository;
import com.example.aspire.mini.repository.ScheduledRepaymentRepository;
import com.example.aspire.mini.utils.CashRepaymentStrategy;
import com.example.aspire.mini.utils.LoanApprovalManager;
import com.example.aspire.mini.utils.RepaymentStrategy;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;

    private final ScheduledRepaymentRepository scheduledRepaymentRepository;

    private final CustomerRepository customerRepository;

    private final RepaymentRepository repaymentRepository;

    public LoanServiceImpl(LoanRepository loanRepository, ScheduledRepaymentRepository scheduledRepaymentRepository, CustomerRepository customerRepository, RepaymentRepository repaymentRepository) {
        this.loanRepository = loanRepository;
        this.scheduledRepaymentRepository = scheduledRepaymentRepository;
        this.customerRepository = customerRepository;
        this.repaymentRepository = repaymentRepository;
    }

    @Override
    public Loan createLoan(LoanRequest loanRequest) {
        Optional<Customer> customer = customerRepository.findById(loanRequest.getCustomerId());
        if(customer.isEmpty()){
            throw new CustomerNotFoundException("Customer not found with ID: " + loanRequest.getCustomerId());
        }
        Customer customerLoan = customer.get();
        Loan loan = new Loan();
        loan.setAmount(loanRequest.getAmount());
        loan.setTerm(loanRequest.getTerm());
        loan.setCustomer(customerLoan);
        loan.setStatus(LoanStatus.PENDING);
        Loan loanSaved = loanRepository.save(loan);
        generateScheduledRepayments(loan);
        return loanSaved;
    }

    @Override
    @SneakyThrows
    public Loan approveLoan(Long loanId) {
        LoanApprovalManager loanApprovalManager = LoanApprovalManager.getInstance(loanRepository);
        return loanApprovalManager.approveLoan(loanId);
    }

    @Override
    public List<Loan> getAllLoansByCustomerId(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()){
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }
        return loanRepository.findByCustomer(customer.get());
    }

    @SneakyThrows
    @Override
    public Repayment addRepayment(RepaymentRequest repaymentRequest) {
        Long loanId = repaymentRequest.getLoanId();
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if(loanOptional.isEmpty()){
            throw new LoanNotFoundException("Loan not found with ID: " + loanId);
        }
        Loan loan = loanOptional.get();
        List<ScheduledRepayment> scheduledRepayments = scheduledRepaymentRepository.findByLoanIdAndStatus(loanId, RepaymentStatus.PENDING);
        if (scheduledRepayments == null || scheduledRepayments.size() == 0){
            throw new ScheduledRepaymentNotFoundException("Pending Scheduled Repayment not found for loanID: " + loanId);
        }
        ScheduledRepayment scheduledRepayment = scheduledRepayments.get(0);
        if(repaymentRequest.getAmount().compareTo(scheduledRepayment.getAmount()) >= 0){
            RepaymentStrategy repaymentStrategy = new CashRepaymentStrategy();
            Repayment repayment = repaymentStrategy.addRepayment(loan, repaymentRequest, scheduledRepayment);
            if(repayment == null){
                throw new RuntimeException("Error while processing repayment");
            }
            repayment.setStatus(RepaymentStatus.PAID);
            repaymentRepository.save(repayment);
            BigDecimal excessAmount = repaymentRequest.getAmount().subtract(scheduledRepayment.getAmount());
            System.out.println("You're getting a refund of " + excessAmount + " on Scheduled Repayment Id: " + scheduledRepayment.getId());
            scheduledRepayment.setStatus(RepaymentStatus.PAID);
            scheduledRepaymentRepository.save(scheduledRepayment);
            boolean allRepaymentsPaid = scheduledRepaymentRepository.countByLoanIdAndStatus(loanId, RepaymentStatus.PENDING) == 0;
            if (allRepaymentsPaid) {
                loan.setStatus(LoanStatus.PAID);
                loanRepository.save(loan);
            }
            return repayment;
        }
        else{
            return null;
        }
    }

    @Override
    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId).get();
    }

    private void generateScheduledRepayments(Loan loan) {
        int term = loan.getTerm();
        BigDecimal repaymentAmount = loan.getAmount().divide(BigDecimal.valueOf(term), 2, RoundingMode.HALF_UP);
        LocalDate dueDate = LocalDate.now().plusDays(7);
        for(int i = 0; i < term; i++){
            ScheduledRepayment scheduledRepayment = new ScheduledRepayment();
            scheduledRepayment.setLoan(loan);
            scheduledRepayment.setStatus(RepaymentStatus.PENDING);
            scheduledRepayment.setAmount(repaymentAmount);
            scheduledRepayment.setDueDate(dueDate);
            scheduledRepaymentRepository.save(scheduledRepayment);
            dueDate = dueDate.plusDays(7);
        }
    }

}
