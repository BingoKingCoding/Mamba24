package com.bingo.king.mvp.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <避免photoview缩小越界>
 * Created by wwb on 2017/10/12 17:45.
 */

public class PhotoViewPager extends ViewPager
{
    public PhotoViewPager(Context context)
    {
        super(context);
    }

    public PhotoViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        try
        {
            return super.dispatchTouchEvent(ev);
        } catch (IllegalArgumentException ignored)
        {
        } catch (ArrayIndexOutOfBoundsException e)
        {
        }
        return false;
    }
}
