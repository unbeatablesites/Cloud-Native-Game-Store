package com.company.productservice.dao;

import com.company.productservice.dto.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductDaoJdbcTemplateImplTest {

    //Field DI of ProductDao
    @Autowired
    ProductDao productDao;

    //for the test, you must clear the database using get all
    //and delete methods from the DAO.
    @Before
    public void setUp() throws Exception {
        //first place all the items in the database into productLIst
        List<Product> productList = productDao.getAllProducts();

        //loop through and delete all of the contents in the productList
        for(Product product : productList){
            productDao.deleteProduct(product.getProductId());
        }
    }

    @Test
    public void addGetDeleteProduct() {
        //you set up the dummy product object without ID
        Product product = new Product();
        product.setProductName("PS4");
        product.setProductDescription("Gaming Console");
        product.setListPrice(new BigDecimal("99.99"));
        product.setUnitCost(new BigDecimal("80.00"));

        //add the product in the DB with DAO
        product = productDao.addProduct(product);

        //Create a new Product Object and assign it using DAO
        Product product1 = productDao.getProductById(product.getProductId());

        //check if they are equal
        assertEquals(product,product1);

        //delete the original product
        productDao.deleteProduct(product.getProductId());

        //assign the customer1 using DAO
        product1 = productDao.getProductById(product.getProductId());

        //check for null
        assertNull(product1);
    }

    @Test
    public void getAllProducts() {
        //you set up the dummy product object without ID
        Product product = new Product();
        product.setProductName("PS4");
        product.setProductDescription("Gaming Console");
        product.setListPrice(new BigDecimal("99.99"));
        product.setUnitCost(new BigDecimal("80.00"));

        //add the first product
        productDao.addProduct(product);

        //renew the product with new Product(); and create the second product
        product = new Product();
        product.setProductName("XBox Pro");
        product.setProductDescription("Gaming Console from MS");
        product.setListPrice(new BigDecimal("90.99"));
        product.setUnitCost(new BigDecimal("70.00"));

        //add the 2nd customer
        productDao.addProduct(product);

        //put all of the in the List<Customer>
        List<Product> productList = productDao.getAllProducts();

        //check the size. should be 2, since I've added 2.
        assertEquals(productList.size(),2);
    }

    @Test
    public void updateProduct() {
        //you set up the dummy product object without ID
        Product product = new Product();
        product.setProductName("PS4");
        product.setProductDescription("Gaming Console");
        product.setListPrice(new BigDecimal("99.99"));
        product.setUnitCost(new BigDecimal("80.00"));

        //create the first product
        product = productDao.addProduct(product);

        //set that same product with new values, "updating"
        product.setProductName("XBox Pro");
        product.setProductDescription("Gaming Console from MS");
        product.setListPrice(new BigDecimal("90.99"));
        product.setUnitCost(new BigDecimal("70.00"));

        //update the product using DAO
        productDao.updateProduct(product);

        //create a new product using get method
        //check if they are equal
        Product product1 = productDao.getProductById(product.getProductId());
        assertEquals(product,product1);
    }
}