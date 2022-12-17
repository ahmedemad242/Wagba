package com.example.wagba.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.model.Cart;
import com.example.wagba.model.Food;
import com.example.wagba.utils.ImageUtils;
import java.util.List;

public class RestaurantFoodAdapter extends RecyclerView.Adapter<RestaurantFoodAdapter.RestaurantFoodViewHolder> {
    private Context context;
    private List<Food> foodList;
    private Cart cart;

    public RestaurantFoodAdapter(Context context, List<Food> foodList, Cart cart) {
        this.context = context;
        this.foodList = foodList;
        this.cart = cart;
    }

    @NonNull
    @Override
    public RestaurantFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.resturant_food_row_item, parent,false);
        return new RestaurantFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantFoodViewHolder holder, int position) {

        ImageUtils.loadImage(context, foodList.get(position).getImageUrl(), holder.image, R.drawable.logo_bg_light);

        holder.name.setText(foodList.get(position).getName());
        holder.price.setText(foodList.get(position).getPrice());
        holder.description.setText(foodList.get(position).getDescription());
        holder.quantity.setText(String.valueOf(cart.getQuantity(foodList.get(position))));

        holder.plusBtn.setOnClickListener(v -> {
            int quantity = Integer.parseInt((String) holder.quantity.getText());
            holder.quantity.setText(String.valueOf(quantity + 1));
            cart.plus(foodList.get(position));
            Log.d("cart", cart.items.toString());
        });

        holder.minusBtn.setOnClickListener(v -> {
            int quantity = Integer.parseInt((String) holder.quantity.getText());
            if (quantity > 0) {
                holder.quantity.setText(String.valueOf(quantity - 1));
                cart.minus(foodList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static final class RestaurantFoodViewHolder extends RecyclerView.ViewHolder{

        ImageView image, plusBtn, minusBtn;
        TextView name, description, price, quantity;

        public RestaurantFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.restaurant_food_image);
            name = itemView.findViewById(R.id.restaurant_food_name);
            description = itemView.findViewById(R.id.restaurant_food_description);
            price = itemView.findViewById(R.id.restaurant_food_price);
            plusBtn = itemView.findViewById(R.id.restaurant_food_plus_btn);
            minusBtn = itemView.findViewById(R.id.restaurant_food_minus_btn);
            quantity = itemView.findViewById(R.id.restaurant_food_count);
        }
    }
}
