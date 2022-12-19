package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.ViewTreeObserver;

import com.example.wagba.database.AppDatabase;
import com.example.wagba.database.DatabaseManager;
import com.example.wagba.database.dao.ProfileDao;
import com.example.wagba.database.entities.Profile;
import com.example.wagba.databinding.SignUpSheetBinding;
import com.example.wagba.databinding.SignInSheetBinding;
import com.example.wagba.databinding.ActivityLoginBinding;
import com.example.wagba.utils.Validator;
import com.example.wagba.utils.WindowController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import android.view.Window;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private SignUpSheetBinding singUpSheetBinding;
    private SignInSheetBinding singInSheetBinding;
    private ActivityLoginBinding loginBinding;
    private BottomSheet bottomSheet;
    private Window window;
    private FirebaseAuth mAuth;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = DatabaseManager.getInstance(this);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        singInSheetBinding = SignInSheetBinding.inflate(getLayoutInflater());
        singUpSheetBinding = singUpSheetBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        window = this.getWindow();
        WindowController.changeNavigationBarColor(window, getResources().getColor(R.color.white));
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.white), true);

        bottomSheet = new BottomSheet(LoginActivity.this, R.style.bottomSheetTheme, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                setLightTheme();
            }
        });
        bottomSheet.setSheetView(singInSheetBinding.getRoot());

        loginBinding.loginLayout.getViewTreeObserver().addOnGlobalLayoutListener(new  ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                loginBinding.loginLayout.getWindowVisibleDisplayFrame(r);
                int screenHeight = loginBinding.loginLayout.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) {
                    bottomSheet.setHeight(0.95);
                } else {
                    bottomSheet.setHeight(0.8);
                }
            }
        });

        loginBinding.signInSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInBottomSheet();
                setDarkTheme();
            }
        });

        singUpSheetBinding.signInSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInBottomSheet();
                setDarkTheme();
            }
        });

        loginBinding.signUpSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpBottomSheet();
                setDarkTheme();
            }
        });

        singInSheetBinding.signUpSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpBottomSheet();
                setDarkTheme();
            }
        });

        singInSheetBinding.signInButton.setOnClickListener(v -> {
            String email = String.valueOf(singInSheetBinding.signInEmailText.getText()).trim();
            String password = String.valueOf(singInSheetBinding.signInPasswordText.getText()).trim();

            if(!Validator.isValidEmail(email)){
                singInSheetBinding.signInEmailText.setError("We only accept emails from @eng.asu.edu.eg");
                return;
            }
            if(!Validator.isValidPassword(password)){
                singInSheetBinding.signInPasswordText.setError("Password should at least be 6 characters");
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                successfulLogin();
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        singUpSheetBinding.signUpButton.setOnClickListener(v -> {
            String name = String.valueOf(singUpSheetBinding.signUpNameText.getText()).trim();
            String email = String.valueOf(singUpSheetBinding.signUpEmailText.getText()).trim();
            String password = String.valueOf(singUpSheetBinding.signUpPasswordText.getText()).trim();
            String confirmPassword = String.valueOf(singUpSheetBinding.signUpConfirmPasswordText.getText()).trim();

            if(!Validator.isValidName(name)) {
                singUpSheetBinding.signUpNameText.setError("Name should at least be 3 characters");
                return;
            }
            if(!Validator.isValidEmail(email)){
                singUpSheetBinding.signUpEmailText.setError("We only accept emails from @eng.asu.edu.eg");
                return;
            }
            if(!Validator.isValidPassword(password)){
                singUpSheetBinding.signUpPasswordText.setError("Password should at least be 6 characters");
                return;
            }
            if(!password.equals(confirmPassword)){
                singUpSheetBinding.signUpConfirmPasswordText.setError("Password should at least be 6 characters");
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        ProfileDao profileDao = database.profileDao();
                                        Profile newProfile = new Profile(user.getUid(), name, email);
                                        profileDao.insert(newProfile);
                                    }
                                });


                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    successfulLogin();
                                                } else {
                                                    //TODO:: Handle display name not set
                                                }
                                            }
                                        });

                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            successfulLogin();
        }
    }

    private void successfulLogin(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void setLightTheme(){
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.white), true);
        loginBinding.loginLayout.setBackgroundColor(getResources().getColor(R.color.white));
        loginBinding.logo.setImageResource(R.drawable.logo_bg_light);
    }

    private void setDarkTheme(){
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.dark_blue), false);
        loginBinding.loginLayout.setBackgroundColor(getResources().getColor(R.color.dark_blue));
        loginBinding.logo.setImageResource(R.drawable.logo_bg_dark);
    }


    private void showSignInBottomSheet() {
        bottomSheet.setSheetView(singInSheetBinding.getRoot());
        bottomSheet.show();
    }

    private void showSignUpBottomSheet() {
        bottomSheet.setSheetView(singUpSheetBinding.getRoot());
        bottomSheet.show();
    }
}