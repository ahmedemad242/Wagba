package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.example.wagba.adapter.FoodAdapter;
import com.example.wagba.databinding.ActivityMainBinding;
import com.example.wagba.model.Food;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        List<Food> foodList = new ArrayList<Food>();
        foodList.add(new Food("Cake", "7.43 LE", R.drawable.ic_launcher_background));
        foodList.add(new Food("Food", "24.72 LE", R.drawable.ic_launcher_background));
        foodList.add(new Food("Italian pasta", "119.1 LE", R.drawable.ic_launcher_background));
        foodList.add(new Food("LOL", "1219.1 LE", R.drawable.ic_launcher_background));

        setFoodRecycler(foodList);

    }

    private void setFoodRecycler(List<Food>foodList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        activityMainBinding.foodRecycleView.setLayoutManager(layoutManager);
        foodAdapter = new FoodAdapter(this, foodList);
        activityMainBinding.foodRecycleView.setAdapter(foodAdapter);
    }
}