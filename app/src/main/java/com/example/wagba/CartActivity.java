package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import com.example.wagba.adapter.CartAdapter;
import com.example.wagba.databinding.ActivityCartBinding;
import com.example.wagba.model.Cart;
import com.example.wagba.utils.WindowController;

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
}