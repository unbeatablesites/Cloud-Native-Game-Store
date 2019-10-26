package com.example.invoiceservice.dao;

import com.example.invoiceservice.dto.Invoice;

import java.util.List;

public interface InvoiceDao {

    //CRUD + Get all
    Invoice addInvoice(Invoice invoice);

    Invoice getInvoiceById(int id);

    List<Invoice> getAllInvoices();

    Invoice updateInvoice(Invoice invoice);

    void deleteInvoice(int id);

    List<Invoice> getInvoiceByCustomerId(int id);
}
