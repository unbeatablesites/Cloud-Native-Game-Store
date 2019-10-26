package com.company.customerservice.dao;

import com.company.customerservice.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerDaoJdbcTemplateImpl implements CustomerDao{

    //dependency injection - Constructor level
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public CustomerDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    //prepared statements
    private static final String INSERT_CUSTOMER =
            "insert into customer (first_name,last_name,street,city,zip,email,phone) values (?,?,?,?,?,?,?)";
    private static final String SELECT_CUSTOMER =
            "select * from customer where customer_id = ?";
    private static final String SELECT_ALL_CUSTOMERS =
            "select * from customer";
    private static final String UPDATE_CUSTOMER =
            "update customer set first_name=?, last_name=?, street=?, city=?, zip=?, email=?, phone=? where customer_id=?";
    private static final String DELETE_CUSTOMER =
            "delete from customer where customer_id = ?";

    //row mapper function
    public Customer mapRowToCustomer(ResultSet rs, int rowNum) throws SQLException{
        Customer c = new Customer();
        c.setCustomerId(rs.getInt("customer_id"));
        c.setFirstName(rs.getString("first_name"));
        c.setLastName(rs.getString("last_name"));
        c.setStreet(rs.getString("street"));
        c.setCity(rs.getString("city"));
        c.setZip(rs.getString("zip"));
        c.setEmail(rs.getString("email"));
        c.setPhone(rs.getString("phone"));
        return c;
    }

    @Override
    @Transactional
    public Customer addCustomer(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getZip(),
                customer.getEmail(),
                customer.getPhone()
                );
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        customer.setCustomerId(id);
        return customer;
    }

    @Override
    public Customer getCustomerById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CUSTOMER, this::mapRowToCustomer, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS,this::mapRowToCustomer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        jdbcTemplate.update(UPDATE_CUSTOMER,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getZip(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCustomerId()
                );
        return customer;
    }

    @Override
    public void deleteCustomer(int id) {
        jdbcTemplate.update(DELETE_CUSTOMER,id);
    }
}
