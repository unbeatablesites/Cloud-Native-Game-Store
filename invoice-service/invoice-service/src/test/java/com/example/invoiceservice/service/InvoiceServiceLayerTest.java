package com.example.invoiceservice.service;

import com.example.invoiceservice.dao.InvoiceDao;
import com.example.invoiceservice.dao.InvoiceDaoJdbcTemplateImpl;
import com.example.invoiceservice.dao.InvoiceItemDao;
import com.example.invoiceservice.dao.InvoiceItemDaoJdbcTemplateImpl;
import com.example.invoiceservice.dto.Invoice;
import com.example.invoiceservice.dto.InvoiceItem;
import com.example.invoiceservice.view.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class InvoiceServiceLayerTest {

    InvoiceServiceLayer invoiceServiceLayer;
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception {
        setUpInvoiceDaoMock();
        setUpInvoiceItemDaoMock();
        invoiceServiceLayer = new InvoiceServiceLayer(invoiceDao,invoiceItemDao);
    }

    private void setUpInvoiceItemDaoMock(){
        invoiceItemDao = mock(InvoiceItemDaoJdbcTemplateImpl.class);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal("10.99"));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(1);
        invoiceItem1.setQuantity(10);
        invoiceItem1.setUnitPrice(new BigDecimal("10.99"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem);

        doReturn(invoiceItem).when(invoiceItemDao).addInvoiceItem(invoiceItem1);
        doReturn(invoiceItem).when(invoiceItemDao).getInvoiceItemById(1);
        doReturn(invoiceItemList).when(invoiceItemDao).getAllInvoiceItems();
        doReturn(invoiceItemList).when(invoiceItemDao).getInvoiceItemsByInvoiceId(1);
    }

    private void setUpInvoiceDaoMock(){
        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,1,1));

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2019,1,1));

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);

        doReturn(invoice).when(invoiceDao).addInvoice(invoice1);
        doReturn(invoice).when(invoiceDao).getInvoiceById(1);
        doReturn(invoiceList).when(invoiceDao).getAllInvoices();
        doReturn(invoiceList).when(invoiceDao).getInvoiceByCustomerId(1);
    }

    @Test
    public void saveFindFindAllInvoice() {
        InvoiceViewModel ivm = new InvoiceViewModel();

        ivm.setCustomerId(1);
        ivm.setPurchaseDate(LocalDate.of(2019,1,1));

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal("10.99"));
        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem);

        ivm.setInvoiceItemList(invoiceItemList);

        ivm = invoiceServiceLayer.saveInvoice(ivm);

        InvoiceViewModel fromService = invoiceServiceLayer.findInvoice(ivm.getInvoiceId());
        assertEquals(fromService,ivm);

        //testing find all
        List<InvoiceViewModel> fromService2 = invoiceServiceLayer.findAllInvoices();
        assertEquals(fromService2.size(),1);
        assertEquals(ivm,fromService2.get(0));
    }

    @Test
    public void getInvoiceByCustomerId(){
        InvoiceViewModel ivm = new InvoiceViewModel();

        ivm.setCustomerId(1);
        ivm.setPurchaseDate(LocalDate.of(2019,1,1));

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setQuantity(10);
        invoiceItem.setUnitPrice(new BigDecimal("10.99"));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem);

        ivm.setInvoiceItemList(invoiceItemList);

        ivm = invoiceServiceLayer.saveInvoice(ivm);

        List<InvoiceViewModel> invoiceViewModelList = new ArrayList<>();
        invoiceViewModelList.add(ivm);

        //testing find all
        List<InvoiceViewModel> fromService2 = invoiceServiceLayer.getInvoiceByCustomerId(1);
        assertEquals(fromService2.size(),1);
        assertEquals(ivm,fromService2.get(0));
    }

//    @Test
//    public void findInvoice() {
//    }
//
//    @Test
//    public void findAllInvoices() {
//    }
}