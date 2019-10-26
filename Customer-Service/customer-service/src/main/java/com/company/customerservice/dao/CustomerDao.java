package com.company.customerservice.dao;

import com.company.customerservice.dto.Customer;

import java.util.List;

public interface CustomerDao {

    //regular CRUD Functionality
    Customer addCustomer(Customer customer);

    Customer getCustomerById(int id);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Customer customer);

    void deleteCustomer(int id);
}
