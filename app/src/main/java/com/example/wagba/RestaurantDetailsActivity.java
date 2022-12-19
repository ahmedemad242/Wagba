package com.example.wagba;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.adapter.RestaurantFoodAdapter;
import com.example.wagba.databinding.ActivityResturantDetailsBinding;
import com.example.wagba.model.Cart;
import com.example.wagba.model.Food;
import com.example.wagba.model.Restaurant;
import com.example.wagba.utils.ImageUtils;
import com.example.wagba.utils.WindowController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsActivity extends AppCompatActivity {

    private ActivityResturantDetailsBinding activityResturantDetailsBinding;
    private RestaurantFoodAdapter restaurantFoodAdapter;
    private Window window;
    FirebaseDatabase database;
    DatabaseReference foodRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResturantDetailsBinding = ActivityResturantDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityResturantDetailsBinding.getRoot());
        window = this.getWindow();
        database = FirebaseDatabase.getInstance("https://wagba-cadcf-default-rtdb.europe-west1.firebasedatabase.app/");


        WindowController.changeNavigationBarColor(window, getResources().getColor(R.color.secondary_blue));
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.white), true);

        Restaurant restaurant = getIntent().getParcelableExtra("restaurant");

        String path = "/restaurants/" + restaurant.getId() + "/menuItems";
        foodRef = database.getReference(path);

        foodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
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
            public void onCancelled(DatabaseError error) {
                // Handle errors here
            }});

        ImageUtils.loadImage(this, restaurant.getImageUrl(),
                activityResturantDetailsBinding.restaurantDetailsImage, R.drawable.logo_bg_light);


        activityResturantDetailsBinding.restaurantDetailsRating.setText(restaurant.getRating());
        //TODO:: Fill order number
        activityResturantDetailsBinding.restaurantDetailsOrders.setText(String.valueOf(restaurant.getOrderCount()));


        activityResturantDetailsBinding.restaurantDetailsCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });


        activityResturantDetailsBinding.restaurantFoodHome.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void setFoodRecycler(Restaurant restaurant, List<Food> foodList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        activityResturantDetailsBinding.restaurantFoodRecyclerView.setLayoutManager(layoutManager);
        restaurantFoodAdapter = new RestaurantFoodAdapter(this, restaurant, foodList);
        activityResturantDetailsBinding.restaurantFoodRecyclerView.setAdapter(restaurantFoodAdapter);
    }


}