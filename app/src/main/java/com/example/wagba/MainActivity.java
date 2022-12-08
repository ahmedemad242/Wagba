package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Window;

import com.example.wagba.adapter.FoodAdapter;
import com.example.wagba.adapter.RestaurantAdapter;
import com.example.wagba.databinding.ActivityMainBinding;
import com.example.wagba.model.Food;
import com.example.wagba.model.Restaurant;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private FoodAdapter foodAdapter;
    private RestaurantAdapter restaurantAdapter;
    private Window window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
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
}