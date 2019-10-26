package com.example.invoiceservice.dao;

import com.example.invoiceservice.dto.Invoice;
import com.example.invoiceservice.dto.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoJdbcTemplateImplTest {

    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    InvoiceItemDao invoiceItemDao;

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
    public void addGetDeleteInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,8,12));

        invoice = invoiceDao.addInvoice(invoice);

        Invoice invoice1 = invoiceDao.getInvoiceById(invoice.getInvoiceId());
        assertEquals(invoice,invoice1);

        invoiceDao.deleteInvoice(invoice.getInvoiceId());
        invoice1 = invoiceDao.getInvoiceById(invoice.getInvoiceId());
        assertNull(invoice1);
    }

    @Test
    public void getAllInvoices() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,8,12));

        invoiceDao.addInvoice(invoice);

        invoice = new Invoice();
        invoice.setCustomerId(2);
        invoice.setPurchaseDate(LocalDate.of(2019,12,12));

        invoiceDao.addInvoice(invoice);

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();

        assertEquals(invoiceList.size(),2);
    }

    @Test
    public void updateInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,8,12));

        invoice = invoiceDao.addInvoice(invoice);

        invoice.setCustomerId(2);
        invoice.setPurchaseDate(LocalDate.of(2019,12,12));

        invoiceDao.updateInvoice(invoice);

        Invoice invoice1 = invoiceDao.getInvoiceById(invoice.getInvoiceId());
        assertEquals(invoice,invoice1);
    }

    @Test
    public void getInvoicesByCustomerId(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,8,12));

        invoice = invoiceDao.addInvoice(invoice);

        List<Invoice> invoice1 = invoiceDao.getInvoiceByCustomerId(invoice.getCustomerId());

        assertEquals(invoice1.size(),1);
    }

}