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

import java.util.List;

public class RestaurantDetails extends AppCompatActivity {

    private ActivityResturantDetailsBinding activityResturantDetailsBinding;
    private RestaurantFoodAdapter restaurantFoodAdapter;
    private Window window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResturantDetailsBinding = ActivityResturantDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityResturantDetailsBinding.getRoot());
        window = this.getWindow();

        WindowController.changeNavigationBarColor(window, getResources().getColor(R.color.secondary_blue));
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.white), true);

        Restaurant restaurant = getIntent().getParcelableExtra("restaurant");

        ImageUtils.loadImage(this, restaurant.getImageUrl(),
                activityResturantDetailsBinding.restaurantDetailsImage, R.drawable.logo_bg_light);

        activityResturantDetailsBinding.restaurantDetailsDish.setText(
                String.valueOf(restaurant.getMenuItems().size()));
        activityResturantDetailsBinding.restaurantDetailsRating.setText(restaurant.getRating());
        activityResturantDetailsBinding.restaurantDetailsOrders.setText("10");
        setFoodRecycler(restaurant);

        activityResturantDetailsBinding.restaurantDetailsCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantDetails.this, CartActivity.class);
                startActivity(intent);
            }
        });


        activityResturantDetailsBinding.restaurantFoodHome.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void setFoodRecycler(Restaurant restaurant){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        activityResturantDetailsBinding.restaurantFoodRecyclerView.setLayoutManager(layoutManager);
        restaurantFoodAdapter = new RestaurantFoodAdapter(this, restaurant);
        activityResturantDetailsBinding.restaurantFoodRecyclerView.setAdapter(restaurantFoodAdapter);
    }


}