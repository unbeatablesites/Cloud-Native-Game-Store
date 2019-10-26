package com.company.productservice.service;

import com.company.productservice.dao.ProductDao;
import com.company.productservice.dto.Product;
import com.company.productservice.view.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductServiceLayer {

    //DI - productDAO at Constructor Level
    private ProductDao productDao;
    @Autowired
    public ProductServiceLayer(ProductDao productDao){
        this.productDao=productDao;
    }

    //save product
    public ProductViewModel saveProduct(ProductViewModel productViewModel){
        //set up a new product object and assign productViewModel's properties
        Product product = new Product();
        product.setProductName(productViewModel.getProductName());
        product.setProductDescription(productViewModel.getProductDescription());
        product.setListPrice(productViewModel.getListPrice());
        product.setUnitCost(productViewModel.getUnitCost());

        //add the product using the DAO
        product = productDao.addProduct(product);

        //set the product ID on the productViewModel with the product object made
        productViewModel.setProductId(product.getProductId());

        return productViewModel;
    }

    //find product by id
    public ProductViewModel findProduct(int id){
        //create the product object with the id parameter
        Product product = productDao.getProductById(id);

        //check if the product object exists with that ID
        //if yes, use buildViewModel with that product object
        //if not return null
        if(product == null){
            return null;
        } else{
            return buildProductViewModel(product);
        }
    }

    //find all products
    @Transactional
    public List<ProductViewModel> findAllProducts(){
        List<Product> productList = productDao.getAllProducts();
        List<ProductViewModel> pvmList = new ArrayList<>();

        productList.stream()
                .forEach(
                        product -> {
                            ProductViewModel pvm = buildProductViewModel(product);
                            pvmList.add(pvm);
                        }
                );
        return pvmList;
    }

    //update product
    public void updateProduct(ProductViewModel productViewModel){
        //create a new Product object
        Product product = new Product();

        //set the product object using the view model passed as a parameter including the ID
        product.setProductId(productViewModel.getProductId());
        product.setProductName(productViewModel.getProductName());
        product.setProductDescription(productViewModel.getProductDescription());
        product.setListPrice(productViewModel.getListPrice());
        product.setUnitCost(productViewModel.getUnitCost());

        //update the product with DAO method.
        productDao.updateProduct(product);
    }

    //delete product
    public void removeProduct(int id){
        productDao.deleteProduct(id);
    }

    //Helper method. setting the view models conveniently
    private ProductViewModel buildProductViewModel(Product product){
        //create the empty productViewModel and set the properties from the product
        ProductViewModel productViewModel = new ProductViewModel();

        //set all properties including the ID
        productViewModel.setProductId(product.getProductId());
        productViewModel.setProductName(product.getProductName());
        productViewModel.setProductDescription(product.getProductDescription());
        productViewModel.setListPrice(product.getListPrice());
        productViewModel.setUnitCost(product.getUnitCost());
        return productViewModel;
    }
}
