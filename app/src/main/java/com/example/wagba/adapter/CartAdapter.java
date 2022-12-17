package com.example.wagba.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.model.Cart;
import com.example.wagba.model.Food;
import com.example.wagba.model.Restaurant;
import com.example.wagba.utils.ImageUtils;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<String> foodIdList;
    private Runnable updateCartUi;

    public CartAdapter(Context context, List<String> foodIdList, Runnable updateCartUi) {
        this.context = context;
        this.foodIdList = foodIdList;
        this.updateCartUi = updateCartUi;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_row_item, parent,false);
        return new CartViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Restaurant restaurant = Cart.getInstance().getRestaurant();
        Cart cart = Cart.getInstance();
        Food currentFood = cart.getFood(foodIdList.get(position));
        Double price = Double.valueOf(currentFood.getPrice());

        ImageUtils.loadImage(context, currentFood.getImageUrl(), holder.image, R.drawable.logo_bg_light);
        holder.price.setText(currentFood.getPrice());
        holder.title.setText(currentFood.getName());
        holder.quantity.setText(
                String.valueOf(Cart.getInstance().getQuantity(foodIdList.get(position))));
        holder.total.setText(
                String.format(Locale.getDefault(), "%.2f",
                price*Cart.getInstance().getQuantity(foodIdList.get(position))));

        holder.plusBtn.setOnClickListener(v -> {
            int quantity = Integer.parseInt((String) holder.quantity.getText());
            holder.quantity.setText(String.valueOf(quantity + 1));
            Cart.getInstance().plus(cart.getFood(foodIdList.get(position)));
            updateCartUi.run();
        });

        holder.minusBtn.setOnClickListener(v -> {
            int quantity = Integer.parseInt((String) holder.quantity.getText());
            if (quantity > 0) {
                holder.quantity.setText(String.valueOf(quantity - 1));
                Cart.getInstance().minus(cart.getFood(foodIdList.get(position)));
                updateCartUi.run();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodIdList.size();
    }

    public static final class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView image, plusBtn, minusBtn;
        TextView price, total, title, quantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cart_item_image);
            total = itemView.findViewById(R.id.cart_item_total);
            price = itemView.findViewById(R.id.cart_item_price);
            title = itemView.findViewById(R.id.cart_item_title);
            plusBtn = itemView.findViewById(R.id.cart_plus_btn);
            minusBtn = itemView.findViewById(R.id.cart_minus_btn);
            quantity = itemView.findViewById(R.id.cart_count);
        }
    }

}
