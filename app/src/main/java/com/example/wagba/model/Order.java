package com.example.wagba.model;

import java.util.Date;
import java.util.List;

public class Order {
    private String restaurantId;
    private String orderDate;
    private String price;
    private String orderId;
    private String status;
    private List<OrderItem> orderItems;

    public Order() {}

    public Order(String restaurantId, String orderDate, String price, int numberOfObjects, String orderId, String status, List<OrderItem> orderItems) {
        this.restaurantId = restaurantId;
        this.orderDate = orderDate;
        this.price = price;
        this.orderId = orderId;
        this.status = status;
        this.orderItems = orderItems;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
