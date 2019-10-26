package com.company.retailapi.util.feign;

import com.company.retailapi.view.CustomerViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
