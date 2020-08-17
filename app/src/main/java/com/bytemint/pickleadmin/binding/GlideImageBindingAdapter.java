package com.bytemint.pickleadmin.binding;

import android.content.Context;
import android.util.Log;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;

public class GlideImageBindingAdapter {

    @BindingAdapter("imageResourceAdapter")
    public static void setImage(ImageView _imageView, String imageUrl) {

        Context context = _imageView.getContext();

        Log.e("GlideBinding", imageUrl);
        Glide.with(context).load(imageUrl).into(_imageView);
    }
}
