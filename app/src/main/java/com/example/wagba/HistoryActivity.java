package com.example.wagba;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wagba.adapter.OrderExpandableAdapter;
import com.example.wagba.databinding.ActivityHistoryBinding;
import com.example.wagba.utils.WindowController;

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

//        List<Order> orderList = new ArrayList<Order>();
//        orderList.add(new Order("22/11/2022", "190.12", 13, "12311", "Delivered"));
//        orderList.add(new Order("22/10/2022", "1241.12", 1, "12312", "Delivered"));
//        orderList.add(new Order("22/12/2022", "13.12", 12, "12123", "Delivered"));
//        orderList.add(new Order("13/11/2022", "1312.12", 4, "12313", "Delivered"));
//        orderList.add(new Order("14/10/2022", "1412.12", 1, "12314", "Delivered"));
//
//        setOrderRecycler(orderList);

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



//    private void setOrderRecycler(List<Order> orderList){
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        activityHistoryBinding.orderRecyclerView.setLayoutManager(layoutManager);
//        orderAdapter = new OrderAdapter(this, orderList);
//        activityHistoryBinding.orderRecyclerView.setAdapter(orderAdapter);
//    }
}