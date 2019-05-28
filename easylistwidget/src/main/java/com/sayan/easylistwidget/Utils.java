package com.sayan.easylistwidget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class Utils {
    public static void loadImageDirectlyWithSize(Context context, String imageURL, ImageView imageView, int height, int width) {
        try {
            Glide.with(context)
                    .load(imageURL)
                    .apply(
                            new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .override(width, height)
                                    .centerCrop()
                    )
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
