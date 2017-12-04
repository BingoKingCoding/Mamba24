package com.bingo.king.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bingo.king.R;
import com.bingo.king.app.App;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by quantan.liu on 2017/3/23.
 */

public class GlideUtils
{
    private static volatile GlideUtils instance;

    public static GlideUtils getInstance()
    {
        if (instance == null)
        {
            synchronized (GlideUtils.class)
            {
                if (instance == null)
                {
                    instance = new GlideUtils();
                }
            }
        }
        return instance;
    }


    public void loadImage(String url, ImageView image)
    {
        Glide.with(image.getContext())
                .load(url)
                .placeholder(R.drawable.ic_bg_image_loading)
                .error(R.drawable.ic_nopic)
                .into(image);
    }


    /**
     * 首页zhihu item读取图片
     *
     * @param imgNumber 图片大小1最大 2中等 3最小 正方形的
     */
    public void loadImage(int imgNumber, String url, ImageView image)
    {
        Glide.with(image.getContext())
                .load(url)
                .crossFade(1500)
                .error(getDefaultPic(imgNumber))
                .into(image);
    }


    private int getDefaultPic(int imgNumber)
    {
        switch (imgNumber)
        {
            case 1:
                return R.mipmap.img_two_bi_one;
            case 2:
                return R.mipmap.img_four_bi_three;
            case 3:
                return R.mipmap.img_one_bi_one;
            case 4:
                return R.mipmap.img_default_movie;
        }
        return R.mipmap.img_four_bi_three;
    }

    public void load(Context mContext, String url, ImageView iv)
    {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(mContext).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
    }

    public void loadDetailImg(Context mContext, String url, ImageView iv)
    {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(mContext).load(url).asBitmap()
                .placeholder(R.drawable.ic_nothing)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .error(R.drawable.ic_nothing)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
    }

    public void loadMovieTopImg(ImageView imageView, String url)
    {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .error(getDefaultPic(4))
                .into(imageView);
    }

    public void loadMovieLatestImg(ImageView imageView, String url)
    {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .override((int) CommonUtils.getDimens(R.dimen.movie_detail_width), (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
                .placeholder(getDefaultPic(4))
                .error(getDefaultPic(4))
                .into(imageView);
    }

    /**
     * 默认调用方法
     *
     * @param model String, byte[], File, Integer, Uri
     * @param <T>
     * @return
     */
    public <T> DrawableTypeRequest<T> load(T model)
    {
        return (DrawableTypeRequest<T>) Glide.with(App.getApplication()).load(model)
                .error(R.drawable.ic_nopic)
                .dontAnimate();
    }

    public void clearMemory(boolean isClearDiskCache, boolean isClearMemory)
    {
        if (isClearDiskCache)
        {//清除本地缓存
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(integer -> Glide.get(App.getApplication()).clearDiskCache());
        }
        if (isClearMemory)
        {//清除内存缓存
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> Glide.get(App.getApplication()).clearMemory());
        }
    }


}
