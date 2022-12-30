package com.example.wagba;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.wagba.database.AppDatabase;
import com.example.wagba.database.DatabaseManager;
import com.example.wagba.database.dao.ProfileDao;
import com.example.wagba.database.entities.Profile;
import com.example.wagba.databinding.ActivityProfileBinding;
import com.example.wagba.utils.WindowController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding activityProfileBinding;
    FirebaseUser user;
    AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(activityProfileBinding.getRoot());
        Window window = this.getWindow();

        WindowController.changeNavigationBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.white));
        WindowController.changeStatusBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.dark_blue), false);

        user = FirebaseAuth.getInstance().getCurrentUser();

        database = DatabaseManager.getInstance(this);

        AsyncTask.execute(() -> {
            ProfileDao profileDao = database.profileDao();
            Profile profile = profileDao.getById(user.getUid());

            String name = profile.name;
            String email = profile.email;


            String profileName = " "+name.split(" ")[0]+" !";
            activityProfileBinding.profileName.setText(profileName);
            activityProfileBinding.profileFullName.setText(name);
            activityProfileBinding.profileEmail.setText(email);
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
}