package com.bingo.king.app.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.bingo.king.app.App;
import com.blankj.utilcode.util.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

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


    public static String formatMoneyChina(String money)
    {
        if (!TextUtils.isEmpty(money))
        {
            String moneyRound = String.valueOf(scaleHalfUp(SDTypeParseUtil.getDouble(money), 2));
            money = new BigDecimal(moneyRound).toPlainString();
            if (money.contains("."))
            {
                int decimalIndex = money.indexOf(".");
                String decimalPart = money.substring(decimalIndex + 1);
                if ("0".equals(decimalPart) || "00".equals(decimalPart))
                {
                    money = money.substring(0, decimalIndex);
                }
            }
            return "¥" + money;
        } else
        {
            return "¥0";
        }
    }

    public static String formatMoneyChina(double money)
    {
        return formatMoneyChina(String.valueOf(money));
    }

    /**
     * 保留小数位（四舍五入模式）
     *
     * @param value 需要格式化的值
     * @param scale 保留几位小数
     * @return
     */
    public static double scaleHalfUp(double value, int scale)
    {
        return scale(value, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留小数位
     *
     * @param value 需要格式化的值
     * @param scale 保留几位小数
     * @param mode  保留模式
     * @return
     */
    public static double scale(double value, int scale, RoundingMode mode)
    {
        return new BigDecimal(String.valueOf(value)).setScale(scale, mode).doubleValue();
    }


    /**
     * 获取颜色值
     *
     * @param object
     * @return
     */
    public static int getResultColor(Object object) {
        if (object instanceof String) {// "#666666"
            return Color.parseColor((String) object);
        } else if (object instanceof Integer) {
            if ((Integer) object > 0)// R.string.app_color
                return App.getApplication().getResources().getColor((Integer) object);
            else return (Integer) object;// Color.BLUE
        } else throw new IllegalStateException("The current color is not found");
    }

    /**
     * 获取文本值
     *
     * @param object
     * @return
     */
    public static String getResultString(Object object) {
        if (object instanceof String)//"标题"
            return (String) object;
        else if (object instanceof Integer && (Integer) object > 0) //R.string.app_name
            return App.getApplication().getResources().getString((Integer) object);
        else throw new IllegalStateException("The current string is not found");
    }


    /**
     * 计算颜色值
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终颜色
     */
    public static int calculateColorWithAlpha(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
}
