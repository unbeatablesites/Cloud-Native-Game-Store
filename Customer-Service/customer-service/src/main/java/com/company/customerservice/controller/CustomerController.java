package com.company.customerservice.controller;


import com.company.customerservice.service.CustomerServiceLayer;
import com.company.customerservice.view.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class CustomerController {

    //DI - servicelayer. Field Level
    @Autowired
    CustomerServiceLayer service;

    //add customer
    @RequestMapping(value = "/customer",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerViewModel saveCustomer(@RequestBody @Valid CustomerViewModel customerViewModel){
        //simply call the service and create the customer using the service layer method + customerViewModel
        return service.saveCustomer(customerViewModel);
    }

    //get All customers
    @RequestMapping(value = "/customer",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerViewModel> getAllCustomers(){
        return service.findAllCustomers();
    }

    //get customer by id
    @RequestMapping(value = "/customer/{id}",method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CustomerViewModel findCustomerById(@PathVariable("id") int id){
        //create a customer View Model using the find method from the service layer
        CustomerViewModel customerViewModel = service.findCustomer(id);

        //if it does not exist, through an illegal argument exception
        if(customerViewModel == null){
            //throw new
        }

        //return the requested customer View Model
        return customerViewModel;
    }

    //update customer by id
    @RequestMapping(value = "/customer/{id}",method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable("id") int id, @RequestBody @Valid CustomerViewModel customerViewModel){
        //check if the id matches the request body. Path variable and the
        //user passed data (customerId) must match to proceed.
        //so you need to input in both path variable and the requestbody
        if(id != customerViewModel.getCustomerId()){
            //throw illegal arguement
        }

        //if it does match, update that customer
        service.updateCustomer(customerViewModel);
    }

    //delete customer by id
    @RequestMapping(value = "/customer/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCustomer(@PathVariable("id") int id){
        //simply remove the customer using the id. If it does not exist, nothing will happen anyways
        service.removeCustomer(id);
    }

}
