package com.example.invoiceservice.dao;

import com.example.invoiceservice.dto.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceItemDaoJdbcTemplateImpl implements InvoiceItemDao{

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public InvoiceItemDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }


    //prepared statements
    private static final String INSERT_INVOICE_ITEM =
            "insert into invoice_item (invoice_id,inventory_id,quantity,unit_price) values (?,?,?,?)";
    private static final String SELECT_INVOICE_ITEM =
            "select * from invoice_item where invoice_item_id = ?";
    private static final String SELECT_ALL_INVOICE_ITEMS =
            "select * from invoice_item";
    private static final String UPDATE_INVOICE_ITEM =
            "update invoice_item set invoice_id=?, inventory_id=?, quantity=?, unit_price=? where invoice_item_id=?";
    private static final String DELETE_INVOICE_ITEM =
            "delete from invoice_item where invoice_item_id = ?";
    private static final String SELECT_INVOICE_ITEMS_BY_INVOICE_ID =
            "select * from invoice_item where invoice_id = ?";

    //row mapper
    public InvoiceItem mapRowToInvoiceItem(ResultSet rs, int rowNum) throws SQLException{
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(rs.getInt("invoice_item_id"));
        invoiceItem.setInvoiceId(rs.getInt("invoice_id"));
        invoiceItem.setInventoryId(rs.getInt("inventory_id"));
        invoiceItem.setQuantity(rs.getInt("quantity"));
        invoiceItem.setUnitPrice(rs.getBigDecimal("unit_price"));
        return invoiceItem;
    }

    @Override
    @Transactional
    public InvoiceItem addInvoiceItem(InvoiceItem invoiceItem) {
        jdbcTemplate.update(
                INSERT_INVOICE_ITEM,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice()
        );
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        invoiceItem.setInvoiceItemId(id);
        return invoiceItem;
    }

    @Override
    public InvoiceItem getInvoiceItemById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_INVOICE_ITEM, this::mapRowToInvoiceItem, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<InvoiceItem> getAllInvoiceItems() {
        return jdbcTemplate.query(SELECT_ALL_INVOICE_ITEMS,this::mapRowToInvoiceItem);
    }

    @Override
    public InvoiceItem updateInvoiceItem(InvoiceItem invoiceItem) {
        jdbcTemplate.update(
                UPDATE_INVOICE_ITEM,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice(),
                invoiceItem.getInvoiceItemId()
        );
        return invoiceItem;
    }

    @Override
    public void deleteInvoiceItem(int id) {
        jdbcTemplate.update(DELETE_INVOICE_ITEM,id);
    }

    @Override
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(int id){
        return jdbcTemplate.query(SELECT_INVOICE_ITEMS_BY_INVOICE_ID,
                this::mapRowToInvoiceItem,id);
    }
}
