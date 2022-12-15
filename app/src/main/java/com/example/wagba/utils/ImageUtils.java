package com.example.wagba.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUtils {

    public static void loadImage(Context context, String url, ImageView imageView, int fallbackResource) {
        Glide.with(context)
                .load(url)
                .error(fallbackResource)
                .into(imageView);
    }
}
