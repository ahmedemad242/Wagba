package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.adapter.CartAdapter;
import com.example.wagba.databinding.ActivityCartBinding;
import com.example.wagba.model.Cart;
import com.example.wagba.model.CartItem;
import com.example.wagba.model.Order;
import com.example.wagba.model.OrderItem;
import com.example.wagba.model.Restaurant;
import com.example.wagba.utils.WindowController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding activityCartBinding;
    private CartAdapter cartAdapter;
    private Window window;
    private Runnable updateCartUi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCartBinding = ActivityCartBinding.inflate(getLayoutInflater());
        getSupportActionBar().setTitle("My Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(activityCartBinding.getRoot());
        window = this.getWindow();
        WindowController.changeNavigationBarColor(window, getResources().getColor(R.color.white));
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.dark_blue), false);

        updateCartUi = () -> {
            Cart cart = Cart.getInstance();
            double subtotal = cart.getSubtotal();

            activityCartBinding.cartItemsTotal.setText(
                    String.format(Locale.getDefault(), "%.2f", subtotal));
            activityCartBinding.cartDeliveryTotal.setText(
                    String.format(Locale.getDefault(), "%.2f", cart.getDeliveryFee(subtotal)));
            activityCartBinding.cartTaxTotal.setText(
                    String.format(Locale.getDefault(), "%.2f", cart.getTax(subtotal)));
            activityCartBinding.cartTotal.setText(
                    String.format(Locale.getDefault(), "%.2f", cart.getTotalCost()));
        };

        updateCartUi.run();
        setCartItemRecycler(Cart.getInstance().getFoodIdList(), updateCartUi);

        activityCartBinding.checkOutButton.setOnClickListener(v -> {
            Cart cart = Cart.getInstance();
            if(cart.getCartItems().size() != 0){
                showConfirmationDialog();
//                saveCartToFirebase(cart);
//                startActivity(new Intent(CartActivity.this, HistoryActivity.class));
//                finish();
            }
            else{
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCartItemRecycler(List<String> foodIdList, Runnable updateCartUi){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        activityCartBinding.cartRecyclerView.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(this, foodIdList, updateCartUi);
        activityCartBinding.cartRecyclerView.setAdapter(cartAdapter);
    }

    private void saveCartToFirebase(Cart cart, String deliverySlot){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference userOrderRef = FirebaseDatabase
                .getInstance("https://wagba-cadcf-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users").child(userId).child("orders").push();
        String orderId = userOrderRef.getKey();

        DatabaseReference restaurantOrderRef = FirebaseDatabase
                .getInstance("https://wagba-cadcf-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("restaurants").child(cart.getRestaurant().getId()).child("orders").child(orderId);

        Order order = cartToOrder(cart, orderId);
        userOrderRef.setValue(order);
        restaurantOrderRef.setValue(order);
        cart.clear();
    }

    private Order cartToOrder(Cart cart, String orderId){
        Order order = new Order();
        order.setOrderId(orderId);
        order.setPrice(String.valueOf(cart.getTotalCost()));
        order.setOrderDate(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        order.setStatus("placed");
        order.setRestaurantId(cart.getRestaurant().getId());
        order.setOrderItems(cartItemToOrderItem(cart.getCartItems()));
        return order;
    }

    private List<OrderItem> cartItemToOrderItem(List<CartItem> cartItems){
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            orderItems.add(new OrderItem(cartItem.getFood().getName(),
                    String.valueOf(cartItem.getQuantity()),
                    cartItem.getFood().getPrice()));
        }
        return orderItems;
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose delivery time");
        builder.setMessage("delivery at 12:00 noon must order before 10:00 am.\n" +
                "delivery at 3:00 pm must order before 1:00 pm");
        LocalDateTime now = LocalDateTime.of(2000,9,9,15,32);
        if (now.getHour() < 10) {
            builder.setNegativeButton("12:00PM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        if (now.getHour() < 13) {
            builder.setPositiveButton("3:00PM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        if (now.getHour() >= 13) {
            builder.setPositiveButton("No Delivery Available Now!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}