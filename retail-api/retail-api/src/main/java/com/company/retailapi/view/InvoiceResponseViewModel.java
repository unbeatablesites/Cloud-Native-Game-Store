package com.company.retailapi.view;

import com.company.retailapi.dto.InvoiceItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InvoiceResponseViewModel {
    private int invoiceId;
    private Integer customerId;
    private LocalDate purchaseDate;
    private List<InvoiceItem> invoiceItemList;
    private Integer levelUpPoints;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<InvoiceItem> getInvoiceItemList() {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItem> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
    }

    public Integer getLevelUpPoints() {
        return levelUpPoints;
    }

    public void setLevelUpPoints(Integer levelUpPoints) {
        this.levelUpPoints = levelUpPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceResponseViewModel that = (InvoiceResponseViewModel) o;
        return invoiceId == that.invoiceId &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(purchaseDate, that.purchaseDate) &&
                Objects.equals(invoiceItemList, that.invoiceItemList) &&
                Objects.equals(levelUpPoints, that.levelUpPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, purchaseDate, invoiceItemList, levelUpPoints);
    }
}
