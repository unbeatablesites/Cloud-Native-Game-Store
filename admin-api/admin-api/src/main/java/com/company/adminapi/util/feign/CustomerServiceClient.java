package com.company.adminapi.util.feign;

import com.company.adminapi.dto.Customer;
import com.company.adminapi.view.CustomerViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    //add customer
    @RequestMapping(value = "/customer",method= RequestMethod.POST)
    public CustomerViewModel saveCustomer(@RequestBody @Valid CustomerViewModel customerViewModel);

    //get All customers
    @RequestMapping(value = "/customer",method= RequestMethod.GET)
    public List<CustomerViewModel> getAllCustomers();

    //get customer by id
    @RequestMapping(value = "/customer/{id}",method=RequestMethod.GET)
    public CustomerViewModel findCustomerById(@PathVariable("id") int id);

    //update customer by id
    @RequestMapping(value = "/customer/{id}",method=RequestMethod.PUT)
    public void updateCustomer(@PathVariable("id") int id, @RequestBody @Valid CustomerViewModel customerViewModel);

    //delete customer by id
    @RequestMapping(value = "/customer/{id}",method=RequestMethod.DELETE)
    public void removeCustomer(@PathVariable("id") int id);
}
