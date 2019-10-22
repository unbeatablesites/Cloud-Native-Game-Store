package com.company.CustomerService.dao;

import com.company.CustomerService.model.Customer;

import java.util.List;

public class CustomerDao {

    Customer addCustomer(Customer customer);

    Customer getCustomer(int customerId);

    void deleteCustomer(int customerId);

    void updateCustomer(Customer customer);

    List<Customer> getAllCustomer();
}
