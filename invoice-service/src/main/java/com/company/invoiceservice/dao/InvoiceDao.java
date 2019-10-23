package com.company.invoiceservice.dao;

import com.company.invoiceservice.dto.Invoice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InvoiceDao {
    @Transactional
    Invoice addInvoice(Invoice invoice);

    Invoice getInvoiceById(int id);

    List<Invoice> getAllInvoices();

    Invoice updateInvoice(Invoice invoice);

    void deleteInvoice(int id);

    List<Invoice> getInvoiceByCustomerId(int id);
}
