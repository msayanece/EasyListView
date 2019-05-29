package com.sayan.easylistwidget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.lang.reflect.Field;

/**
 * This class contains different utility methods used in this module
 */
public class Utils {
    /**
     * Used for loading image views with image URLs
     * @param context the context or activity object
     * @param imageURL The URL of the image to be displayed in String
     * @param imageView The image view as the image container
     * @param height the required height of the image to be displayed
     * @param width the required width of the image to be displayed
     */
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

    /**
     * used to find the getter method corresponding to a field Ex: field name = "title" method name = getTitle()
     * @param field the field of a class
     * @return the getter method name
     */
    public static String findGetterMethodName(Field field) {
        StringBuilder methodNameBuilder = new StringBuilder();
        methodNameBuilder.append("get");
        String fieldName = field.getName();
        String upperCaseFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        methodNameBuilder.append(upperCaseFieldName);
        return methodNameBuilder.toString();
    }
}
