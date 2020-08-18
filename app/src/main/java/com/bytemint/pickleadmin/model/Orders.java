package com.bytemint.pickleadmin.model;

import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

public class Orders implements Serializable {
    private long date;
    private String orderDetailsIds;
    private String orderId;
    private int orderStatus;
    private String userId;
    private double subTotal;
    private double pcoinsSpent;
    private String paymentMethod;
    private String deliveryTime;
    private String address;
    private int shipping;

    private String comboId;
    private double comboPrice;
    private int comboQuantity;

    public boolean isPastOrder;
    public int totalProduct;

    public Orders(long date, String orderDetailsIds, String orderId, int orderStatus, double pcoinsSpent, String userId, double subTotal, String paymentMethod, String deliveryTime, String address, int shipping, String comboId, double comboPrice, int comboQuantity) {
        this.date = date;
        this.orderDetailsIds = orderDetailsIds;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.pcoinsSpent = pcoinsSpent;
        this.userId = userId;
        this.subTotal = subTotal;
        this.paymentMethod = paymentMethod;
        this.deliveryTime = deliveryTime;
        this.address = address;
        this.shipping = shipping;
        this.comboId = comboId;
        this.comboPrice = comboPrice;
        this.comboQuantity = comboQuantity;
    }

    public Orders() {}

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getOrderDetailsIds() {
        return orderDetailsIds;
    }

    public void setOrderDetailsIds(String orderDetailsIds) {
        this.orderDetailsIds = orderDetailsIds;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getPcoinsSpent() {
        return pcoinsSpent;
    }

    public void setPcoinsSpent(double pcoinsSpent) {
        this.pcoinsSpent = pcoinsSpent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getShipping() {
        return shipping;
    }

    public void setShipping(int shipping) {
        this.shipping = shipping;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Log.e("order", "equals: instance of " + (obj instanceof Orders) );

        if (obj instanceof Orders) {
            Orders orders = (Orders) obj;
            Log.e("order", "equals: " + orderId + " get order" + orders.getOrderId() );
            return orders.getOrderId().matches(orderId);
        }
        return false;
    }

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public double getComboPrice() {
        return comboPrice;
    }

    public void setComboPrice(double comboPrice) {
        this.comboPrice = comboPrice;
    }


    @Override
    public String toString() {
        return "Orders :" +"\n"+
                " date = " + new Date(date) +"\n"+
                " orderId = " + orderId + '\'' +"\n"+
                " orderStatus = "  + orderStatus +"\n"+
                " userId = " + userId + '\'' +"\n"+
                " subTotal = " + subTotal +"\n"+
                " pcoinsSpent = " + pcoinsSpent +"\n"+
                " deliveryTime = " + deliveryTime + '\'' +"\n"+
                " address = " + address + '\'' +"\n"+
                " shipping = " + shipping +"\n"+
                " comboId = " + comboId + '\'' +"\n"+
                " comboPrice = " + comboPrice +"\n"+
                " totalProduct = " + totalProduct +"\n";
    }

    public int getComboQuantity() {
        return comboQuantity;
    }

    public void setComboQuantity(int comboQuantity) {
        this.comboQuantity = comboQuantity;
    }

    public double getAllOverTotal() {
        return ((getShipping() + getComboPrice() + getSubTotal()) - getPcoinsSpent());
    }

}
