package com.bingo.king.app.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.utils.ColorUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/10 15:14.
 */

public abstract class BaseCoordinatorActivity<P extends IPresenter> extends LoadingBaseActivity<P>
{

    protected AppBarLayout app_bar;
    protected CollapsingToolbarLayout ctl_toolbar;
    protected ImageView iv_bar;
    protected Toolbar toolbar;
    protected TextView tv_copyright;
    protected FloatingActionButton fab;


    @Override
    protected void setStatusBar()
    {

    }

    @Override
    public int onCreateContentView(Bundle savedInstanceState)
    {
        return R.layout.activity_coordinator_base;
    }

    @Override
    public int getBaseFrameLayoutId()
    {
        return R.id.fl_base_content;
    }

    @Override
    protected void initUI()
    {
        app_bar = findViewById(R.id.app_bar);
        ctl_toolbar = findViewById(R.id.ctl_toolbar);
        iv_bar = findViewById(R.id.iv_bar);
        toolbar = findViewById(R.id.toolbar);
        tv_copyright = findViewById(R.id.tv_copyright);
        fab = findViewById(R.id.fab);
    }

    protected void loadImage(String url){
        Glide.with(this).load(url).asBitmap().priority(Priority.IMMEDIATE).into(new SimpleTarget<Bitmap>()
        {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation)
            {
                iv_bar.setImageBitmap(bitmap);
                new Palette.Builder(bitmap).generate(palette ->
                {
                    Palette.Swatch swatch = ColorUtils.getMostPopulousSwatch(palette);
                    if (swatch != null)
                    {
                        int color = swatch.getRgb();
                        ctl_toolbar.setContentScrimColor(color);
                        ctl_toolbar.setStatusBarScrimColor(ColorUtils.getStatusBarColor(color));
                    }
                });
            }
        });
    }
}
