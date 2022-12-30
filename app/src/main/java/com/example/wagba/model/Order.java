package com.example.wagba.model;

import java.util.List;

public class Order {
    private String restaurantId;
    private String orderDate;
    private String price;
    private String orderId;
    private String status;
    private String deliverySlot;
    private List<OrderItem> orderItems;
    private String userId;

    public Order() {}

    public Order(String restaurantId, String userId, String deliverySlot, String orderDate, String price, String orderId, String status, List<OrderItem> orderItems) {
        this.restaurantId = restaurantId;
        this.orderDate = orderDate;
        this.price = price;
        this.orderId = orderId;
        this.status = status;
        this.deliverySlot = deliverySlot;
        this.orderItems = orderItems;
        this.userId = userId;
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

    public String getDeliverySlot() {
        return deliverySlot;
    }

    public void setDeliverySlot(String deliverySlot) {
        this.deliverySlot = deliverySlot;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
