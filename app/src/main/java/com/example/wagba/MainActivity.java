package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.wagba.adapter.FoodAdapter;
import com.example.wagba.adapter.RestaurantAdapter;
import com.example.wagba.databinding.ActivityMainBinding;
import com.example.wagba.model.Food;
import com.example.wagba.model.Restaurant;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding activityMainBinding;
    private FoodAdapter foodAdapter;
    private RestaurantAdapter restaurantAdapter;
    private Window window;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        window = this.getWindow();

        WindowController.changeNavigationBarColor(window, getResources().getColor(R.color.secondary_blue));
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.white), true);


        List<Food> foodList = new ArrayList<Food>();
        foodList.add(new Food("Cake", "7.43 LE", R.drawable.ic_launcher_background));
        foodList.add(new Food("Food", "24.72 LE", R.drawable.ic_launcher_background));
        foodList.add(new Food("Italian pasta", "119.1 LE", R.drawable.ic_launcher_background));
        foodList.add(new Food("LOL", "1219.1 LE", R.drawable.ic_launcher_background));

        setFoodRecycler(foodList);

        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        restaurantList.add(new Restaurant("3am Sadam", "Best fool in the world",
                R.drawable.ic_launcher_background, "5.0"));
        restaurantList.add(new Restaurant("Cilantroooo", "Imagine Cilantro in Handasa ainshams lol",
                R.drawable.ic_launcher_background, "3.0"));
        restaurantList.add(new Restaurant("Macdonals", "Crazy prices due to inflation",
                R.drawable.ic_launcher_background, "1.0"));
        restaurantList.add(new Restaurant("Tybat Elsham", "Syrian Shawerma",
                R.drawable.ic_launcher_background, "4.8"));



        setRestaurantRecycler(restaurantList);

        activityMainBinding.drawerButton.setOnClickListener(v -> {
            activityMainBinding.drawer.openDrawer(GravityCompat.END);
        });

        toggle = new ActionBarDrawerToggle(this, activityMainBinding.drawer,
                R.string.nav_drawer_open, R.string.nav_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                WindowController.changeStatusBarColor(window, getResources().getColor(R.color.white), true);
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                WindowController.changeStatusBarColor(window, getResources().getColor(R.color.dark_blue), false);
            }
        };
        activityMainBinding.drawer.addDrawerListener(toggle);
        toggle.syncState();
        activityMainBinding.navigationView.setNavigationItemSelectedListener(this);

    }

    private void setFoodRecycler(List<Food> foodList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        activityMainBinding.foodRecycleView.setLayoutManager(layoutManager);
        foodAdapter = new FoodAdapter(this, foodList);
        activityMainBinding.foodRecycleView.setAdapter(foodAdapter);
    }

    private void setRestaurantRecycler(List<Restaurant> restaurantList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        activityMainBinding.restaurantRecycleView.setLayoutManager(layoutManager);
        restaurantAdapter = new RestaurantAdapter(this, restaurantList);
        activityMainBinding.restaurantRecycleView.setAdapter(restaurantAdapter);
    }

    @Override
    public void onBackPressed() {
        if (activityMainBinding.drawer.isDrawerOpen(GravityCompat.END)) {
            activityMainBinding.drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.profileDrawerButton:
                Toast.makeText(this, "Profile Press", Toast.LENGTH_SHORT).show();
                break;
            case R.id.historyDrawerButton:
                startActivity(new Intent(MainActivity.this, History.class));
                break;
            case R.id.cartDrawerButton:
                startActivity(new Intent(MainActivity.this, Cart.class));
                break;
        }

        activityMainBinding.drawer.closeDrawer(GravityCompat.END);
        return true;
    }

}