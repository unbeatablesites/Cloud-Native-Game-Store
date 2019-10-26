package com.example.invoiceservice.service;

import com.example.invoiceservice.dao.InvoiceDao;
import com.example.invoiceservice.dao.InvoiceItemDao;
import com.example.invoiceservice.dto.Invoice;
import com.example.invoiceservice.dto.InvoiceItem;
import com.example.invoiceservice.view.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceServiceLayer {

    private InvoiceDao invoiceDao;
    private InvoiceItemDao invoiceItemDao;

    @Autowired
    public InvoiceServiceLayer(InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao){
        this.invoiceDao=invoiceDao;
        this.invoiceItemDao=invoiceItemDao;
    }

    //save invoice
    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel invoiceViewModel){
        //persist invoice
        Invoice invoice = new Invoice();
        invoice.setCustomerId(invoiceViewModel.getCustomerId());
        invoice.setPurchaseDate(invoiceViewModel.getPurchaseDate());
        invoice = invoiceDao.addInvoice(invoice);

        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());

        //add invoice Id to invoice Items and persist InvoiceItemList
        List<InvoiceItem> invoiceItemList = invoiceViewModel.getInvoiceItemList();

        invoiceItemList.stream()
                .forEach(
                        invoiceItem -> {
                            invoiceItem.setInvoiceId(invoiceViewModel.getInvoiceId());
                            invoiceItemDao.addInvoiceItem(invoiceItem);
                        }
                );

        invoiceItemList = invoiceItemDao.getInvoiceItemsByInvoiceId(invoiceViewModel.getInvoiceId());
        invoiceViewModel.setInvoiceItemList(invoiceItemList);

        return invoiceViewModel;
    }

    //find Invoice
    public InvoiceViewModel findInvoice(int id){
        Invoice invoice = invoiceDao.getInvoiceById(id);
        return buildInvoiceViewModel(invoice);
    }

    //find all Invoices
    public List<InvoiceViewModel> findAllInvoices(){
        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        List<InvoiceViewModel> ivmList = new ArrayList<>();

        for(Invoice invoice : invoiceList){
            InvoiceViewModel ivm = buildInvoiceViewModel(invoice);
            ivmList.add(ivm);
        }
        return ivmList;
    }

    //update
    @Transactional
    public void updateInvoice(InvoiceViewModel invoiceViewModel){
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invoiceViewModel.getInvoiceId());
        invoice.setCustomerId(invoiceViewModel.getCustomerId());
        invoice.setPurchaseDate(invoiceViewModel.getPurchaseDate());

        invoiceDao.updateInvoice(invoice);

        List<InvoiceItem> invoiceItemList = invoiceItemDao.getInvoiceItemsByInvoiceId(invoice.getInvoiceId());
        invoiceItemList.stream()
                .forEach(
                        invoiceItem -> {
                            invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());
                        }
                );


        List<InvoiceItem> invoiceItems = invoiceViewModel.getInvoiceItemList();
        invoiceItems.stream()
                .forEach(
                        it -> {
                            it.setInvoiceId(invoiceViewModel.getInvoiceId());
                            it = invoiceItemDao.addInvoiceItem(it);
                        }
                );
    }

    //remove
    public void removeInvoice(int id){
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getInvoiceItemsByInvoiceId(id);
        invoiceItemList.stream()
                .forEach(
                        invoiceItem -> {
                            invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());
                        }
                );

        invoiceDao.deleteInvoice(id);
    }

    //get invoice by customer ID
    public List<InvoiceViewModel> getInvoiceByCustomerId(int id){
        List<Invoice> invoiceList = invoiceDao.getInvoiceByCustomerId(id);
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        for(Invoice invoice: invoiceList){
            //InvoiceViewModel ivm = buildInvoiceViewModel(invoice);
            ivmList.add(buildInvoiceViewModel(invoice));
        }
        return ivmList;
    }



    //helper method
    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice){
        //get the invoiceItems associated with invoice
        List<InvoiceItem> invoiceItemList = invoiceItemDao.getInvoiceItemsByInvoiceId(invoice.getInvoiceId());

        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setCustomerId(invoice.getCustomerId());
        invoiceViewModel.setPurchaseDate(invoice.getPurchaseDate());
        invoiceViewModel.setInvoiceItemList(invoiceItemList);

        return invoiceViewModel;
    }

}
