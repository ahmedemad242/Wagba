package com.example.wagba;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.adapter.CartAdapter;
import com.example.wagba.databinding.ActivityCartBinding;
import com.example.wagba.feeCalculator.delivery.FlatRateDeliveryFeeCalculator;
import com.example.wagba.feeCalculator.tax.FlatTaxCalculator;
import com.example.wagba.model.Cart;
import com.example.wagba.model.CartItem;
import com.example.wagba.model.Order;
import com.example.wagba.model.OrderItem;
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
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding activityCartBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cart cart = Cart.getInstance();
        activityCartBinding = ActivityCartBinding.inflate(getLayoutInflater());
        Objects.requireNonNull(getSupportActionBar()).setTitle("My Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(activityCartBinding.getRoot());
        Window window = this.getWindow();
        WindowController.changeNavigationBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.white));
        WindowController.changeStatusBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.dark_blue), false);

        cart.setTaxCalculator(new FlatTaxCalculator(0.14));
        cart.setDeliveryFeeCalculator(new FlatRateDeliveryFeeCalculator(10.00));

        Runnable updateCartUi = () -> {
            boolean isCartEmpty = cart.getCartItems().isEmpty();
            double subtotal = cart.getSubtotal();

            activityCartBinding.cartItemsTotal.setText(
                    String.format(Locale.getDefault(), "%.2f", isCartEmpty ? 0 : subtotal));
            activityCartBinding.cartDeliveryTotal.setText(
                    String.format(Locale.getDefault(), "%.2f", isCartEmpty ? 0 : cart.getDeliveryFee(subtotal)));
            activityCartBinding.cartTaxTotal.setText(
                    String.format(Locale.getDefault(), "%.2f", isCartEmpty ? 0 : cart.getTax(subtotal)));
            activityCartBinding.cartTotal.setText(
                    String.format(Locale.getDefault(), "%.2f", isCartEmpty ? 0 : cart.getTotalCost()));
        };

        updateCartUi.run();
        setCartItemRecycler(Cart.getInstance().getFoodIdList(), updateCartUi);

        activityCartBinding.checkOutButton.setOnClickListener(v -> {
            if(cart.getCartItems().size() != 0){
                showConfirmationDialog(cart);
            }
            else{
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCartItemRecycler(List<String> foodIdList, Runnable updateCartUi){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        activityCartBinding.cartRecyclerView.setLayoutManager(layoutManager);
        CartAdapter cartAdapter = new CartAdapter(this, foodIdList, updateCartUi);
        activityCartBinding.cartRecyclerView.setAdapter(cartAdapter);
    }


    private void showConfirmationDialog(Cart cart) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose delivery time");
        builder.setMessage("delivery at 12:00 noon must order before 10:00 am.\n" +
                "delivery at 3:00 pm must order before 1:00 pm");
        LocalDateTime now = LocalDateTime.now();
        if (now.getHour() < 10) {
            builder.setNegativeButton("12:00PM", (dialog, which) -> {
                saveCartToFirebase(cart, "12:00");
                startActivity(new Intent(CartActivity.this, HistoryActivity.class));
                finish();
            });
        }
        if (now.getHour() < 13) {
            builder.setPositiveButton("3:00PM", (dialog, which) -> {
                saveCartToFirebase(cart, "15:00");
                startActivity(new Intent(CartActivity.this, HistoryActivity.class));
                finish();
            });
        }
        if (now.getHour() >= 13) {
            builder.setPositiveButton("No Delivery Available Now!", (dialog, which) -> {

            });
        }

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void saveCartToFirebase(Cart cart, String deliverySlot){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = Objects.requireNonNull(user).getUid();
        DatabaseReference userOrderRef = FirebaseDatabase
                .getInstance("https://wagba-cadcf-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users").child(userId).child("orders").push();
        String orderId = userOrderRef.getKey();

        DatabaseReference restaurantOrderRef = FirebaseDatabase
                .getInstance("https://wagba-cadcf-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("restaurants").child(cart.getRestaurant().getId()).child("orders").child(Objects.requireNonNull(orderId));

        Order order = cartToOrder(cart, orderId, userId, deliverySlot);
        userOrderRef.setValue(order);
        restaurantOrderRef.setValue(order);
        cart.clear();
    }

    private Order cartToOrder(Cart cart, String orderId, String userId, String deliverySlot){
        Order order = new Order();
        order.setOrderId(orderId);
        order.setPrice(String.valueOf(cart.getTotalCost()));
        order.setDeliveryFee(String.valueOf(cart.getDeliveryFee()));
        order.setTaxFee(String.valueOf(cart.getTax()));
        order.setOrderDate(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        order.setStatus("placed");
        order.setDeliverySlot(deliverySlot);
        order.setRestaurantId(cart.getRestaurant().getId());
        order.setOrderItems(cartItemToOrderItem(cart.getCartItems()));
        order.setUserId(userId);
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


}