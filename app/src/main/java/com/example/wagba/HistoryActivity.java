package com.example.wagba;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.adapter.OrderExpandableAdapter;
import com.example.wagba.databinding.ActivityHistoryBinding;
import com.example.wagba.model.Order;
import com.example.wagba.model.OrderItem;
import com.example.wagba.utils.WindowController;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding activityHistoryBinding;
    private OrderExpandableAdapter orderAdapter;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHistoryBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        getSupportActionBar().setTitle("History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(activityHistoryBinding.getRoot());
        window = this.getWindow();

        WindowController.changeNavigationBarColor(window, getResources().getColor(R.color.white));
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.dark_blue), false);

        List<Order> orderList = new ArrayList<Order>();
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(new OrderItem("burger", "3", "15"));
        orderItemList.add(new OrderItem("burger1", "5", "17"));
        orderItemList.add(new OrderItem("burger2", "1", "90"));

        orderList.add(new Order("resfcr","22/11/2022", "190.12",
                "usr1or3", "Delivered", orderItemList));
        orderList.add(new Order("resfcr","22/11/2022", "190.12",
                "usr1or3", "Delivered", orderItemList));
        orderList.add(new Order("resfcr","22/11/2022", "190.12",
                "usr1or3", "Delivered", orderItemList));





        setOrderRecycler(orderList);

    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void setOrderRecycler(List<Order> orderList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        activityHistoryBinding.orderRecyclerView.setLayoutManager(layoutManager);
        orderAdapter = new OrderExpandableAdapter(this, orderList);

        activityHistoryBinding.orderRecyclerView.setAdapter(orderAdapter);
    }
}