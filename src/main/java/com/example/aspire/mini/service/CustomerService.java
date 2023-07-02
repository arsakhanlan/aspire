package com.example.aspire.mini.service;

import com.example.aspire.mini.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Long id);
    Customer createCustomer(Customer customer);
    Optional<Customer> updateCustomer(Long id, Customer customer);
    boolean deleteCustomer(Long id);
}
