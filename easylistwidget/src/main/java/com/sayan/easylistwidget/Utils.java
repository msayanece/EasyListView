package com.sayan.easylistwidget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.lang.reflect.Field;

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

    public static String findGetterMethodName(Field field) {
        StringBuilder methodNameBuilder = new StringBuilder();
        methodNameBuilder.append("get");
        String fieldName = field.getName();
        String upperCaseFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        methodNameBuilder.append(upperCaseFieldName);
        return methodNameBuilder.toString();
    }
}
