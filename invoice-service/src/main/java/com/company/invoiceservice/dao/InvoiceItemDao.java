package com.company.invoiceservice.dao;

import com.company.invoiceservice.dto.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {

    //CRUD + Get all
    InvoiceItem addInvoiceItem(InvoiceItem invoiceItem);

    InvoiceItem getInvoiceItemById(int id);

    List<InvoiceItem> getAllInvoiceItems();

    InvoiceItem updateInvoiceItem(InvoiceItem invoiceItem);

    void deleteInvoiceItem(int id);

    List<InvoiceItem> getInvoiceItemsByInvoiceId(int id);

}
