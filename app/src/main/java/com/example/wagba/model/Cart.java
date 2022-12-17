package com.example.wagba.model;

import com.example.wagba.feeCalculator.delivery.DeliveryFeeCalculator;
import com.example.wagba.feeCalculator.tax.TaxCalculator;

import java.util.HashMap;
import java.util.Map;


public class Cart {
    private Map<Food, Integer> items;
    private DeliveryFeeCalculator deliveryFeeCalculator;
    private TaxCalculator taxCalculator;

    public Cart(DeliveryFeeCalculator deliveryFeeCalculator, TaxCalculator taxCalculator) {
        items = new HashMap<>();
        this.deliveryFeeCalculator = deliveryFeeCalculator;
        this.taxCalculator = taxCalculator;
    }

    public void plus(Food item) {
        int quantity = 1;
        if (items.containsKey(item)) {
            quantity += items.get(item);
        }
        items.put(item, quantity);
    }

    public void minus(Food item) {
        if (items.containsKey(item)) {
            int quantity = items.get(item);
            if (quantity > 1) {
                items.put(item, quantity - 1);
            } else {
                items.remove(item);
            }
        }
    }

    public double getTotalCost() {
        double subtotal = 0;
        for (Map.Entry<Food, Integer> entry : items.entrySet()) {
            Food menuItem = entry.getKey();
            int itemQuantity = entry.getValue();
            subtotal += Float.parseFloat(menuItem.getPrice()) * itemQuantity;
        }
        double deliveryFee = deliveryFeeCalculator.calculateDeliveryFee(subtotal);
        double tax = taxCalculator.calculateTax(subtotal + deliveryFee);
        return subtotal + deliveryFee + tax;
    }

    public double getDeliveryFee() {
        double subtotal = 0;
        for (Map.Entry<Food, Integer> entry : items.entrySet()) {
            Food menuItem = entry.getKey();
            int itemQuantity = entry.getValue();
            subtotal += Float.parseFloat(menuItem.getPrice()) * itemQuantity;
        }
        return deliveryFeeCalculator.calculateDeliveryFee(subtotal);
    }

    public double getTax() {
        double subtotal = 0;
        for (Map.Entry<Food, Integer> entry : items.entrySet()) {
            Food menuItem = entry.getKey();
            int itemQuantity = entry.getValue();
            subtotal += Float.parseFloat(menuItem.getPrice()) * itemQuantity;
        }
        double deliveryFee = deliveryFeeCalculator.calculateDeliveryFee(subtotal);
        return taxCalculator.calculateTax(subtotal + deliveryFee);
    }

    public void clear() {
        items.clear();
    }
}


