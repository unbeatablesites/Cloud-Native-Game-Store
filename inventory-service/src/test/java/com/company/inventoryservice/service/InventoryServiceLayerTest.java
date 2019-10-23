package com.company.inventoryservice.service;

import com.company.inventoryservice.dao.InventoryDao;
import com.company.inventoryservice.dao.InventoryDaoJdbcTemplateImpl;
import com.company.inventoryservice.dto.Inventory;
import com.company.inventoryservice.view.InventoryViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class InventoryServiceLayerTest {

    InventoryServiceLayer inventoryServiceLayer;
    InventoryDao inventoryDao;

    @Before
    public void setUp() throws Exception {
        setUpInventoryDaoMock();
        inventoryServiceLayer = new InventoryServiceLayer(inventoryDao);
    }

    private void setUpInventoryDaoMock(){
        inventoryDao = mock(InventoryDaoJdbcTemplateImpl.class);
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setProductId(1);
        inventory.setQuantity(10);

        Inventory inventory1 = new Inventory();
        inventory1.setProductId(1);
        inventory1.setQuantity(10);

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory);

        doReturn(inventory).when(inventoryDao).addInventory(inventory1);
        doReturn(inventory).when(inventoryDao).getInventoryById(inventory.getInventoryId());
        doReturn(inventoryList).when(inventoryDao).getAllInventory();
    }

    @Test
    public void saveFindInventory() {
        InventoryViewModel inventoryViewModel = new InventoryViewModel();
        inventoryViewModel.setProductId(1);
        inventoryViewModel.setQuantity(10);

        inventoryViewModel = inventoryServiceLayer.saveInventory(inventoryViewModel);

        InventoryViewModel fromService = inventoryServiceLayer.findInventory(inventoryViewModel.getInventoryId());

        assertEquals(fromService,inventoryViewModel);
    }

    @Test
    public void findAllInventory() {
        InventoryViewModel inventoryViewModel = new InventoryViewModel();
        inventoryViewModel.setInventoryId(1);
        inventoryViewModel.setProductId(1);
        inventoryViewModel.setQuantity(10);

        List<InventoryViewModel> fromService = inventoryServiceLayer.findAllInventory();
        assertEquals(fromService.size(),1);
        assertEquals(fromService.get(0),inventoryViewModel);
    }
}