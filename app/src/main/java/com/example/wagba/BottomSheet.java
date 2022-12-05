package com.example.wagba;


import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheet extends BottomSheetDialog {
    View sheetView;

    public BottomSheet(@NonNull Context context, int theme, OnCancelListener cancelListener) {
        super(context, theme);

        setOnCancelListener(cancelListener);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }

    public void setSheetView(View sheetView){
        this.sheetView = sheetView;
        setContentView(sheetView);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) sheetView.getParent());
        bottomSheetBehavior.setDraggable(false);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        setHeight(0.8);
    }

    public void setHeight(double percentage){
        if(percentage > 1){
            percentage = 1;
        }
        if(percentage <0){
            percentage = 0;
        }

        View bottomSheetLayout = findViewById(sheetView.getId());
        assert bottomSheetLayout != null;
        bottomSheetLayout.setMinimumHeight((int)(Resources.getSystem().getDisplayMetrics().heightPixels*percentage));
    }


}
