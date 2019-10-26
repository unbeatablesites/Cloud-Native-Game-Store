package com.company.retailapi.service;


import com.company.retailapi.dto.InvoiceItem;
import com.company.retailapi.dto.LevelUp;
import com.company.retailapi.util.feign.*;
import com.company.retailapi.view.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RetailApiServiceLayer {
    public static final String EXCHANGE = "levelUp-exchange";
    public static final String ROUTING_KEY = "levelUp.create.retail.service";

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

    public RetailApiServiceLayer(CustomerServiceClient customerServiceClient,
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

            LevelUp levelUp = new LevelUp();
            levelUp.setCustomerId(levelUpViewModel.getCustomerId());
            levelUp.setMemberDate(levelUpViewModel.getMemberDate());
            levelUp.setPoints(levelUpViewModel.getPoints());
            System.out.println("Sending Level Up...");
            rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY,levelUp);
            System.out.println("Level Up object Sent to Queue...");

            //levelUpServiceClient.saveLevelUp(levelUpViewModel);
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


    private void slowService() {
        try {
            long time = 1000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }


}
