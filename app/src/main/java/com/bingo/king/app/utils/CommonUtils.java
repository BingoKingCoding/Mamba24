package com.bingo.king.app.utils;

import android.content.res.Resources;

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

}
