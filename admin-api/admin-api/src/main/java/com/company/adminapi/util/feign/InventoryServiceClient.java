package com.company.adminapi.util.feign;

import com.company.adminapi.view.InventoryViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {

    //add Product
    @RequestMapping(value = "/inventory",method= RequestMethod.POST)
    public InventoryViewModel saveInventory(@RequestBody @Valid InventoryViewModel inventoryViewModel);

    //get All products
    @RequestMapping(value = "/inventory",method= RequestMethod.GET)
    public List<InventoryViewModel> getAllInventory();

    //Get Product by ID
    @RequestMapping(value = "/inventory/{id}", method=RequestMethod.GET)
    public InventoryViewModel findInventoryById(@PathVariable("id") int id);

    //Update Product
    @RequestMapping(value = "/inventory/{id}",method=RequestMethod.PUT)
    public void updateInventory(@PathVariable("id") int id, @RequestBody @Valid InventoryViewModel inventoryViewModel);

    //Delete Product
    @RequestMapping(value = "/inventory/{id}",method=RequestMethod.DELETE)
    public void removeInventory(@PathVariable("id") int id);
}
