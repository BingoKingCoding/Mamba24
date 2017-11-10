package com.bingo.king.app.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.bingo.king.app.App;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/26 15:27.
 */

public class ResourcesUtil
{
    public static Resources getResources()
    {
        return App.getApplication().getResources();
    }

    /**
     * 根据资源id获得Drawable
     *
     * @param resId Drawable资源id
     * @return
     */
    public static Drawable getDrawable(int resId)
    {
        return getResources().getDrawable(resId);
    }

    /**
     * 根据资源id获得String字符串
     *
     * @param resId String资源id
     * @return
     */
    public static String getString(int resId)
    {
        return getResources().getString(resId);
    }

    /**
     * 根据资源id获得颜色值
     *
     * @param resId Color资源id
     * @return
     */
    public static int getColor(int resId)
    {
        return getResources().getColor(resId);
    }

    /**
     * 根据资源id获得转换后的值
     *
     * @param resId
     * @return
     */
    public static float getDimension(int resId)
    {
        return getResources().getDimension(resId);
    }

    /**
     * 根据资源id获得转换后的值（取整数部分的值返回）
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelOffset(int resId)
    {
        return getResources().getDimensionPixelOffset(resId);
    }

    /**
     * 根据资源id获得转换后的值（取四舍五入后的整数部分的值返回）
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelSize(int resId)
    {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获得文件名对应的资源id
     *
     * @param name       文件名
     * @param defType    文件类型
     * @param defPackage 包名
     * @return
     */
    public static int getIdentifier(String name, String defType, String defPackage)
    {
        try
        {
            return getResources().getIdentifier(name, defType, defPackage);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

}
