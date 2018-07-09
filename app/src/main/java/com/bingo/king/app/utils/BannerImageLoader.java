package com.bingo.king.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bingo.king.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by wwb on 2017/12/4 11:56.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object url, ImageView imageView) {
        GlideUtils.getInstance().loadImage(1,url,imageView);
    }
}
