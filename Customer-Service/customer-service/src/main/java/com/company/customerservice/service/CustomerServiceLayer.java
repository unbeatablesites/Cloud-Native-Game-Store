package com.company.customerservice.service;

import com.company.customerservice.dao.CustomerDao;
import com.company.customerservice.dto.Customer;
import com.company.customerservice.view.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerServiceLayer {

    //dependency injection of CustomerDao
    private CustomerDao customerDao;
    @Autowired
    public CustomerServiceLayer(CustomerDao customerDao){
        this.customerDao=customerDao;
    }

    //save customer
    public CustomerViewModel saveCustomer(CustomerViewModel customerViewModel){
        //set up a new Customer Object and assign customer's properties from customerViewModel
        Customer customer = new Customer();
        customer.setFirstName(customerViewModel.getFirstName());
        customer.setLastName(customerViewModel.getLastName());
        customer.setStreet(customerViewModel.getStreet());
        customer.setCity(customerViewModel.getCity());
        customer.setZip(customerViewModel.getZip());
        customer.setEmail(customerViewModel.getEmail());
        customer.setPhone(customerViewModel.getPhone());

        //using DAO, add the Customer.
        customer = customerDao.addCustomer(customer);

        //set the customer ID on the customerViewModel using the Customer object made just now.
        customerViewModel.setCustomerId(customer.getCustomerId());

        //return the customerViewModel with the ID set ready to go.
        return customerViewModel;
    }

    //find customer by id
    public CustomerViewModel findCustomer(int id){
        //create the Customer Object with the id parameter
        Customer customer = customerDao.getCustomerById(id);

        //Check if the customer object by that ID exists,
        //if yes, build the view model using the helper method, with the customer object
        //if not, return null
        if(customer == null){
            return null;
        } else {
            return buildCustomerViewModel(customer);
        }
    }

    //find all customers
    @Transactional
    public List<CustomerViewModel> findAllCustomers(){
        List<Customer> customerList = customerDao.getAllCustomers();
        List<CustomerViewModel> cvmList = new ArrayList<>();

        customerList.stream()
                .forEach(
                        customer -> {
                            CustomerViewModel cvm = buildCustomerViewModel(customer);
                            cvmList.add(cvm);
                        }
                );
        return cvmList;
    }

    //update customer - this will not be tested in the mock
    public void updateCustomer(CustomerViewModel customerViewModel){
        //create a new Customer Object
        Customer customer = new Customer();

        //set the customer object including the customer ID
        customer.setCustomerId(customerViewModel.getCustomerId());
        customer.setFirstName(customerViewModel.getFirstName());
        customer.setLastName(customerViewModel.getLastName());
        customer.setStreet(customerViewModel.getStreet());
        customer.setCity(customerViewModel.getCity());
        customer.setZip(customerViewModel.getZip());
        customer.setEmail(customerViewModel.getEmail());
        customer.setPhone(customerViewModel.getPhone());

        //update the customer using the DAO. for update it is basically the same as DAO one
        customerDao.updateCustomer(customer);

    }

    //delete customer - this will not be tested in the mock
    public void removeCustomer(int id){
        //directly delete the customer object using the DAO method
        customerDao.deleteCustomer(id);
    }

    //Helper method. setting the view models conveniently
    private CustomerViewModel buildCustomerViewModel(Customer customer){
        //Create the new customer view model and set the view model with the properties from customer
        CustomerViewModel customerViewModel = new CustomerViewModel();

        //you must set all properties including the customer ID
        customerViewModel.setCustomerId(customer.getCustomerId());
        customerViewModel.setFirstName(customer.getFirstName());
        customerViewModel.setLastName(customer.getLastName());
        customerViewModel.setStreet(customer.getStreet());
        customerViewModel.setCity(customer.getCity());
        customerViewModel.setZip(customer.getZip());
        customerViewModel.setEmail(customer.getEmail());
        customerViewModel.setPhone(customer.getPhone());
        return customerViewModel;
    }
}
