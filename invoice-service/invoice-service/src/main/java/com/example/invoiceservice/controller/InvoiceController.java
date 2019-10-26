package com.example.invoiceservice.controller;

import com.example.invoiceservice.service.InvoiceServiceLayer;
import com.example.invoiceservice.view.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class InvoiceController {

    @Autowired
    InvoiceServiceLayer service;


    //save invoice
    @RequestMapping(value = "/invoice",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel saveInvoice(@RequestBody @Valid InvoiceViewModel invoiceViewModel){
        return service.saveInvoice(invoiceViewModel);
    }


    //get all invoices
    @RequestMapping(value = "/invoice",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> findAllInvoices(){
        return service.findAllInvoices();
    }

    //get invoice by id
    @RequestMapping(value = "/invoice/{id}",method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel findInvoiceById(@PathVariable("id") int id){
        InvoiceViewModel invoiceViewModel = service.findInvoice(id);

        if(invoiceViewModel == null){
            //throw illegal argument exception
        }
        return invoiceViewModel;
    }


    //update invoice
    @RequestMapping(value = "/invoice/{id}",method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@PathVariable("id") int id, @RequestBody @Valid InvoiceViewModel invoiceViewModel){
        if(id != invoiceViewModel.getInvoiceId()){
            //throw illegal argument
        }
        service.updateInvoice(invoiceViewModel);
    }


    //remove invoice
    @RequestMapping(value = "/invoice/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeInvoice(@PathVariable("id") int id){
        service.removeInvoice(id);
    }




}
