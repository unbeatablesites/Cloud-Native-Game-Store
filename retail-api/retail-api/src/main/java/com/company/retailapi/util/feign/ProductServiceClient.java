package com.company.retailapi.util.feign;


import com.company.retailapi.view.ProductViewModel;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    //add Product
    @RequestMapping(value = "/product",method= RequestMethod.POST)
    public ProductViewModel saveProduct(@RequestBody @Valid ProductViewModel productViewModel);

    //get All products
    @RequestMapping(value = "/product",method= RequestMethod.GET)
    public List<ProductViewModel> getAllProducts();

    //Get Product by ID
    @RequestMapping(value = "/product/{id}", method=RequestMethod.GET)
    public ProductViewModel findProductById(@PathVariable("id") int id);

    //Update Product
    @RequestMapping(value = "/product/{id}",method=RequestMethod.PUT)
    public void updateProduct(@PathVariable("id") int id, @RequestBody @Valid ProductViewModel productViewModel);

    //Delete Product
    @RequestMapping(value = "/product/{id}",method=RequestMethod.DELETE)
    public void removeProduct(@PathVariable("id") int id);
}
