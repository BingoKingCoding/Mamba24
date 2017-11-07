package com.bingo.king.app.utils;

import android.content.res.Resources;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/2 16:31.
 */

public class CommonUtils
{
    public static Resources getResoure()
    {
        return Utils.getApp().getResources();
    }

    public static float getDimens(int resId)
    {
        return getResoure().getDimension(resId);
    }


    /**
     * 配置recycleview
     *
     * @param recyclerView
     * @param layoutManager
     */
    public static void configRecycleView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
