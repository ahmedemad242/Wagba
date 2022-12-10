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

import com.example.wagba.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_row_item, parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.status.setText(orderList.get(position).getStatus());
        holder.orderDate.setText(orderList.get(position).getOrderDate());
        holder.orderId.setText("Order # " + orderList.get(position).getOrderId());
        holder.price.setText(orderList.get(position).getPrice() + "LE");
        holder.numberOfObjects.setText("Number of Items:" + String.valueOf(orderList.get(position).getNumberOfObjects()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static final class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView price, numberOfObjects, status, orderDate, orderId;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            orderDate = itemView.findViewById(R.id.order_date);
            numberOfObjects = itemView.findViewById(R.id.order_item_numbers);
            price = itemView.findViewById(R.id.order_price);
            status = itemView.findViewById(R.id.order_status);
            orderId = itemView.findViewById(R.id.order_number);

        }
    }

}
