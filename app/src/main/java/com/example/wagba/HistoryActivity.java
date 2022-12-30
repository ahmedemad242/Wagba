package com.example.wagba;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.adapter.OrderExpandableAdapter;
import com.example.wagba.databinding.ActivityHistoryBinding;
import com.example.wagba.model.Order;
import com.example.wagba.utils.WindowController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding activityHistoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = Objects.requireNonNull(user).getUid();
        DatabaseReference ordersRef = FirebaseDatabase.getInstance("https://wagba-cadcf-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users")
                .child(userId).child("orders");

        activityHistoryBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        Objects.requireNonNull(getSupportActionBar()).setTitle("History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(activityHistoryBinding.getRoot());
        Window window = this.getWindow();

        WindowController.changeNavigationBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.white));
        WindowController.changeStatusBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.dark_blue), false);

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Order> orderList = new ArrayList<>();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    Order order = orderSnapshot.getValue(Order.class);
                    orderList.add(0, order);
                }
                setOrderRecycler(orderList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void setOrderRecycler(List<Order> orderList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        activityHistoryBinding.orderRecyclerView.setLayoutManager(layoutManager);
        OrderExpandableAdapter orderAdapter = new OrderExpandableAdapter(this, orderList);

        activityHistoryBinding.orderRecyclerView.setAdapter(orderAdapter);
    }
}