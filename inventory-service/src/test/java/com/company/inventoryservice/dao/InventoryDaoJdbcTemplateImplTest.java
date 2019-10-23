package com.company.inventoryservice.dao;

import com.company.inventoryservice.dto.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InventoryDaoJdbcTemplateImplTest {

    @Autowired
    InventoryDao inventoryDao;

    @Before
    public void setUp() throws Exception {
        List<Inventory> inventoryList = inventoryDao.getAllInventory();

        for(Inventory inventory : inventoryList){
            inventoryDao.deleteInventory(inventory.getInventoryId());
        }
    }

    @Test
    public void addGetDeleteInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);

        inventory = inventoryDao.addInventory(inventory);

        Inventory inventory1 = inventoryDao.getInventoryById(inventory.getInventoryId());

        assertEquals(inventory,inventory1);

        inventoryDao.deleteInventory(inventory.getInventoryId());
        inventory1 = inventoryDao.getInventoryById(inventory.getInventoryId());
        assertNull(inventory1);
    }

    @Test
    public void getAllInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);

        inventoryDao.addInventory(inventory);

        inventory = new Inventory();
        inventory.setProductId(2);
        inventory.setQuantity(20);

        inventoryDao.addInventory(inventory);

        List<Inventory> inventoryList = inventoryDao.getAllInventory();

        assertEquals(inventoryList.size(),2);
    }

    @Test
    public void updateInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);

        inventory = inventoryDao.addInventory(inventory);

        inventory.setProductId(2);
        inventory.setQuantity(20);

        inventoryDao.updateInventory(inventory);

        Inventory inventory1 = inventoryDao.getInventoryById(inventory.getInventoryId());

        assertEquals(inventory,inventory1);
    }
}