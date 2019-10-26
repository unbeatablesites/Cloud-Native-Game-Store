package com.company.inventoryservice.dao;

import com.company.inventoryservice.dto.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InventoryDaoJdbcTemplateImpl implements InventoryDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public InventoryDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    private static final String INSERT_INVENTORY =
            "insert into inventory (product_id,quantity) values (?,?)";
    private static final String SELECT_INVENTORY =
            "select * from inventory where inventory_id = ?";
    private static final String SELECT_ALL_INVENTORY =
            "select * from inventory";
    private static final String UPDATE_INVENTORY =
            "update inventory set product_id=?, quantity=? where inventory_id=?";
    private static final String DELETE_INVENTORY =
            "delete from inventory where inventory_id = ?";

    //row mapper function
    public Inventory mapRowToInventory(ResultSet rs, int rowNum) throws SQLException {
        Inventory i = new Inventory();
        i.setInventoryId(rs.getInt("inventory_id"));
        i.setProductId(rs.getInt("product_id"));
        i.setQuantity(rs.getInt("quantity"));
        return i;
    }

    @Override
    @Transactional
    public Inventory addInventory(Inventory inventory) {
        jdbcTemplate.update(INSERT_INVENTORY,
                inventory.getProductId(),
                inventory.getQuantity()
        );
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        inventory.setInventoryId(id);
        return inventory;
    }

    @Override
    public Inventory getInventoryById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_INVENTORY, this::mapRowToInventory, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Inventory> getAllInventory() {
        return jdbcTemplate.query(SELECT_ALL_INVENTORY,this::mapRowToInventory);
    }

    @Override
    public Inventory updateInventory(Inventory inventory) {
        jdbcTemplate.update(UPDATE_INVENTORY,
                inventory.getProductId(),
                inventory.getQuantity(),
                inventory.getInventoryId()
        );
        return inventory;
    }

    @Override
    public void deleteInventory(int id) {
        jdbcTemplate.update(DELETE_INVENTORY,id);
    }
}
