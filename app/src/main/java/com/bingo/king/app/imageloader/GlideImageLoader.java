package com.bingo.king.app.imageloader;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;


/**
 * <图片加载实现类>
 * Created by WangWB on 2018/06/27  11:18.
 */

public class GlideImageLoader implements ILoaderStrategy {

    @Override
    public void loadImage(LoaderOptions options) {
        RequestBuilder<Drawable> glideRequest = Glide.with(options.targetView.getContext()).asDrawable();
        if (options.url != null) {
            glideRequest = glideRequest.load(options.url);
        } else if (options.file != null) {
            glideRequest = glideRequest.load(options.file);
        } else if (options.drawableResId != 0) {
            glideRequest = glideRequest.load(options.drawableResId);
        } else if (options.uri != null) {
            glideRequest = glideRequest.load(options.uri);
        }

    }

    @Override
    public void clearMemoryCache() {

    }

    @Override
    public void clearDiskCache() {

    }
}
