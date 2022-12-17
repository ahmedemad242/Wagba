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
import com.example.wagba.RestaurantDetailsActivity;
import com.example.wagba.model.Restaurant;
import com.example.wagba.utils.ImageUtils;

import java.util.List;

public class PopularRestaurantAdapter extends RecyclerView.Adapter<PopularRestaurantAdapter.FoodViewHolder> {
    private Context context;
    private List<Restaurant> restaurantList;

    public PopularRestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_restaurant_row_item, parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        ImageUtils.loadImage(context, restaurantList.get(position).getImageUrl(), holder.image, R.drawable.logo_bg_light);
        holder.name.setText(restaurantList.get(position).getName());
        holder.price.setText(restaurantList.get(position).getRating());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RestaurantDetailsActivity.class);
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

            image = itemView.findViewById(R.id.popular_restaurant_image);
            name = itemView.findViewById(R.id.popular_restaurant_name);
            price = itemView.findViewById(R.id.popular_restaurant_rating);

        }
    }

}
