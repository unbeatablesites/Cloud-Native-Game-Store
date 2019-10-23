package com.company.inventoryservice.service;

import com.company.inventoryservice.dao.InventoryDao;
import com.company.inventoryservice.dto.Inventory;
import com.company.inventoryservice.view.InventoryViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class InventoryServiceLayer {
    private InventoryDao inventoryDao;

    @Autowired
    public InventoryServiceLayer(InventoryDao inventoryDao){
        this.inventoryDao=inventoryDao;
    }

    public InventoryViewModel saveInventory(InventoryViewModel inventoryViewModel){
        Inventory inventory = new Inventory();
        inventory.setProductId(inventoryViewModel.getProductId());
        inventory.setQuantity(inventoryViewModel.getQuantity());
        inventory = inventoryDao.addInventory(inventory);

        inventoryViewModel.setInventoryId(inventory.getInventoryId());

        return inventoryViewModel;
    }

    public InventoryViewModel findInventory(int id){
        Inventory inventory = inventoryDao.getInventoryById(id);
        if(inventory == null){
            return null;
        }else{
            return buildInventoryViewModel(inventory);
        }
    }

    @Transactional
    public List<InventoryViewModel> findAllInventory(){
        List<Inventory> inventoryList = inventoryDao.getAllInventory();
        List<InventoryViewModel> ivmList = new ArrayList<>();

        inventoryList.stream()
                .forEach(
                        inventory -> {
                            InventoryViewModel ivm = buildInventoryViewModel(inventory);
                            ivmList.add(ivm);
                        }
                );
        return ivmList;
    }

    public void updateInventory(InventoryViewModel inventoryViewModel){
        Inventory inventory = new Inventory();
        inventory.setInventoryId(inventoryViewModel.getInventoryId());
        inventory.setProductId(inventoryViewModel.getProductId());
        inventory.setQuantity(inventoryViewModel.getQuantity());

        inventoryDao.updateInventory(inventory);
    }

    public void removeInventory(int id){
        inventoryDao.deleteInventory(id);
    }

//    @RequestMapping(value = "/products/inventory", method = RequestMethod.GET)
//    public List<Product> getProductsInInventory() {
//        return null;
//    }

    //helper method
    private InventoryViewModel buildInventoryViewModel(Inventory inventory){
        InventoryViewModel inventoryViewModel = new InventoryViewModel();
        inventoryViewModel.setInventoryId(inventory.getInventoryId());
        inventoryViewModel.setProductId(inventory.getProductId());
        inventoryViewModel.setQuantity(inventory.getQuantity());
        return inventoryViewModel;
    }
}
