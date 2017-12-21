package com.bingo.king.app.base;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingo.king.R;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/21 14:37.
 */

public abstract class BaseZhihuThemeActivity<P extends IPresenter> extends LoadingBaseActivity<P>
{

    protected Toolbar toolbarThemeBase;
    protected AppBarLayout appbarThemeChild;
    protected ImageView ivThemeChildBlur;
    protected ImageView ivThemeChildOrigin;
    protected TextView tvThemeChildDes;
    protected SwipeRefreshLayout swipeRefresh;


    @Override
    public int onCreateContentView(Bundle savedInstanceState)
    {
        return R.layout.activity_zhihu_theme_base;
    }

    @Override
    public int getBaseFrameLayoutId()
    {
        return R.id.fl_base_theme_content;
    }


    @Override
    protected void initUI() {
        toolbarThemeBase = findViewById(R.id.toolbar_theme_base);
        appbarThemeChild = findViewById(R.id.appbar_theme_child);
        ivThemeChildBlur = findViewById(R.id.iv_theme_child_blur);
        ivThemeChildOrigin = findViewById(R.id.iv_theme_child_origin);
        tvThemeChildDes = findViewById(R.id.tv_theme_child_des);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        initToolBar(toolbarThemeBase,true,"");
    }



}
