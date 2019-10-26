package com.company.customerservice.dao;

import com.company.customerservice.dto.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerDaoJdbcTemplateImplTest {

    @Autowired
    CustomerDao customerDao;

    //for the testing purposes, you are clearing the database.
    @Before
    public void setUp() throws Exception {
        List<Customer> customers = customerDao.getAllCustomers();
        for(Customer customer : customers){
            customerDao.deleteCustomer(customer.getCustomerId());
        }
    }

    @Test
    public void addGetDeleteCustomer() {
        //you set up the dummy customer object without ID
        Customer customer = new Customer();
        customer.setFirstName("Lebron");
        customer.setLastName("James");
        customer.setStreet("Gentry Drive");
        customer.setCity("Akron");
        customer.setZip("11111");
        customer.setEmail("LBJ@gmail.com");
        customer.setPhone("201-666-6666");

        //add the customer in the DB with DAO
        customer = customerDao.addCustomer(customer);

        //Create a new Customer Object and assign it using DAO
        Customer customer1 = customerDao.getCustomerById(customer.getCustomerId());

        //check if they are equal
        assertEquals(customer,customer1);

        //delete the original customer
        //assign the customer1 using DAO
        //check for null
        customerDao.deleteCustomer(customer.getCustomerId());
        customer1 = customerDao.getCustomerById(customer.getCustomerId());
        assertNull(customer1);
    }

    @Test
    public void getAllCustomers() {
        //you set up the dummy customer object without ID
        Customer customer = new Customer();
        customer.setFirstName("Lebron");
        customer.setLastName("James");
        customer.setStreet("Gentry Drive");
        customer.setCity("Akron");
        customer.setZip("11111");
        customer.setEmail("LBJ@gmail.com");
        customer.setPhone("201-666-6666");

        //add the first customer
        customerDao.addCustomer(customer);

        //renew the customer with new Customer(); and create the second customer
        customer = new Customer();
        customer.setFirstName("Kobe");
        customer.setLastName("Bryant");
        customer.setStreet("HollyWood Drive");
        customer.setCity("LA");
        customer.setZip("22222");
        customer.setEmail("kobe@gmail.com");
        customer.setPhone("201-333-3333");

        //add the 2nd customer
        customerDao.addCustomer(customer);

        //put all of the in the List<Customer>
        List<Customer> customerList = customerDao.getAllCustomers();

        //check the size. should be 2, since I've added 2.
        assertEquals(customerList.size(),2);
    }

    @Test
    public void updateCustomer() {
        //you set up the dummy customer object without ID
        Customer customer = new Customer();
        customer.setFirstName("Lebron");
        customer.setLastName("James");
        customer.setStreet("Gentry Drive");
        customer.setCity("Akron");
        customer.setZip("11111");
        customer.setEmail("LBJ@gmail.com");
        customer.setPhone("201-666-6666");

        //create the first customer
        customer = customerDao.addCustomer(customer);

        //set that same customer with new values, "updating"
        customer.setFirstName("Kobe");
        customer.setLastName("Bryant");
        customer.setStreet("HollyWood Drive");
        customer.setCity("LA");
        customer.setZip("22222");
        customer.setEmail("kobe@gmail.com");
        customer.setPhone("201-333-3333");

        //update the customer using DAO
        customerDao.updateCustomer(customer);

        //create a new customer using get method
        //check if they are equal
        Customer customer1 = customerDao.getCustomerById(customer.getCustomerId());
        assertEquals(customer,customer1);
    }

}