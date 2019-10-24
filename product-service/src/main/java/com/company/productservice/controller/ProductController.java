package com.company.productservice.controller;

import com.company.productservice.service.ProductServiceLayer;
import com.company.productservice.view.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class ProductController {

    //DI - service Layer Field Level
    @Autowired
    ProductServiceLayer service;

    //add Product
    @RequestMapping(value = "/product",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductViewModel saveProduct(@RequestBody @Valid ProductViewModel productViewModel){
        return service.saveProduct(productViewModel);
    }

    //get All products
    @RequestMapping(value = "/product",method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductViewModel> getAllProducts(){
        return service.findAllProducts();
    }

    //Get Product by ID
    //this ways originally named just find ProductById
    @RequestMapping(value = "/product/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ProductViewModel findProductById(@PathVariable("id") int id){
        ProductViewModel productViewModel = service.findProduct(id);

        if(productViewModel == null){
            //throw illegal arugment exception
        }
        return productViewModel;
    }

    //Update Product
    @RequestMapping(value = "/product/{id}",method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("id") int id, @RequestBody @Valid ProductViewModel productViewModel){
        if(id != productViewModel.getProductId()){
            //throw illegal argument exception
        }
        service.updateProduct(productViewModel);
    }

    //Delete Product
    @RequestMapping(value = "/product/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(@PathVariable("id") int id){
        //simply remove the product using the id. If it does not exist, nothing will happen anyways
        service.removeProduct(id);
    }
}
