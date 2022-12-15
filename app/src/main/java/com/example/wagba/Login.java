package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import com.example.wagba.databinding.SignUpSheetBinding;
import com.example.wagba.databinding.SignInSheetBinding;
import com.example.wagba.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.Window;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    private SignUpSheetBinding singUpSheetBinding;
    private SignInSheetBinding singInSheetBinding;
    private ActivityLoginBinding loginBinding;
    private BottomSheet bottomSheet;
    private Window window;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        singInSheetBinding = SignInSheetBinding.inflate(getLayoutInflater());
        singUpSheetBinding = singUpSheetBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        window = this.getWindow();
        WindowController.changeNavigationBarColor(window, getResources().getColor(R.color.white));
        WindowController.changeStatusBarColor(window, getResources().getColor(R.color.white), true);

        bottomSheet = new BottomSheet(Login.this, R.style.bottomSheetTheme, new DialogInterface.OnCancelListener() {
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
            String email = String.valueOf(singInSheetBinding.signInEmailText.getText());
            String password = String.valueOf(singInSheetBinding.signInPasswordText.getText());

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                successfulLogin();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        singUpSheetBinding.signUpButton.setOnClickListener(v -> {
            String name = String.valueOf(singUpSheetBinding.signUpNameText.getText());
            String email = String.valueOf(singUpSheetBinding.signUpEmailText.getText());
            String password = String.valueOf(singUpSheetBinding.signUpPasswordText.getText());

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                successfulLogin();
                            } else {
                                Toast.makeText(Login.this, "Authentication failed.",
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
        startActivity(new Intent(Login.this, MainActivity.class));
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