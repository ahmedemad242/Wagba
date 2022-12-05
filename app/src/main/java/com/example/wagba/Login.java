package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.CancellationSignal;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wagba.BottomSheet;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class Login extends AppCompatActivity {

    private Button showSignInSheet;
    private View loginLayout;
    private ImageView logoImageView;
    private BottomSheet bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        loginLayout = findViewById(R.id.loginLayout);
        logoImageView = findViewById(R.id.logo);
        showSignInSheet = findViewById(R.id.showSignInSheet);
        bottomSheet = new BottomSheet(Login.this, R.style.bottomSheetTheme, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                loginLayout.setBackgroundColor(getResources().getColor(R.color.white));
                logoImageView.setImageResource(R.drawable.logo_bg_light);
            }
        });
        View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet, null);
        bottomSheet.setSheetView(sheetView);


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

        showSignInSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInBottomSheet();
                loginLayout.setBackgroundColor(getResources().getColor(R.color.dark_blue));
                logoImageView.setImageResource(R.drawable.logo_bg_dark);
                Log.d("keyboard","keyboard 2");
            }
        });
    }

    private void showSignInBottomSheet() {
        View sheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet, null);
        bottomSheet.show();
    }


}