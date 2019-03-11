package com.mezzsy.copyzhihunews.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    private static final String TAG = "GlideImageLoader";

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
//        Log.d(TAG, "displayImage: path = " + path);
        Glide.with(context).load((String) path).into(imageView);
    }
}
