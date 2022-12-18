package com.example.wagba.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.model.Order;
import com.example.wagba.model.OrderItem;

import java.util.List;
import java.util.Locale;

public class OrderExpandableAdapter extends RecyclerView.Adapter<OrderExpandableAdapter.ViewHolder> {
    private Context context;
    private List<Order> orders;
    private OnItemClickListener listener;
    private SparseBooleanArray expandedGroups;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    public OrderExpandableAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
        this.expandedGroups = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row_item, parent, false);
        listener = new OrderExpandableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                // Toggle the expanded state of the group
                toggleGroup(position);
            }
        };
        return new ViewHolder(view, listener);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderId.setText(order.getOrderId());
        holder.orderStatus.setText(order.getStatus());
        holder.orderItemNumbers.setText(String.valueOf(order.getOrderItems().size()));
        holder.orderDate.setText(order.getOrderDate());

        holder.orderItemsContainer.removeAllViews();
        if (expandedGroups.get(position)) {
            holder.orderItemsContainer.setVisibility(View.VISIBLE);

            for (OrderItem orderItem : order.getOrderItems()) {
                View itemView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.order_item_row_item, holder.orderItemsContainer, false);
                TextView orderItemName = itemView.findViewById(R.id.order_item_name);
                TextView orderItemPrice = itemView.findViewById(R.id.order_item_price);
                TextView orderItemQuantity = itemView.findViewById(R.id.order_item_quantity);
                TextView orderItemTotalPrice = itemView.findViewById(R.id.order_item_total_price);
                orderItemName.setText(orderItem.getName());
                orderItemPrice.setText(String.format(Locale.getDefault(),"%.2f", Float.parseFloat(orderItem.getPrice())));
                orderItemQuantity.setText(String.valueOf(orderItem.getQuantity()));
                orderItemTotalPrice.setText(String.format(Locale.getDefault(),"%.2f", Float.parseFloat(orderItem.getPrice())* Integer.parseInt(orderItem.getQuantity())));

                holder.orderItemsContainer.addView(itemView);
            }
        } else {
            holder.orderItemsContainer.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderId, orderStatus, orderItemNumbers, orderDate;
        LinearLayout orderItemsContainer;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_number);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderItemNumbers = itemView.findViewById(R.id.order_item_numbers);
            orderItemsContainer = itemView.findViewById(R.id.order_items_container);
            orderDate = itemView.findViewById(R.id.order_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }

    public void toggleGroup(int position) {
        if (expandedGroups.get(position)) {
            expandedGroups.delete(position);
        } else {
            expandedGroups.put(position, true);
        }
        notifyDataSetChanged();
    }

    public void collapseAllGroups() {
        expandedGroups.clear();
        notifyDataSetChanged();
    }

}
