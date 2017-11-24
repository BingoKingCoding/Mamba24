package com.bingo.king.mvp.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bingo.king.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * <自定义刷新头部>
 * Created by wang on 2017/11/24 09:47.
 */

public class CustomRefreshHeader extends FrameLayout implements RefreshHeader
{

    private AnimationDrawable mAnimationDrawable;
    private ImageView iv_progress;
    private ImageView iv_bottom_shadow;

    public CustomRefreshHeader(Context context)
    {
        super(context);
        initView();
    }

    public CustomRefreshHeader(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    public CustomRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView()
    {
        View refreshView = LayoutInflater.from(getContext()).inflate(R.layout.layout_refresh_header_loading, null);
        this.addView(refreshView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        iv_progress = refreshView.findViewById(R.id.iv_progress);
        iv_bottom_shadow = refreshView.findViewById(R.id.iv_bottom_shadow);
        mAnimationDrawable = (AnimationDrawable) iv_progress.getDrawable();
    }


    //真实的视图就是自己，不能返回null
    @NonNull
    @Override
    public View getView()
    {
        return this;
    }


    //指定为平移，不能null
    @Override
    public SpinnerStyle getSpinnerStyle()
    {
        return SpinnerStyle.Translate;
    }


    //开始动画
    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight)
    {
        if (mAnimationDrawable != null && !mAnimationDrawable.isRunning())
        {
            mAnimationDrawable.start();
        }
    }


    @Override
    public int onFinish(RefreshLayout layout, boolean success)
    {
        //停止动画
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning())
        {
            mAnimationDrawable.stop();
        }
//        if (success)
//        {
//            mHeaderText.setText("刷新完成");
//        } else
//        {
//            mHeaderText.setText("刷新失败");
//        }
        return 200;//延迟200毫秒之后再弹回
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState)
    {
        switch (newState)
        {
            case None:
            case PullDownToRefresh:
//                mHeaderText.setText("下拉开始刷新");
//                mArrowView.setVisibility(VISIBLE);//显示下拉箭头
//                mProgressView.setVisibility(GONE);//隐藏动画
//                mArrowView.animate().rotation(0);//还原箭头方向
//                iv.animate().;
                if (mAnimationDrawable != null && !mAnimationDrawable.isRunning())
                {
                    mAnimationDrawable.start();
                }
                break;
            case Refreshing:
//                mHeaderText.setText("正在刷新");
//                mProgressView.setVisibility(VISIBLE);//显示加载动画
//                mArrowView.setVisibility(GONE);//隐藏箭头
                break;
            case ReleaseToRefresh:
//                mHeaderText.setText("释放立即刷新");
//                mArrowView.animate().rotation(180);//显示箭头改为朝上
                break;
        }
    }

    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight)
    {
        if (percent == 0)
        {
            //停止动画
            if (mAnimationDrawable != null && mAnimationDrawable.isRunning())
            {
                mAnimationDrawable.stop();
            }
        }
        // percent<1的时候是显示下拉可以刷新 >1的时候是显示释放立即刷新
        if (percent < 1)
        {
            iv_progress.setScaleX(percent);
            iv_progress.setScaleY(percent);
            iv_bottom_shadow.setScaleX(percent);
            iv_bottom_shadow.setScaleY(percent);
        }
    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight)
    {

    }

    @Override
    public boolean isSupportHorizontalDrag()
    {
        return false;
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors)
    {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight)
    {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax)
    {

    }
}
