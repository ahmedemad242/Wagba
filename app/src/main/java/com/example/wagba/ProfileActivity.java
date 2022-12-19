package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.example.wagba.database.AppDatabase;
import com.example.wagba.database.DatabaseManager;
import com.example.wagba.database.dao.ProfileDao;
import com.example.wagba.database.entities.Profile;
import com.example.wagba.databinding.ActivityProfileBinding;
import com.example.wagba.utils.WindowController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding activityProfileBinding;
    private Window window;
    FirebaseUser user;
    AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(activityProfileBinding.getRoot());
        window = this.getWindow();

        WindowController.changeNavigationBarColor(window, getResources().getColor(R.color.white));
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.dark_blue), false);

        user = FirebaseAuth.getInstance().getCurrentUser();

        database = DatabaseManager.getInstance(this);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ProfileDao profileDao = database.profileDao();
                Profile profile = profileDao.getById(user.getUid());

                String name = profile.name;
                String email = profile.email;

                activityProfileBinding.profileName.setText(name.split(" ")[0]+"!");
                activityProfileBinding.profileFullName.setText(name);
                activityProfileBinding.profileEmail.setText(email);
            }
        });


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