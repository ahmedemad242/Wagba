package com.example.wagba.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.wagba.feeCalculator.delivery.DeliveryFeeCalculator;
import com.example.wagba.feeCalculator.tax.TaxCalculator;

import java.util.HashMap;
import java.util.Map;


public class Cart {
    public Map<String, Integer> items;
    private DeliveryFeeCalculator deliveryFeeCalculator;
    private TaxCalculator taxCalculator;
    private static Cart instance;
    private Restaurant restaurant;

    private Cart(Restaurant restaurant) {
        items = new HashMap<>();
        this.restaurant = restaurant;
    }

    public void setDeliveryFeeCalculator(DeliveryFeeCalculator deliveryFeeCalculator) {
        this.deliveryFeeCalculator = deliveryFeeCalculator;
    }

    public void setTaxCalculator(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }

    public static Cart getInstance(Restaurant restaurant, Context context) {
        if (instance == null) {
            instance = new Cart(restaurant);
        }
        if(!restaurant.getId().equals(instance.restaurant.getId())){
            showConfirmationDialog(restaurant, context);
        }
        return instance;
    }

    public void plus(String foodId) {
        int quantity = 1;
        if (items.containsKey(foodId)) {
            quantity += items.get(foodId);
        }
        items.put(foodId, quantity);
    }

    public void minus(String foodId) {
        if (items.containsKey(foodId)) {
            int quantity = items.get(foodId);
            if (quantity > 1) {
                items.put(foodId, quantity - 1);
            } else {
                items.remove(foodId);
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
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String foodId = entry.getKey();
            int itemQuantity = entry.getValue();
            subtotal += Float.parseFloat(restaurant.getFoodById(foodId).getPrice()) * itemQuantity;
        }
        return subtotal;
    }

    public int getQuantity(String foodId) {
        if (this.items.containsKey(foodId)) {
            return this.items.get(foodId);
        }
        return 0;
    }

    private static void showConfirmationDialog(Restaurant restaurant, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Clear cart?");
        builder.setMessage("You have a cart open in another restaurant, wanna clear that?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                instance.restaurant = restaurant;
                instance.clear();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void clear() {
        items.clear();
    }
}


