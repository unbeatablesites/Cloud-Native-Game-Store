package com.company.productservice.service;

import com.company.productservice.dao.ProductDao;
import com.company.productservice.dao.ProductDaoJdbcTemplateImpl;
import com.company.productservice.dto.Product;
import com.company.productservice.view.ProductViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ProductServiceLayerTest {

    //Declare the product DAO class and the service layer
    ProductDao productDao;
    ProductServiceLayer productServiceLayer;

    //setting up the mocks
    @Before
    public void setUp() throws Exception {
        setUpProductDaoMock();

        //set up the product service layer
        productServiceLayer = new ProductServiceLayer(productDao);
    }

    //implementing the productDaoMock
    private void setUpProductDaoMock(){
        productDao = mock(ProductDaoJdbcTemplateImpl.class);

        //create the expected value. which includes the id as well
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("PS4");
        product.setProductDescription("Gaming Console");
        product.setListPrice(new BigDecimal("99.99"));
        product.setUnitCost(new BigDecimal("80.00"));

        //this will be the product that is "requested". ID will not be included
        Product product1 = new Product();
        product1.setProductName("PS4");
        product1.setProductDescription("Gaming Console");
        product1.setListPrice(new BigDecimal("99.99"));
        product1.setUnitCost(new BigDecimal("80.00"));

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        //returns
        //returns product when product1 gets added/requested
        doReturn(product).when(productDao).addProduct(product1);
        doReturn(product).when(productDao).getProductById(1);
        doReturn(productList).when(productDao).getAllProducts();

    }

    @Test
    public void saveFindProduct() {
        //create a View Model
        ProductViewModel productViewModel = new ProductViewModel();
        productViewModel.setProductId(1);
        productViewModel.setProductName("PS4");
        productViewModel.setProductDescription("Gaming Console");
        productViewModel.setListPrice(new BigDecimal("99.99"));
        productViewModel.setUnitCost(new BigDecimal("80.00"));

        //using the servicelayer method, create a new productView model to compare
        ProductViewModel fromService = productServiceLayer.findProduct(productViewModel.getProductId());

        //check the match b/w setup and from service
        assertEquals(productViewModel,fromService);
    }

    @Test
    public void findAllProducts(){
        ProductViewModel productViewModel = new ProductViewModel();
        productViewModel.setProductId(1);
        productViewModel.setProductName("PS4");
        productViewModel.setProductDescription("Gaming Console");
        productViewModel.setListPrice(new BigDecimal("99.99"));
        productViewModel.setUnitCost(new BigDecimal("80.00"));

        List<ProductViewModel> fromService = productServiceLayer.findAllProducts();
        assertEquals(fromService.size(),1);
        assertEquals(productViewModel,fromService.get(0));
    }

}