package com.example.wagba.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.RestaurantDetails;
import com.example.wagba.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private Context context;
    private List<Restaurant> restaurantList;

    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_row_item, parent,false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.image.setImageResource(restaurantList.get(position).getImageUrl());
        holder.name.setText(restaurantList.get(position).getName());
        holder.description.setText(restaurantList.get(position).getDescription());
        holder.rating.setText(restaurantList.get(position).getRating());

        holder.itemView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, RestaurantDetails.class));

        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static final class RestaurantViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView description, name, rating;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.restaurant_food_image);
            name = itemView.findViewById(R.id.restaurant_food_name);
            description = itemView.findViewById(R.id.restaurant_food_description);
            rating = itemView.findViewById(R.id.restaurant_rating);
        }
    }

}
