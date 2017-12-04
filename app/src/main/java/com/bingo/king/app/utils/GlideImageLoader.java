package com.bingo.king.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bingo.king.R;
import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by wwb on 2017/12/4 11:56.
 */

public class GlideImageLoader extends ImageLoader
{
    @Override
    public void displayImage(Context context, Object url, ImageView imageView)
    {
        Glide.with(context).load(url)
                .placeholder(R.mipmap.img_two_bi_one)
                .error(R.mipmap.img_two_bi_one)
                .crossFade(1000)
                .into(imageView);
    }
}
