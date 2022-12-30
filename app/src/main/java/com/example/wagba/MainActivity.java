package com.example.wagba;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.adapter.PopularRestaurantAdapter;
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
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding activityMainBinding;
    private Window window;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://wagba-cadcf-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference restaurantRef = database.getReference("restaurants");


        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());


        window = this.getWindow();
        WindowController.changeNavigationBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.secondary_blue));
        WindowController.changeStatusBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.white), true);


        restaurantRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Restaurant> restaurants = new ArrayList<>();
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    Restaurant restaurant = childSnapshot.getValue(Restaurant.class);
                    restaurants.add(restaurant);
                }
                setRestaurantRecycler(restaurants);
                setPopularFoodRecycler(restaurants.subList(0, 3));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO:: Handle errors
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        activityMainBinding.drawerButton.setOnClickListener(v -> activityMainBinding.drawer.openDrawer(GravityCompat.END));

        TextView drawerName = activityMainBinding.navigationView.getHeaderView(0)
                .findViewById(R.id.drawer_name);
        TextView drawerEmail = activityMainBinding.navigationView.getHeaderView(0)
                .findViewById(R.id.drawer_email);
        if(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getDisplayName() != null){
            drawerName.setText(firebaseAuth.getCurrentUser().getDisplayName());
        }
        else {
            drawerName.setText("");
        }
        drawerEmail.setText(firebaseAuth.getCurrentUser().getEmail());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, activityMainBinding.drawer,
                R.string.nav_drawer_open, R.string.nav_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                WindowController.changeStatusBarColor(window, ContextCompat.getColor(getApplicationContext(), R.color.white) , true);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                WindowController.changeStatusBarColor(window, ContextCompat.getColor(getApplicationContext(), R.color.dark_blue), false);
            }
        };

        activityMainBinding.drawer.addDrawerListener(toggle);
        toggle.syncState();
        activityMainBinding.navigationView.setNavigationItemSelectedListener(this);


        firebaseAuth.addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() == null) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    private void setPopularFoodRecycler(List<Restaurant> foodList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        activityMainBinding.foodRecycleView.setLayoutManager(layoutManager);
        PopularRestaurantAdapter popularFoodAdapter = new PopularRestaurantAdapter(this, foodList);
        activityMainBinding.foodRecycleView.setAdapter(popularFoodAdapter);
    }

    private void setRestaurantRecycler(List<Restaurant> restaurantList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        activityMainBinding.restaurantRecycleView.setLayoutManager(layoutManager);
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this, restaurantList);
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
        if(menuItem.getItemId() == R.id.profileDrawerButton){
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        } else if (menuItem.getItemId() == R.id.historyDrawerButton) {
            startActivity(new Intent(MainActivity.this, HistoryActivity.class));
        } else if (menuItem.getItemId() == R.id.cartDrawerButton) {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        } else if (menuItem.getItemId() == R.id.logOutDrawerButton) {
            firebaseAuth.signOut();
        }

        activityMainBinding.drawer.closeDrawer(GravityCompat.END);
        return true;
    }

}