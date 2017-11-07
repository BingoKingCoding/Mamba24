package com.bingo.king.mvp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/30 11:52.
 */

public class FixedImageView extends AppCompatImageView
{
    private int mScreenHeight;

    public FixedImageView(Context paramContext)
    {
        this(paramContext, null);
    }

    public FixedImageView(Context paramContext, AttributeSet paramAttributeSet)
    {
        this(paramContext, paramAttributeSet, 0);
    }

    public FixedImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext, paramAttributeSet);
    }

    public static int[] getScreenWidthHeight(Context paramContext)
    {
        int[] arrayOfInt = new int[2];
        if (paramContext == null)
            return arrayOfInt;
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity)paramContext).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int i = localDisplayMetrics.widthPixels;
        int j = localDisplayMetrics.heightPixels;
        arrayOfInt[0] = i;
        arrayOfInt[1] = j;
        return arrayOfInt;
    }

    private void init(Context paramContext, AttributeSet paramAttributeSet)
    {
        this.mScreenHeight = getScreenWidthHeight(paramContext)[1];
    }

    protected void onMeasure(int paramInt1, int paramInt2)
    {
        int i = MeasureSpec.getSize(paramInt1);
        MeasureSpec.getSize(paramInt1);
        setMeasuredDimension(i, this.mScreenHeight);
    }
}
