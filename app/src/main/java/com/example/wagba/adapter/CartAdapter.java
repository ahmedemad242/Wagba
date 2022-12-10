package com.example.wagba.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.model.CartItem;
import com.example.wagba.model.Food;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<CartItem> cartList;

    public CartAdapter(Context context, List<CartItem> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_row_item, parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Double price = Double.valueOf(cartList.get(position).getFood().getPrice());

        holder.image.setImageResource(cartList.get(position).getFood().getImageUrl());
        holder.price.setText(cartList.get(position).getFood().getPrice());
        holder.title.setText(cartList.get(position).getFood().getName());
        holder.quantity.setText(String.valueOf(cartList.get(position).getQuantity()));
        holder.total.setText(String.format(Locale.getDefault(), "%.2f",
                price*cartList.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static final class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView price, total, title, quantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cart_item_image);
            total = itemView.findViewById(R.id.cart_item_total);
            price = itemView.findViewById(R.id.cart_item_price);
            title = itemView.findViewById(R.id.cart_item_title);
            quantity = itemView.findViewById(R.id.cart_item_number);

        }
    }

}
