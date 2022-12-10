package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.wagba.databinding.ActivityCartBinding;

public class Cart extends AppCompatActivity {

    private ActivityCartBinding activityCartBinding;
    private Window window;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCartBinding = ActivityCartBinding.inflate(getLayoutInflater());
        getSupportActionBar().setTitle("My Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(activityCartBinding.getRoot());
        window = this.getWindow();

        WindowController.changeNavigationBarColor(window, getResources().getColor(R.color.white));
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.dark_blue), false);

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
}