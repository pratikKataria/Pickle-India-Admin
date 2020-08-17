package com.bytemint.pickleadmin.model;

import androidx.annotation.Nullable;

public class OrdersDetails {
    private String itemId;
    private String itemThumbImage;
    private int itemQty;
    private double itemBasePrice;
    private double itemSellPrice;
    private String itemCategory;
    private String itemName;

    public transient boolean isPastOrder;
    public transient String orderId;
    public transient int status;
    public transient long date;

    public OrdersDetails(String itemId, String itemThumbImage, int itemQty, double itemBasePrice, double itemSellPrice, String itemCategory, String itemName) {
        this.itemId = itemId;
        this.itemThumbImage = itemThumbImage;
        this.itemQty = itemQty;
        this.itemBasePrice = itemBasePrice;
        this.itemSellPrice = itemSellPrice;
        this.itemCategory = itemCategory;
        this.itemName = itemName;
    }


    public OrdersDetails() { }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getItemBasePrice() {
        return itemBasePrice;
    }

    public void setItemBasePrice(double itemBasePrice) {
        this.itemBasePrice = itemBasePrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemThumbImage() {
        return itemThumbImage;
    }

    public void setItemThumbImage(String itemThumbImage) {
        this.itemThumbImage = itemThumbImage;
    }

    public Integer getItemQty() {
        return itemQty;
    }

    @Override
    public String toString() {
        return "OrdersDetails{" +
                "itemId='" + itemId + '\'' + "\n" +
                "date='" + itemThumbImage + '\'' + "\n" +
                "itemQty=" + itemQty + "\n" +
                "itemBasePrice=" + itemBasePrice + "\n" +
                "itemCategory='" + itemCategory + '\'' + "\n" +
                "status ='" + status + '\'';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof OrdersDetails) {
            OrdersDetails ordersDetails = (OrdersDetails) obj;
            return ordersDetails.itemId.equals(itemId);
        }
        return false;
    }

    public double getItemSellPrice() {
        return itemSellPrice;
    }

    public void setItemSellPrice(double itemSellPrice) {
        this.itemSellPrice = itemSellPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
