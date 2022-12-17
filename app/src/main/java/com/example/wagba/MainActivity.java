package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.wagba.adapter.PopularFoodAdapter;
import com.example.wagba.adapter.RestaurantAdapter;
import com.example.wagba.databinding.ActivityMainBinding;
import com.example.wagba.model.Restaurant;
import com.example.wagba.utils.WindowController;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding activityMainBinding;
    private PopularFoodAdapter popularFoodAdapter;
    private RestaurantAdapter restaurantAdapter;
    private Window window;
    private ActionBarDrawerToggle toggle;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference restaurantRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://wagba-cadcf-default-rtdb.europe-west1.firebasedatabase.app/");
        restaurantRef = database.getReference("restaurants");


        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        window = this.getWindow();

        WindowController.changeNavigationBarColor(window, getResources().getColor(R.color.secondary_blue));
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.white), true);




        restaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Restaurant> restaurants = new ArrayList<>();
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    Restaurant restaurant = childSnapshot.getValue(Restaurant.class);
                    restaurants.add(restaurant);
                }
                Collections.shuffle(restaurants);
                setRestaurantRecycler(restaurants);
                setPopularFoodRecycler(restaurants.subList(0, 5));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //TODO:: Handle errors
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


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


        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });


    }

    private void setPopularFoodRecycler(List<Restaurant> foodList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        activityMainBinding.foodRecycleView.setLayoutManager(layoutManager);
        popularFoodAdapter = new PopularFoodAdapter(this, foodList);
        activityMainBinding.foodRecycleView.setAdapter(popularFoodAdapter);
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
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            case R.id.historyDrawerButton:
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                break;
            case R.id.cartDrawerButton:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                break;
            case R.id.logOutDrawerButton:
                firebaseAuth.signOut();
        }

        activityMainBinding.drawer.closeDrawer(GravityCompat.END);
        return true;
    }

}