package com.company.CustomerService.controller;


import com.company.CustomerService.dao.CustomerDao;
import com.company.CustomerService.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/customers")
@CacheConfig(cacheNames = {"customers"})
public class Controller {
    @Autowired
    CustomerDao customerDao;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Valid Customer customer){
        return null;
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable int customerId){
        return null;
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return null;
    }

    @PutMapping
    public void updateCustomer(@RequestBody Customer customer){
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable int customerId){

    }
}