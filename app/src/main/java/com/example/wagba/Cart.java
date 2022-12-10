package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.wagba.adapter.CartAdapter;
import com.example.wagba.adapter.FoodAdapter;
import com.example.wagba.adapter.RestaurantAdapter;
import com.example.wagba.databinding.ActivityCartBinding;
import com.example.wagba.model.CartItem;
import com.example.wagba.model.Food;
import com.example.wagba.model.Order;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    private ActivityCartBinding activityCartBinding;
    private CartAdapter cartAdapter;
    private Window window;


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

        List<CartItem> cartList = new ArrayList<CartItem>();
        cartList.add(new CartItem(new Food("Cake", "7.43", R.drawable.ic_launcher_background, "Sad"),2));
        cartList.add(new CartItem(new Food("Food", "24.72", R.drawable.ic_launcher_background, "Sad"),4));
        cartList.add(new CartItem(new Food("Italian pasta", "119.1", R.drawable.ic_launcher_background, "Sad"), 1));
        cartList.add(new CartItem(new Food("LOL", "1219.1", R.drawable.ic_launcher_background, "Sad"), 12));
        cartList.add(new CartItem(new Food("Cake", "7.43", R.drawable.ic_launcher_background, "Sad"),2));
        cartList.add(new CartItem(new Food("Food", "24.72", R.drawable.ic_launcher_background, "Sad"),4));
        cartList.add(new CartItem(new Food("Italian pasta", "119.1", R.drawable.ic_launcher_background, "Sad"), 1));

        setCartItemRecycler(cartList);

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

    private void setCartItemRecycler(List<CartItem> cartList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        activityCartBinding.cartRecyclerView.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(this, cartList);
        activityCartBinding.cartRecyclerView.setAdapter(cartAdapter);
    }
}