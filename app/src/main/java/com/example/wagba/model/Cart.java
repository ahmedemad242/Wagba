package com.example.wagba.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.wagba.feeCalculator.delivery.DeliveryFeeCalculator;
import com.example.wagba.feeCalculator.tax.TaxCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Cart {
    public Map<String, CartItem> items;
    private DeliveryFeeCalculator deliveryFeeCalculator;
    private TaxCalculator taxCalculator;
    private static Cart instance;
    private Restaurant restaurant;

    private Cart(Restaurant restaurant) {
        items = new HashMap<>();
        this.restaurant = restaurant;
    }

    private Cart() {
        items = new HashMap<>();
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
        if(instance.restaurant != null){
            if(!restaurant.getId().equals(instance.restaurant.getId())){
                showConfirmationDialog(restaurant, context);
            }
        }
        else {
            instance.restaurant = restaurant;
        }

        return instance;
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void plus(Food food) {
        int quantity = 1;
        if (items.containsKey(food.getId())) {
            quantity += items.get(food.getId()).getQuantity();
        }
        items.put(food.getId(), new CartItem(food, quantity));
    }

    public void minus(Food food) {
        if (items.containsKey(food.getId())) {
            int quantity = items.get(food.getId()).getQuantity();
            if (quantity > 1) {
                items.put(food.getId(), new CartItem(food, quantity - 1) );
            } else {
                items.remove(food.getId());
            }
        }
    }

    public double getTotalCost() {
        double subtotal = getSubtotal();
        double deliveryFee = getDeliveryFee(subtotal);
        double tax = getTax(subtotal + deliveryFee);
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
        for (Map.Entry<String, CartItem> entry : items.entrySet()) {
            String foodId = entry.getKey();
            int itemQuantity = entry.getValue().getQuantity();
            subtotal += Float.parseFloat(items.get(foodId).getFood().getPrice()) * itemQuantity;
        }
        return subtotal;
    }

    public int getQuantity(String foodId) {
        if (this.items.containsKey(foodId)) {
            return this.items.get(foodId).getQuantity();
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
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public Food getFood(String foodId){
        return items.get(foodId).getFood();
    }

    public List<CartItem> getCartItems(){
        return items.values().stream().collect(Collectors.toList());
    }


    public List<String> getFoodIdList(){
        return new ArrayList<String>(items.keySet());

    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void clear() {
        items.clear();
    }
}


