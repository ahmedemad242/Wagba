package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private Button showSignInSheetButton;
    private TextView showSignUpSheetButton;
    private View loginLayout;
    private ImageView logoImageView;
    private BottomSheet bottomSheet;
    private View signInSheet;
    private View signUpSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        loginLayout = findViewById(R.id.loginLayout);
        logoImageView = findViewById(R.id.logo);
        showSignInSheetButton = findViewById(R.id.signInSheetButton);
        showSignUpSheetButton = findViewById(R.id.signUpSheetButton);
        bottomSheet = new BottomSheet(Login.this, R.style.bottomSheetTheme, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                setLightTheme();
            }
        });
        signInSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.sigin_in_sheet, null);
        signUpSheet = LayoutInflater.from(getApplicationContext()).inflate(R.layout.sigin_up_sheet, null);
        bottomSheet.setSheetView(signInSheet);


        loginLayout.getViewTreeObserver().addOnGlobalLayoutListener(new  ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                loginLayout.getWindowVisibleDisplayFrame(r);
                int screenHeight = loginLayout.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) {
                    bottomSheet.setHeight(0.95);
                } else {
                    bottomSheet.setHeight(0.8);
                }
            }
        });

        showSignInSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInBottomSheet();
                setDarkTheme();
            }
        });

        showSignUpSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpBottomSheet();
                setDarkTheme();
            }
        });
    }

    private void setLightTheme(){
        loginLayout.setBackgroundColor(getResources().getColor(R.color.white));
        logoImageView.setImageResource(R.drawable.logo_bg_light);
    }

    private void setDarkTheme(){
        loginLayout.setBackgroundColor(getResources().getColor(R.color.dark_blue));
        logoImageView.setImageResource(R.drawable.logo_bg_dark);
    }


    private void showSignInBottomSheet() {
        bottomSheet.setSheetView(signInSheet);
        bottomSheet.show();
    }

    private void showSignUpBottomSheet() {
        bottomSheet.setSheetView(signUpSheet);
        bottomSheet.show();
    }


}