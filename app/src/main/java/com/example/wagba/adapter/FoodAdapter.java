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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private Context context;
    private List<Food> foodList;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_row_item, parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.image.setImageResource(foodList.get(position).getImageUrl());
        holder.name.setText(foodList.get(position).getName());
        holder.price.setText(foodList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
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
