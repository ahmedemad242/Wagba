package com.example.wagba;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.adapter.RestaurantFoodAdapter;
import com.example.wagba.databinding.ActivityResturantDetailsBinding;
import com.example.wagba.model.Food;
import com.example.wagba.model.Restaurant;
import com.example.wagba.utils.ImageUtils;
import com.example.wagba.utils.WindowController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsActivity extends AppCompatActivity {

    private ActivityResturantDetailsBinding activityResturantDetailsBinding;
    private FirebaseDatabase database;
    private Restaurant restaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResturantDetailsBinding = ActivityResturantDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityResturantDetailsBinding.getRoot());
        Window window = this.getWindow();
        database = FirebaseDatabase.getInstance("https://wagba-cadcf-default-rtdb.europe-west1.firebasedatabase.app/");


        WindowController.changeNavigationBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.secondary_blue));
        WindowController.changeStatusBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.white), true);

        restaurant = getIntent().getParcelableExtra("restaurant");



        ImageUtils.loadImage(this, restaurant.getImageUrl(),
                activityResturantDetailsBinding.restaurantDetailsImage, R.drawable.logo_bg_light);


        activityResturantDetailsBinding.restaurantDetailsRating.setText(restaurant.getRating());
        //TODO:: Fill order number

        activityResturantDetailsBinding.restaurantDetailsCartBtn.setOnClickListener(view -> {
            Intent intent = new Intent(RestaurantDetailsActivity.this, CartActivity.class);
            startActivity(intent);
        });


        activityResturantDetailsBinding.restaurantFoodHome.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseReference foodRef = database.getReference("/restaurants/" + restaurant.getId() + "/menuItems");
        DatabaseReference orderCountRef = database.getReference("/restaurants/" + restaurant.getId() + "/orderCount");

        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Food> foodList = new ArrayList<>();
                for (DataSnapshot dishSnapshot : snapshot.getChildren()) {
                    Food food = dishSnapshot.getValue(Food.class);
                    foodList.add(food);
                }
                setFoodRecycler(restaurant, foodList);

                activityResturantDetailsBinding.restaurantDetailsDish.setText(
                        String.valueOf(foodList.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors here
            }});

        orderCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                activityResturantDetailsBinding.restaurantDetailsOrders.setText(
                        String.valueOf(snapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors here
            }});
    }

    private void setFoodRecycler(Restaurant restaurant, List<Food> foodList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        activityResturantDetailsBinding.restaurantFoodRecyclerView.setLayoutManager(layoutManager);
        RestaurantFoodAdapter restaurantFoodAdapter = new RestaurantFoodAdapter(this, restaurant, foodList);
        activityResturantDetailsBinding.restaurantFoodRecyclerView.setAdapter(restaurantFoodAdapter);
    }


}