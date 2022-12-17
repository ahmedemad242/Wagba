package com.example.wagba.utils;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class WindowController {
    public static void changeStatusBarColor(Window window, Integer color, Boolean isLight){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
        window.getDecorView().setSystemUiVisibility(isLight ?
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : 0);
    }

    public static void changeNavigationBarColor(Window window, Integer color){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(color);
    }
}
