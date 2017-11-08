package com.bingo.king.mvp.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import timber.log.Timber;

/**
 * <不能滑动的viewpager>
 */

public class NoScrollViewPager extends ViewPager
{
    public final String TAG = this.getClass().getSimpleName();

    private boolean noScroll = false;

    public NoScrollViewPager(Context context)
    {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        Timber.d(TAG,"onTouchEvent");
        return !noScroll && super.onTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        Timber.d(TAG,"onInterceptTouchEvent");
        return !noScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item)
    {
        super.setCurrentItem(item,false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll)
    {
        super.setCurrentItem(item, smoothScroll);
    }
}
