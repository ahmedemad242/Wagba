package com.example.wagba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.model.Food;

import java.util.List;

public class RestaurantFoodAdapter extends RecyclerView.Adapter<RestaurantFoodAdapter.RestaurantFoodViewHolder> {
    private Context context;
    private List<Food> foodList;

    public RestaurantFoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public RestaurantFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.resturant_food_row_item, parent,false);
        return new RestaurantFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantFoodViewHolder holder, int position) {
        holder.image.setImageResource(foodList.get(position).getImageUrl());
        holder.name.setText(foodList.get(position).getName());
        holder.price.setText(foodList.get(position).getPrice());
        holder.description.setText(foodList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static final class RestaurantFoodViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name, description, price;

        public RestaurantFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.restaurant_food_image);
            name = itemView.findViewById(R.id.restaurant_food_name);
            description = itemView.findViewById(R.id.restaurant_food_description);
            price = itemView.findViewById(R.id.restaurant_food_price);
        }
    }
}
