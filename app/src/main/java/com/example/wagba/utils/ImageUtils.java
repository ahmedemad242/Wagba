package com.example.wagba.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtils {

    public static void loadImage(Context context, String url, ImageView imageView, int fallbackResource) {
        Glide.with(context)
                .load(url)
                .error(fallbackResource)
                .into(imageView);
    }
}
