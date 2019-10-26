package com.company.adminapi.controller;


import com.company.adminapi.service.AdminApiServiceLayer;
import com.company.adminapi.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class AdminApiController {

    @Autowired
    AdminApiServiceLayer service;

    //add a customer
    @RequestMapping(value = "/customer",method= RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public CustomerViewModel saveCustomer(@RequestBody @Valid CustomerViewModel customerViewModel){
        return service.saveCustomer(customerViewModel);
    }

    //get customer by id
    @RequestMapping(value = "/customer/{id}",method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CustomerViewModel findCustomerById(@PathVariable("id") int id){
        //create a customer View Model using the find method from the service layer
        CustomerViewModel customerViewModel = service.findCustomer(id);
        //if it does not exist, through an illegal argument exception
        if(customerViewModel == null){
            //throw new
        }
        //return the requested customer View Model
        return customerViewModel;
    }

    //update customer by id
    @RequestMapping(value = "/customer/{id}",method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable("id") int id, @RequestBody @Valid CustomerViewModel customerViewModel){
        //check if the id matches the request body. Path variable and the
        //user passed data (customerId) must match to proceed.
        //so you need to input in both path variable and the requestbody
        if(id != customerViewModel.getCustomerId()){
            //throw illegal arguement
        }

        //if it does match, update that customer
        service.updateCustomer(customerViewModel);
    }

    //get All customers
    @RequestMapping(value = "/customer",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerViewModel> getAllCustomers(){
        return service.findAllCustomers();
    }

    //delete customer by id
    @RequestMapping(value = "/customer/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCustomer(@PathVariable("id") int id){
        //simply remove the customer using the id. If it does not exist, nothing will happen anyways
        service.removeCustomer(id);
    }



    //add Product
    @RequestMapping(value = "/product",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductViewModel saveProduct(@RequestBody @Valid ProductViewModel productViewModel){
        return service.saveProduct(productViewModel);
    }
    //get All products
    @RequestMapping(value = "/product",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductViewModel> getAllProducts(){
        return service.findAllProducts();
    }

    //Get Product by ID
    //this ways originally named just find ProductById
    @RequestMapping(value = "/product/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ProductViewModel findProductById(@PathVariable("id") int id){
        ProductViewModel productViewModel = service.findProductById(id);

        if(productViewModel == null){
            //throw illegal arugment exception
        }
        return productViewModel;
    }

    //Update Product
    @RequestMapping(value = "/product/{id}",method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("id") int id, @RequestBody @Valid ProductViewModel productViewModel){
        if(id != productViewModel.getProductId()){
            //throw illegal argument exception
        }
        service.updateProduct(productViewModel);
    }

    //Delete Product
    @RequestMapping(value = "/product/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(@PathVariable("id") int id){
        //simply remove the product using the id. If it does not exist, nothing will happen anyways
        service.removeProduct(id);
    }


    //add Inventory
    @RequestMapping(value = "/inventory",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryViewModel saveInventory(@RequestBody @Valid InventoryViewModel inventoryViewModel){
        return service.saveInventory(inventoryViewModel);
    }

    //Get inventory by ID
    @RequestMapping(value = "/inventory/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public InventoryViewModel findInventoryById(@PathVariable("id") int id){
        InventoryViewModel inventoryViewModel = service.findInventory(id);

        if(inventoryViewModel == null){
            //throw illegal arugment exception
        }
        return inventoryViewModel;
    }

    //get All inventory
    @RequestMapping(value = "/inventory",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getAllInventory(){
        return service.findAllInventory();
    }

    //Update inventory
    @RequestMapping(value = "/inventory/{id}",method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInventory(@PathVariable("id") int id, @RequestBody @Valid InventoryViewModel inventoryViewModel){
        if(id != inventoryViewModel.getInventoryId()){
            //throw illegal argument exception
        }
        service.updateInventory(inventoryViewModel);
    }

    //Delete inventory
    @RequestMapping(value = "/inventory/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeInventory(@PathVariable("id") int id){
        //simply remove the product using the id. If it does not exist, nothing will happen anyways
        service.removeInventory(id);
    }


    //add level up
    @RequestMapping(value = "/levelup",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUpViewModel saveLevelUp(@RequestBody @Valid LevelUpViewModel levelUpViewModel){


        return service.saveLevelUp(levelUpViewModel);
    }

    //get all level ups - it was getAllLevelUps but i changed the name
    @RequestMapping(value = "/levelup",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUpViewModel> findAllLevelUps(){
        return service.findAllLevelUps();
    }

    //get level up
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel findLevelUpById(@PathVariable("id") int id){
        LevelUpViewModel levelUpViewModel = service.findLevelUp(id);

        if(levelUpViewModel == null){
            //throw exception. illgal argument
        }
        return levelUpViewModel;
    }

    //update level ups
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLevelUp(@PathVariable("id") int id, @RequestBody @Valid LevelUpViewModel levelUpViewModel){
        if(id != levelUpViewModel.getLevelUpId()){
            //throw illegal argument
        }
        service.updateLevelUp(levelUpViewModel);
    }

    //delete level ups
    @RequestMapping(value = "/levelup/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLevelUp(@PathVariable("id") int id){
        service.removeLevelUp(id);
    }




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
    @RequestMapping(value = "/invoice/{id}",method=RequestMethod.GET)
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

    //remove invoice
    @RequestMapping(value = "/invoice/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeInvoice(@PathVariable("id") int id){
        service.removeInvoice(id);
    }

}
