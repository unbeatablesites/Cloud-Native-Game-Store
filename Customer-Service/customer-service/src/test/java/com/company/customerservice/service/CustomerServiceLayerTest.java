package com.company.customerservice.service;

import com.company.customerservice.dao.CustomerDao;
import com.company.customerservice.dao.CustomerDaoJdbcTemplateImpl;
import com.company.customerservice.dto.Customer;
import com.company.customerservice.view.CustomerViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class CustomerServiceLayerTest {

    //Declaring the DAO and the service layer
    //no need to autowire
    CustomerServiceLayer customerServiceLayer;
    CustomerDao customerDao;

    //set up the mocks here
    @Before
    public void setUp() throws Exception {
        setUpCustomerDaoMock();
        //in the service layer, I have made the constructor to
        //have the DAO interface being passed
        customerServiceLayer = new CustomerServiceLayer(customerDao);
    }

    //Setting up the Customer DAO mock here
    private void setUpCustomerDaoMock(){
        //mocking the customer DAO
        customerDao = mock(CustomerDaoJdbcTemplateImpl.class);

        //Set up the mock Customer Object
        //this customer is the expected value. So it includes the ID
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFirstName("Lebron");
        customer.setLastName("James");
        customer.setStreet("Gentry Drive");
        customer.setCity("Akron");
        customer.setZip("11111");
        customer.setEmail("LBJ@gmail.com");
        customer.setPhone("201-666-6666");

        //this customer1 is what is being inputted or "Requested"
        //thus, this does not include the ID
        Customer customer1 = new Customer();
        customer1.setFirstName("Lebron");
        customer1.setLastName("James");
        customer1.setStreet("Gentry Drive");
        customer1.setCity("Akron");
        customer1.setZip("11111");
        customer1.setEmail("LBJ@gmail.com");
        customer1.setPhone("201-666-6666");

        //adding the customer in the List to mock the getAllCustomers but
        //may be uncessary
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        //mocking the returns
        //return customer when customer1 is being added/requested
        doReturn(customer).when(customerDao).addCustomer(customer1);
        doReturn(customer).when(customerDao).getCustomerById(1);
        doReturn(customerList).when(customerDao).getAllCustomers();
    }

    @Test
    public void saveFindCustomer() {
        //create a View model
        CustomerViewModel customerViewModel = new CustomerViewModel();
        customerViewModel.setCustomerId(1);
        customerViewModel.setFirstName("Lebron");
        customerViewModel.setLastName("James");
        customerViewModel.setStreet("Gentry Drive");
        customerViewModel.setCity("Akron");
        customerViewModel.setZip("11111");
        customerViewModel.setEmail("LBJ@gmail.com");
        customerViewModel.setPhone("201-666-6666");

        //using the service layer, create a new customerViewModel
        CustomerViewModel fromService = customerServiceLayer.findCustomer(customerViewModel.getCustomerId());

        //check if what you have setup and fromService is matching
        assertEquals(customerViewModel,fromService);

    }

    @Test
    public void findAllCustomers(){
        CustomerViewModel customerViewModel = new CustomerViewModel();
        customerViewModel.setCustomerId(1);
        customerViewModel.setFirstName("Lebron");
        customerViewModel.setLastName("James");
        customerViewModel.setStreet("Gentry Drive");
        customerViewModel.setCity("Akron");
        customerViewModel.setZip("11111");
        customerViewModel.setEmail("LBJ@gmail.com");
        customerViewModel.setPhone("201-666-6666");

        List<CustomerViewModel> fromService = customerServiceLayer.findAllCustomers();
        assertEquals(fromService.size(),1);
        assertEquals(customerViewModel,fromService.get(0));
    }
}