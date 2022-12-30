package com.example.wagba;

import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.wagba.database.AppDatabase;
import com.example.wagba.database.DatabaseManager;
import com.example.wagba.database.dao.ProfileDao;
import com.example.wagba.database.entities.Profile;
import com.example.wagba.databinding.ActivityLoginBinding;
import com.example.wagba.databinding.SignInSheetBinding;
import com.example.wagba.databinding.SignUpSheetBinding;
import com.example.wagba.utils.Validator;
import com.example.wagba.utils.WindowController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    private SignUpSheetBinding singUpSheetBinding;
    private SignInSheetBinding singInSheetBinding;
    private ActivityLoginBinding loginBinding;
    private BottomSheet bottomSheet;
    private Window window;
    private FirebaseAuth mAuth;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = DatabaseManager.getInstance(this);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        singInSheetBinding = SignInSheetBinding.inflate(getLayoutInflater());
        singUpSheetBinding = SignUpSheetBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        window = this.getWindow();
        WindowController.changeNavigationBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.white));
        WindowController.changeStatusBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.white), true);

        bottomSheet = new BottomSheet(LoginActivity.this, R.style.bottomSheetTheme, dialog -> setLightTheme());
        bottomSheet.setSheetView(singInSheetBinding.getRoot());

        loginBinding.loginLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            loginBinding.loginLayout.getWindowVisibleDisplayFrame(r);
            int screenHeight = loginBinding.loginLayout.getRootView().getHeight();
            int keypadHeight = screenHeight - r.bottom;
            if (keypadHeight > screenHeight * 0.15) {
                bottomSheet.setHeight(0.95);
            } else {
                bottomSheet.setHeight(0.8);
            }
        });

        loginBinding.signInSheetButton.setOnClickListener(v -> {
            showSignInBottomSheet();
            setDarkTheme();
        });

        singUpSheetBinding.signInSheetButton.setOnClickListener(v -> {
            showSignInBottomSheet();
            setDarkTheme();
        });

        loginBinding.signUpSheetButton.setOnClickListener(v -> {
            showSignUpBottomSheet();
            setDarkTheme();
        });

        singInSheetBinding.signUpSheetButton.setOnClickListener(v -> {
            showSignUpBottomSheet();
            setDarkTheme();
        });

        singInSheetBinding.signInButton.setOnClickListener(v -> {
            String email = String.valueOf(singInSheetBinding.signInEmailText.getText()).trim();
            String password = String.valueOf(singInSheetBinding.signInPasswordText.getText()).trim();

            if(Validator.isInvalidEmail(email)){
                singInSheetBinding.signInEmailText.setError("We only accept emails from @eng.asu.edu.eg");
                return;
            }
            if(Validator.isInvalidPassword(password)){
                singInSheetBinding.signInPasswordText.setError("Password should at least be 6 characters");
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();

                            successfulLogin();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
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
            if(Validator.isInvalidEmail(email)){
                singUpSheetBinding.signUpEmailText.setError("We only accept emails from @eng.asu.edu.eg");
                return;
            }
            if(Validator.isInvalidPassword(password)){
                singUpSheetBinding.signUpPasswordText.setError("Password should at least be 6 characters");
                return;
            }
            if(!password.equals(confirmPassword)){
                singUpSheetBinding.signUpConfirmPasswordText.setError("Password should at least be 6 characters");
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            AsyncTask.execute(() -> {
                                ProfileDao profileDao = database.profileDao();
                                Profile newProfile = new Profile(Objects.requireNonNull(user).getUid(), name, email);
                                profileDao.insert(newProfile);
                            });


                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            Objects.requireNonNull(user).updateProfile(profileUpdates)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            successfulLogin();
                                        }  //TODO:: Handle display name not set

                                    });

                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
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
        WindowController.changeStatusBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.white), true);
        loginBinding.loginLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
        loginBinding.logo.setImageResource(R.drawable.logo_bg_light);
    }

    private void setDarkTheme(){
        WindowController.changeStatusBarColor(window, ContextCompat.getColor(getApplicationContext(),R.color.dark_blue), false);
        loginBinding.loginLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.dark_blue));
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