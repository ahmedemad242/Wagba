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
import com.example.wagba.model.Food;
import com.example.wagba.model.Restaurant;
import com.example.wagba.utils.ImageUtils;

import java.util.Collections;
import java.util.List;

public class PopularFoodAdapter extends RecyclerView.Adapter<PopularFoodAdapter.FoodViewHolder> {
    private Context context;
    private List<Restaurant> restaurantList;

    public PopularFoodAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_row_item, parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        List<Food> menuItems = restaurantList.get(position).getMenuItems();
        Collections.shuffle(menuItems);

        Food randomFood = menuItems.get(0);
        ImageUtils.loadImage(context, randomFood.getImageUrl(), holder.image, R.drawable.logo_bg_light);
        holder.name.setText(randomFood.getName());
        holder.price.setText(randomFood.getPrice());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RestaurantDetails.class);
            intent.putExtra("restaurant", restaurantList.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static final class FoodViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView price, name;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.food_image);
            name = itemView.findViewById(R.id.food_name);
            price = itemView.findViewById(R.id.food_price);

        }
    }

}
