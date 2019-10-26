package com.company.adminapi.service;

import com.company.adminapi.dto.Customer;
import com.company.adminapi.dto.Inventory;
import com.company.adminapi.dto.InvoiceItem;
import com.company.adminapi.dto.Product;
import com.company.adminapi.util.feign.*;
import com.company.adminapi.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdminApiServiceLayer {
    //Feign setup
    @Autowired
    private final CustomerServiceClient customerServiceClient;
    @Autowired
    private final ProductServiceClient productServiceClient;
    @Autowired
    private final InventoryServiceClient inventoryServiceClient;
    @Autowired
    private final LevelUpServiceClient levelUpServiceClient;
    @Autowired
    private final InvoiceServiceClient invoiceServiceClient;

    public AdminApiServiceLayer(CustomerServiceClient customerServiceClient,
                                ProductServiceClient productServiceClient,
                                InventoryServiceClient inventoryServiceClient,
                                LevelUpServiceClient levelUpServiceClient,
                                InvoiceServiceClient invoiceServiceClient
                                ){
        this.customerServiceClient=customerServiceClient;
        this.productServiceClient=productServiceClient;
        this.inventoryServiceClient=inventoryServiceClient;
        this.levelUpServiceClient=levelUpServiceClient;
        this.invoiceServiceClient=invoiceServiceClient;
    }

    /*@@@@@@@@@@@@@@@@@@@@@@@@@ BELOW IS FOR THE CUSTOMER SERVICE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
    //CRUD + get ALL for Customer
    //create Customer
    @Transactional
    public CustomerViewModel saveCustomer(CustomerViewModel customerViewModel){
        return customerServiceClient.saveCustomer(customerViewModel);
    }

    //get Customer
    public CustomerViewModel findCustomer(int id){
        CustomerViewModel customerViewModel = customerServiceClient.findCustomerById(id);

        if(customerViewModel == null){
            return null;
        } else {
            return customerViewModel;
        }
    }

    //update customer
    public void updateCustomer(CustomerViewModel customerViewModel){
        customerServiceClient.updateCustomer(customerViewModel.getCustomerId(),customerViewModel);
    }

    //delete customer
    public void removeCustomer(int id){
        //directly delete the customer object using the DAO method
        customerServiceClient.removeCustomer(id);
    }

    //find all customers
    @Transactional
    public List<CustomerViewModel> findAllCustomers(){
        List<CustomerViewModel> cvmList = customerServiceClient.getAllCustomers();
        return cvmList;
    }
    /*@@@@@@@@@@@@@@@@@@@@@@@@@ ABOVE IS FOR THE CUSTOMER SERVICE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/


    /*@@@@@@@@@@@@@@@@@@@@@@@@@ BELOW IS FOR THE PRODUCT SERVICE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
    //save product
    public ProductViewModel saveProduct(ProductViewModel productViewModel){
        return productServiceClient.saveProduct(productViewModel);
    }

    public ProductViewModel findProductById(int id){
    //create the product object with the id parameter
        ProductViewModel productViewModel = productServiceClient.findProductById(id);

        if(productViewModel == null){
            return null;
        } else{
            return productViewModel;
        }
    }
    //find all products
    @Transactional
    public List<ProductViewModel> findAllProducts(){
        List<ProductViewModel> pvmList = productServiceClient.getAllProducts();
        return pvmList;
    }

    //update product
    public void updateProduct(ProductViewModel productViewModel){

        productServiceClient.updateProduct(productViewModel.getProductId(),productViewModel);
    }

    //delete product
    public void removeProduct(int id){
        productServiceClient.removeProduct(id);
    }
    /*@@@@@@@@@@@@@@@@@@@@@@@@@ ABOVE IS FOR THE PRODUCT SERVICE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/


    /*@@@@@@@@@@@@@@@@@@@@@@@@@ BELOW IS FOR INVENTORY SERVICE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
    public InventoryViewModel saveInventory(InventoryViewModel inventoryViewModel){
        ProductViewModel productViewModel = productServiceClient.findProductById(inventoryViewModel.getProductId());

        //so if the user inputted product id does not exist, return null
        //else create the inventory row.
        if(productViewModel == null){
            return null;
        } else {
            return inventoryServiceClient.saveInventory(inventoryViewModel);
        }

    }

    public InventoryViewModel findInventory(int id){
        InventoryViewModel inventoryViewModel = inventoryServiceClient.findInventoryById(id);
        if(inventoryViewModel == null){
            return null;
        }else{
            return inventoryViewModel;
        }
    }
    @Transactional
    public List<InventoryViewModel> findAllInventory(){
        List<InventoryViewModel> ivmList = inventoryServiceClient.getAllInventory();
        return ivmList;
    }

    public void updateInventory(InventoryViewModel inventoryViewModel){
        //if the product ID does not match, throw and exception
        inventoryServiceClient.updateInventory(inventoryViewModel.getInventoryId(),inventoryViewModel);
    }

    public void removeInventory(int id){
        inventoryServiceClient.removeInventory(id);
    }
    /*@@@@@@@@@@@@@@@@@@@@@@@@@ ABOVE IS FOR THE INVENTORY SERVICE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/


    /*@@@@@@@@@@@@@@@@@@@@@@@@@ BELOW IS FOR THE LEVEL UP SERVICE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
    //save levelUp
    public LevelUpViewModel saveLevelUp(LevelUpViewModel levelUpViewModel){
        CustomerViewModel customerViewModel = customerServiceClient.findCustomerById(levelUpViewModel.getCustomerId());
        //so if the user inputted customer id does not exist, return null
        //else create the level up row.
        if(customerViewModel == null){
            return null;
        } else {
            return levelUpServiceClient.saveLevelUp(levelUpViewModel);
        }

    }

    //find levelUp by id
    public LevelUpViewModel findLevelUp(int id){
        LevelUpViewModel levelUpViewModel = levelUpServiceClient.findLevelUpById(id);

        if(levelUpViewModel == null){
            return null;
        } else{
            return levelUpViewModel;
        }
    }

    //find all levelUps
    public List<LevelUpViewModel> findAllLevelUps(){
        List<LevelUpViewModel> lvmList = levelUpServiceClient.findAllLevelUps();

        return lvmList;
    }

    //update level up
    public void updateLevelUp(LevelUpViewModel levelUpViewModel){
        //if the customer ID does not match, throw and exception
        levelUpServiceClient.updateLevelUp(levelUpViewModel.getLevelUpId(),levelUpViewModel);
    }

    //remove level up
    public void removeLevelUp(int id){
        levelUpServiceClient.removeLevelUp(id);
    }

    //I dont think i need the get level up points by customer ID feature in here.

    /*@@@@@@@@@@@@@@@@@@@@@@@@@ ABOVE IS FOR THE LEVEL UP  SERVICE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/




    /*@@@@@@@@@@@@@@@@@@@@@@@@@ BELOW IS FOR THE INVOICE SERVICE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
    //Update Invoice
    //Not sure if it will be neceesary.

    //save invoice
    @Transactional
    public InvoiceResponseViewModel saveInvoice(InvoiceViewModel invoiceViewModel){
        CustomerViewModel customerViewModel = customerServiceClient.findCustomerById(invoiceViewModel.getCustomerId());
        if(customerViewModel == null){
            throw new IllegalArgumentException("Cannot create an Invoice. Customer ID does not exist.");
        }else{
            List<InvoiceItem> invoiceItems = invoiceViewModel.getInvoiceItemList();
            InventoryViewModel inventoryViewModel;
            boolean flag = false;
            for(InvoiceItem invoiceItem : invoiceItems){
                inventoryViewModel = inventoryServiceClient.findInventoryById(invoiceItem.getInventoryId());
                if(inventoryViewModel == null){
                    flag = true;
                }
            }
            if(flag){
                throw new IllegalArgumentException("Cannot create an Invoice. Inventory ID does not exist.");
            }

            //you need to create the response view model and persist first
            InvoiceViewModel afterSaveInvoice = invoiceServiceClient.saveInvoice(invoiceViewModel);
            InvoiceResponseViewModel invoiceResponseViewModel = buildInvoiceResponseViewModel(afterSaveInvoice);

            //need to calculate the total cost to determine points to input in the level up service
            //need to go through each itemList and check the quantity and unit price.
            BigDecimal total = new BigDecimal("0.00");
            List<InvoiceItem> invoiceItemList = invoiceViewModel.getInvoiceItemList();
            for(InvoiceItem invoiceItem : invoiceItemList){
                BigDecimal totalCostPerItem = invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuantity()));
                total = total.add(totalCostPerItem);
            }
            //you need to set the level up points for invoice response view model
            int totalPointsToSet = (total.divide(new BigDecimal("50.00")).intValue())*10;
            invoiceResponseViewModel.setLevelUpPoints(totalPointsToSet);

            //need to create the levelup first. for memeber date, it will get the old one.
            //if the member date is already there, you need to take the old one.
            //determine whether invoice was created from that same customer Id and if it is, set the previous
            //member date as the old one since the member date gets generated at the very first order, invoice creation.
            LevelUpViewModel levelUpViewModel = new LevelUpViewModel();
            List<LevelUpViewModel> levelUpViewModelList = levelUpServiceClient.findAllLevelUps();
            LocalDate memberDate = invoiceResponseViewModel.getPurchaseDate();
            for(LevelUpViewModel levelUpViewModel1 : levelUpViewModelList){
                if(levelUpViewModel1.getCustomerId() == invoiceResponseViewModel.getCustomerId()){
                    memberDate = levelUpViewModel1.getMemberDate();
                    break;
                }
            }
            levelUpViewModel.setMemberDate(memberDate);
            levelUpViewModel.setPoints(totalPointsToSet);
            levelUpViewModel.setCustomerId(invoiceResponseViewModel.getCustomerId());
            levelUpServiceClient.saveLevelUp(levelUpViewModel);
            return invoiceResponseViewModel;
        }
    }


    //find Invoice
    public InvoiceResponseViewModel findInvoice(int id){
        InvoiceViewModel invoiceViewModel = invoiceServiceClient.findInvoiceById(id);
        if(invoiceViewModel == null){
            return null;
        }else{
            InvoiceResponseViewModel invoiceResponseViewModel = buildInvoiceResponseViewModel(invoiceViewModel);
            int levelUpPoints = levelUpServiceClient.getLevelUpPointsByCustomerId(invoiceResponseViewModel.getCustomerId());
            invoiceResponseViewModel.setLevelUpPoints(levelUpPoints);
            return invoiceResponseViewModel;
        }
    }
    //find all Invoices
    public List<InvoiceResponseViewModel> findAllInvoices(){
        List<InvoiceViewModel> invoiceViewModelList = invoiceServiceClient.findAllInvoices();
        List<InvoiceResponseViewModel> ivmList = new ArrayList<>();
        if(invoiceViewModelList.size() == 0){
            return null;
        }else{
            for(InvoiceViewModel invoiceViewModel : invoiceViewModelList){
                InvoiceResponseViewModel irvm = buildInvoiceResponseViewModel(invoiceViewModel);
                int levelUpPoints = levelUpServiceClient.getLevelUpPointsByCustomerId(invoiceViewModel.getCustomerId());
                irvm.setLevelUpPoints(levelUpPoints);
                ivmList.add(irvm);
            }
            return ivmList;
        }
    }

    //remove
    public void removeInvoice(int id){
        invoiceServiceClient.removeInvoice(id);
    }

    //helper method
    private InvoiceResponseViewModel buildInvoiceResponseViewModel(InvoiceViewModel invoiceViewModel){
        List<InvoiceItem> invoiceItemList =
                invoiceServiceClient.findInvoiceById(invoiceViewModel.getInvoiceId()).getInvoiceItemList();

        InvoiceResponseViewModel invoiceResponseViewModel = new InvoiceResponseViewModel();
        invoiceResponseViewModel.setInvoiceId(invoiceViewModel.getInvoiceId());
        invoiceResponseViewModel.setCustomerId(invoiceViewModel.getCustomerId());
        invoiceResponseViewModel.setPurchaseDate(invoiceViewModel.getPurchaseDate());
        invoiceResponseViewModel.setInvoiceItemList(invoiceItemList);

        return invoiceResponseViewModel;
    }
    /*@@@@@@@@@@@@@@@@@@@@@@@@@ ABOVE IS FOR THE INVOICE  SERVICE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/






    private void slowService() {
        try {
            long time = 1000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
