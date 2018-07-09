package com.bingo.king.app.utils;

import android.widget.ImageView;

import com.bingo.king.R;
import com.bingo.king.app.App;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideUtils {
    private static volatile GlideUtils instance;

    public static GlideUtils getInstance() {
        if (instance == null) {
            synchronized (GlideUtils.class) {
                if (instance == null) {
                    instance = new GlideUtils();
                }
            }
        }
        return instance;
    }

    public void loadImage(Object object, ImageView imageView) {
        loadImage(object, imageView, new RequestOptions().placeholder(R.drawable.ic_bg_image_loading).error(R.drawable.ic_nopic));
    }

    public void loadImage(Object object, ImageView view, RequestOptions options) {
        Glide.with(view.getContext()).load(object).apply(options).transition(withCrossFade(500)).into(view);
    }

    /**
     * 首页zhihu item读取图片
     *
     * @param imgNumber 图片大小1最大 2中等 3最小 正方形的
     */
    public void loadImage(int imgNumber, Object object, ImageView imageView) {
        loadImage(object, imageView, new RequestOptions().error(getDefaultPic(imgNumber)));
    }

    public void loadMovieLatestImg(ImageView imageView, Object object) {
        RequestOptions options = new RequestOptions().override((int) CommonUtils.getDimens(R.dimen.movie_detail_width), (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
                .placeholder(getDefaultPic(4))
                .error(getDefaultPic(4));
        loadImage(object, imageView, options);
    }


    private int getDefaultPic(int imgNumber) {
        switch (imgNumber) {
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


    public void clearMemory(boolean isClearDiskCache, boolean isClearMemory) {
        if (isClearDiskCache) {//清除本地缓存
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(integer -> Glide.get(App.getApplication()).clearDiskCache());
        }
        if (isClearMemory) {//清除内存缓存
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> Glide.get(App.getApplication()).clearMemory());
        }
    }


}
