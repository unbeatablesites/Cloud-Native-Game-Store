package com.company.inventoryservice.dao;

import com.company.inventoryservice.dto.Inventory;

import java.util.List;

public interface InventoryDao {

    Inventory addInventory(Inventory inventory);

    Inventory getInventoryById(int id);

    List<Inventory> getAllInventory();

    Inventory updateInventory(Inventory inventory);

    void deleteInventory(int id);
}
