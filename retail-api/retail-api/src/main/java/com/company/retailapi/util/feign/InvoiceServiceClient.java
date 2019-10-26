package com.company.retailapi.util.feign;


import com.company.retailapi.view.InvoiceViewModel;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceServiceClient {
    //save invoice
    @RequestMapping(value = "/invoice",method= RequestMethod.POST)
    public InvoiceViewModel saveInvoice(@RequestBody @Valid InvoiceViewModel invoiceViewModel);


    //get all invoices
    @RequestMapping(value = "/invoice",method= RequestMethod.GET)
    public List<InvoiceViewModel> findAllInvoices();

    //get invoice by id
    @RequestMapping(value = "/invoice/{id}",method=RequestMethod.GET)
    public InvoiceViewModel findInvoiceById(@PathVariable("id") int id);


    //update invoice
    @RequestMapping(value = "/invoice/{id}",method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@PathVariable("id") int id, @RequestBody @Valid InvoiceViewModel invoiceViewModel);


    //remove invoice
    @RequestMapping(value = "/invoice/{id}",method=RequestMethod.DELETE)
    public void removeInvoice(@PathVariable("id") int id);

}
