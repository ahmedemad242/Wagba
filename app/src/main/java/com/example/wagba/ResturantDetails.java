package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;

import com.example.wagba.adapter.FoodAdapter;
import com.example.wagba.adapter.RestaurantFoodAdapter;
import com.example.wagba.databinding.ActivityCartBinding;
import com.example.wagba.databinding.ActivityResturantDetailsBinding;
import com.example.wagba.model.Food;

import java.util.ArrayList;
import java.util.List;

public class ResturantDetails extends AppCompatActivity {

    private ActivityResturantDetailsBinding activityResturantDetailsBinding;
    private RestaurantFoodAdapter restaurantFoodAdapter;
    private Window window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResturantDetailsBinding = ActivityResturantDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityResturantDetailsBinding.getRoot());
        window = this.getWindow();

        WindowController.changeNavigationBarColor(window, getResources().getColor(R.color.white));
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.white), true);

        List<Food> foodList = new ArrayList<Food>();
        foodList.add(new Food("Cake", "7.43 LE", R.drawable.ic_launcher_background, "Grilled chicken with beans"));
        foodList.add(new Food("Food", "24.72 LE", R.drawable.ic_launcher_background, "Pasta grilled on 340°C with a grain of salt"));
        foodList.add(new Food("Italian pasta", "119.1 LE", R.drawable.ic_launcher_background, "Lettuce, tomato, and cheese"));
        foodList.add(new Food("LOL", "1219.1 LE", R.drawable.ic_launcher_background, "The most exotic food ever tasted"));

        setFoodRecycler(foodList);
    }

    private void setFoodRecycler(List<Food> foodList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        activityResturantDetailsBinding.restaurantFoodRecyclerView.setLayoutManager(layoutManager);
        restaurantFoodAdapter = new RestaurantFoodAdapter(this, foodList);
        activityResturantDetailsBinding.restaurantFoodRecyclerView.setAdapter(restaurantFoodAdapter);
    }
}