package com.company.productservice.dao;

import com.company.productservice.dto.Product;

import java.util.List;

public interface ProductDao {

    //CRUD Methods and get All
    Product addProduct(Product product);

    Product getProductById(int id);

    List<Product> getAllProducts();

    Product updateProduct(Product product);

    void deleteProduct(int id);
}
