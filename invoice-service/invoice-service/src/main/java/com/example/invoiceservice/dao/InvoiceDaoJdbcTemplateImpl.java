package com.example.invoiceservice.dao;

import com.example.invoiceservice.dto.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao{

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    //prepared statements
    private static final String INSERT_INVOICE =
            "insert into invoice (customer_id,purchase_date) values (?,?)";
    private static final String SELECT_INVOICE =
            "select * from invoice where invoice_id = ?";
    private static final String SELECT_ALL_INVOICES =
            "select * from invoice";
    private static final String UPDATE_INVOICE =
            "update invoice set customer_id=?, purchase_date=? where invoice_id=?";
    private static final String DELETE_INVOICE =
            "delete from invoice where invoice_id = ?";
    private static final String SELECT_INVOICES_BY_CUSTOMER_ID=
            "select * from invoice where customer_id=?";

    //row mapper function
    public Invoice mapRowToInvoice(ResultSet rs, int rowNum)throws SQLException{
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setCustomerId(rs.getInt("customer_id"));
        invoice.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());
        return invoice;
    }

    @Override
    @Transactional
    public Invoice addInvoice(Invoice invoice) {
        jdbcTemplate.update(
                INSERT_INVOICE,
                invoice.getCustomerId(),
                invoice.getPurchaseDate()
        );
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        invoice.setInvoiceId(id);
        return invoice;
    }

    @Override
    public Invoice getInvoiceById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_INVOICE, this::mapRowToInvoice, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return jdbcTemplate.query(SELECT_ALL_INVOICES,this::mapRowToInvoice);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        jdbcTemplate.update(
                UPDATE_INVOICE,
                invoice.getCustomerId(),
                invoice.getPurchaseDate(),
                invoice.getInvoiceId()
        );
        return invoice;
    }

    @Override
    public void deleteInvoice(int id) {
        jdbcTemplate.update(DELETE_INVOICE,id);
    }

    @Override
    public List<Invoice> getInvoiceByCustomerId(int id){
        return jdbcTemplate.query(SELECT_INVOICES_BY_CUSTOMER_ID,this::mapRowToInvoice,id);
    }
}
