package com.company.inventoryservice.controller;

import com.company.inventoryservice.service.InventoryServiceLayer;
import com.company.inventoryservice.view.InventoryViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RefreshScope
public class InventoryController {
    //DI - service Layer Field Level
    @Autowired
    InventoryServiceLayer service;

    //add Inventory
    @RequestMapping(value = "/inventory",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryViewModel saveInventory(@RequestBody @Valid InventoryViewModel inventoryViewModel){
        return service.saveInventory(inventoryViewModel);
    }

    //get All products
    @RequestMapping(value = "/inventory",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getAllInventory(){
        return service.findAllInventory();
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
}
