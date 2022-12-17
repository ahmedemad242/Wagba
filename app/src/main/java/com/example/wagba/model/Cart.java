package com.example.wagba.model;

import com.example.wagba.feeCalculator.delivery.DeliveryFeeCalculator;
import com.example.wagba.feeCalculator.tax.TaxCalculator;

import java.util.HashMap;
import java.util.Map;


public class Cart {
    public Map<Food, Integer> items;
    private DeliveryFeeCalculator deliveryFeeCalculator;
    private TaxCalculator taxCalculator;
    private static Cart instance;

    private Cart() {
        items = new HashMap<>();
    }

    public void setDeliveryFeeCalculator(DeliveryFeeCalculator deliveryFeeCalculator) {
        this.deliveryFeeCalculator = deliveryFeeCalculator;
    }

    public void setTaxCalculator(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
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
        double subtotal = getSubtotal();
        double deliveryFee = deliveryFeeCalculator.calculateDeliveryFee(subtotal);
        double tax = taxCalculator.calculateTax(subtotal + deliveryFee);
        return subtotal + deliveryFee + tax;
    }

    public double getDeliveryFee() {
        if(deliveryFeeCalculator != null) {
            double subtotal = getSubtotal();
            return deliveryFeeCalculator.calculateDeliveryFee(subtotal);
        }
        return 0;
    }

    public double getDeliveryFee(double subtotal) {
        if(deliveryFeeCalculator != null) {
            return deliveryFeeCalculator.calculateDeliveryFee(subtotal);
        }
        return 0;
    }

    public double getTax() {
        if(taxCalculator != null) {
            double subtotal = getSubtotal();
            double deliveryFee = deliveryFeeCalculator.calculateDeliveryFee(subtotal);
            return taxCalculator.calculateTax(subtotal + deliveryFee);
        }
        return 0;
    }

    public double getTax(double subtotal) {
        if(taxCalculator != null) {
            return taxCalculator.calculateTax(subtotal);
        }
        return 0;
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (Map.Entry<Food, Integer> entry : items.entrySet()) {
            Food menuItem = entry.getKey();
            int itemQuantity = entry.getValue();
            subtotal += Float.parseFloat(menuItem.getPrice()) * itemQuantity;
        }
        return subtotal;
    }

    public int getQuantity(Food item) {
        if (this.items.containsKey(item)) {
            return this.items.get(item);
        }
        return 0;
    }

    public void clear() {
        items.clear();
    }
}


