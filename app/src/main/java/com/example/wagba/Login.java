package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import com.example.wagba.databinding.SignUpSheetBinding;
import com.example.wagba.databinding.SignInSheetBinding;
import com.example.wagba.databinding.ActivityLoginBinding;
import android.view.Window;


public class Login extends AppCompatActivity {

    private SignUpSheetBinding singUpSheetBinding;
    private SignInSheetBinding singInSheetBinding;
    private ActivityLoginBinding loginBinding;
    private BottomSheet bottomSheet;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
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