package com.example.wagba.model;

import java.util.Date;

public class Order {
    private String orderDate;
    private String price;
    private int numberOfObjects;
    private String orderId;
    private String status;

    public Order(String orderDate, String price, int numberOfObjects, String orderId, String status) {
        this.orderDate = orderDate;
        this.price = price;
        this.numberOfObjects = numberOfObjects;
        this.orderId = orderId;
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumberOfObjects() {
        return numberOfObjects;
    }

    public void setNumberOfObjects(int numberOfObjects) {
        this.numberOfObjects = numberOfObjects;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
