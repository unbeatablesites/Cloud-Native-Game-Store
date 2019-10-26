package com.company.retailapi.controller;

import com.company.retailapi.service.RetailApiServiceLayer;
import com.company.retailapi.view.InvoiceResponseViewModel;
import com.company.retailapi.view.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class RetailApiController {

    @Autowired
    RetailApiServiceLayer service;

    //save invoice
    @RequestMapping(value = "/invoice",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceResponseViewModel saveInvoice(@RequestBody @Valid InvoiceViewModel invoiceViewModel){
//        CustomerViewModel customerViewModel = service.findCustomer(invoiceViewModel.getCustomerId());
//        if(customerViewModel == null){
//            throw new IllegalArgumentException("Cannot create an Invoice. Customer ID does not exist.");
//        }
        return service.saveInvoice(invoiceViewModel);
    }

    //get invoice by id
    @RequestMapping(value = "/invoice/{id}",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceResponseViewModel findInvoiceById(@PathVariable("id") int id){
        //InvoiceViewModel invoiceViewModel = service.findInvoice(id);

        InvoiceResponseViewModel invoiceResponseViewModel = service.findInvoice(id);

        if(invoiceResponseViewModel == null){
            //throw illegal argument exception
        }
        return invoiceResponseViewModel;
    }

    //get all invoices
    @RequestMapping(value = "/invoice",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceResponseViewModel> findAllInvoices(){
        return service.findAllInvoices();
    }
}
