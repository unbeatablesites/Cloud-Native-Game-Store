package com.example.invoiceservice.dao;

import com.example.invoiceservice.dto.Invoice;
import com.example.invoiceservice.dto.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemDaoJdbcTemplateImplTest {

    @Autowired
    InvoiceItemDao invoiceItemDao;
    @Autowired
    InvoiceDao invoiceDao;


    @Before
    public void setUp() throws Exception {
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();
        for(InvoiceItem invoiceItem : invoiceItemList){
            invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());
        }

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        for(Invoice invoice : invoiceList){
            invoiceDao.deleteInvoice(invoice.getInvoiceId());
        }
    }

    @Test
    public void addGetDeleteInvoiceItem() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,8,12));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal("10.99"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        InvoiceItem invoiceItem1 = invoiceItemDao.getInvoiceItemById(invoiceItem.getInvoiceItemId());

        assertEquals(invoiceItem,invoiceItem1);


        invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());
        invoiceItem1 = invoiceItemDao.getInvoiceItemById(invoiceItem.getInvoiceItemId());
        assertNull(invoiceItem1);
    }

    @Test
    public void getAllInvoiceItems() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,8,12));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal("10.99"));

        invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(2);
        invoiceItem.setQuantity(5);
        invoiceItem.setUnitPrice(new BigDecimal("20.99"));

        invoiceItemDao.addInvoiceItem(invoiceItem);

        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();

        assertEquals(invoiceItemList.size(),2);
    }

    @Test
    public void updateInvoiceItem() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,8,12));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal("10.99"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItem.setInventoryId(2);
        invoiceItem.setQuantity(5);
        invoiceItem.setUnitPrice(new BigDecimal("20.99"));

        invoiceItemDao.updateInvoiceItem(invoiceItem);

        InvoiceItem invoiceItem1 = invoiceItemDao.getInvoiceItemById(invoiceItem.getInvoiceItemId());
        assertEquals(invoiceItem,invoiceItem1);
    }

    @Test
    public void getInvoicesItemByInvoiceId(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,8,12));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal("10.99"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(2);
        invoiceItem.setQuantity(5);
        invoiceItem.setUnitPrice(new BigDecimal("20.99"));

        invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);

        List<InvoiceItem> invoiceItemList = invoiceItemDao.getInvoiceItemsByInvoiceId(invoice.getInvoiceId());
        assertEquals(invoiceItemList.size(),2);
    }

}