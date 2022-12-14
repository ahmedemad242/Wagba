package com.example.wagba.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.model.Order;
import com.example.wagba.model.OrderItem;
import com.example.wagba.utils.StatusColorMapper;

import java.util.List;
import java.util.Locale;

public class OrderExpandableAdapter extends RecyclerView.Adapter<OrderExpandableAdapter.ViewHolder> {
    private final Context context;
    private final List<Order> orders;
    private final SparseBooleanArray expandedGroups;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row_item, parent, false);
        // Toggle the expanded state of the group
        OnItemClickListener listener = (itemView, position) -> {
            // Toggle the expanded state of the group
            toggleGroup(position);
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
        holder.orderTime.setText(order.getDeliverySlot());
        holder.orderPrice.setText(String.format(Locale.getDefault(),"%.2f",Float.parseFloat(order.getPrice())));
        holder.orderStatus.setTextColor(ContextCompat.getColor(context.getApplicationContext(),StatusColorMapper.getColorForStatus(order.getStatus())));

        holder.orderItemsContainer.removeAllViews();
        if (expandedGroups.get(position) && order.getOrderItems().size() > 0) {
            holder.orderItemsContainer.setVisibility(View.VISIBLE);

            for (OrderItem orderItem : order.getOrderItems()) {
                addOrderItemView(holder, orderItem.getName(), orderItem.getPrice(), orderItem.getQuantity());
            }
            addFeeItemView(holder, "Delivery Fee", order.getDeliveryFee());
            addFeeItemView(holder, "Tax Fee", order.getTaxFee());

        } else {
            holder.orderItemsContainer.setVisibility(View.GONE);
        }
    }

    private void addOrderItemView(ViewHolder holder, String name, String price, String quantity) {
        View itemView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.order_item_row_item, holder.orderItemsContainer, false);
        TextView orderItemName = itemView.findViewById(R.id.order_item_name);
        TextView orderItemPrice = itemView.findViewById(R.id.order_item_price);
        TextView orderItemQuantity = itemView.findViewById(R.id.order_item_quantity);
        TextView orderItemTotalPrice = itemView.findViewById(R.id.order_item_total_price);
        orderItemName.setText(name);
        orderItemPrice.setText(String.format(Locale.getDefault(),"%.2f", Float.parseFloat(price)));
        orderItemQuantity.setText(String.valueOf(quantity));
        orderItemTotalPrice.setText(String.format(Locale.getDefault(),"%.2f", Float.parseFloat(price)* Integer.parseInt(quantity)));
        holder.orderItemsContainer.addView(itemView);
    }

    private void addFeeItemView(ViewHolder holder, String name, String price) {
        View itemView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.order_item_row_item, holder.orderItemsContainer, false);
        TextView orderItemName = itemView.findViewById(R.id.order_item_name);
        TextView orderItemPrice = itemView.findViewById(R.id.order_item_price);
        TextView orderItemQuantity = itemView.findViewById(R.id.order_item_quantity);
        TextView orderItemTotalPrice = itemView.findViewById(R.id.order_item_total_price);
        TextView orderItemMultiply =itemView.findViewById(R.id.order_item_multiply);
        TextView orderItemCurrency =itemView.findViewById(R.id.order_item_currency);
        orderItemMultiply.setText("");
        orderItemCurrency.setText("");
        orderItemPrice.setText("");
        orderItemQuantity.setText("");
        orderItemName.setText(name);
        orderItemTotalPrice.setText(String.format(Locale.getDefault(),"%.2f", Float.parseFloat(price)));
        holder.orderItemsContainer.addView(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderId, orderStatus, orderItemNumbers, orderDate, orderPrice, orderTime;
        LinearLayout orderItemsContainer;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_number);
            orderStatus = itemView.findViewById(R.id.order_status);
            orderItemNumbers = itemView.findViewById(R.id.order_item_numbers);
            orderItemsContainer = itemView.findViewById(R.id.order_items_container);
            orderDate = itemView.findViewById(R.id.order_date);
            orderPrice = itemView.findViewById(R.id.order_price);
            orderTime = itemView.findViewById(R.id.order_time);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, position);
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
