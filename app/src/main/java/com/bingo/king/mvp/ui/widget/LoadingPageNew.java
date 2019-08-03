package com.bingo.king.mvp.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bingo.king.R;


/**
 * <加载页面>
 * Created by wwb on 2017/6/22 15:26.
 */

public abstract class LoadingPageNew extends FrameLayout {
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;
    private View loadingView;                 // 加载中的界面
    private View errorView;                   // 错误界面
    private View emptyView;                   // 空界面
    public View contentView;                 // 加载成功的界面

    private AnimationDrawable mAnimationDrawable;
    private ImageView img;

    private int state = STATE_UNKNOWN;

    private Context mContext;


    public LoadingPageNew(Context context) {
        this(context, null);
    }

    public LoadingPageNew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPageNew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        this.setBackgroundColor(getResources().getColor(R.color.color_page_bg));
        //把loadingView添加到frameLayout上
        if (loadingView == null) {
            loadingView = createLoadingView();
            this.addView(loadingView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        //把emptyView添加到frameLayout上
        if (emptyView == null) {
            emptyView = createEmptyView();
            this.addView(emptyView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        }
        //把errorView添加到frameLayout上
        if (errorView == null) {
            errorView = createErrorView();
            this.addView(errorView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }

        if (contentView == null) {
            contentView = createSuccessView();
            this.addView(contentView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }

        showPage();//根据状态显示界面
    }

    private View createLoadingView() {
        loadingView = LayoutInflater.from(mContext).inflate(R.layout.loadingpage_state_loading, null);
        img = (ImageView) loadingView.getRootView().findViewById(R.id.img_progress);
        // 加载动画 这边也可以直接用progressbar
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        return loadingView;
    }

    private View createEmptyView() {
        emptyView = LayoutInflater.from(mContext).inflate(R.layout.loadingpage_state_empty, null);
        return emptyView;
    }


    private View createErrorView() {
        errorView = LayoutInflater.from(mContext).inflate(R.layout.loadingpage_state_error, null);
        CardView cv_error_refresh = errorView.findViewById(R.id.cv_error_refresh);
        cv_error_refresh.setOnClickListener(view -> {
            state = STATE_LOADING;
            showPage();
            retryRequestData();
        });
        return errorView;
    }


    private void startAnimation() {
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
    }

    private void stopAnimation() {
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void showPage(int state) {
        this.state = state;
        showPage();
    }

    private void showPage() {
        if (loadingView != null) {
            if (state == STATE_UNKNOWN || state == STATE_LOADING) {
                loadingView.setVisibility(View.VISIBLE);
                // 开始动画
                startAnimation();
            } else {
                // 关闭动画
                stopAnimation();
                loadingView.setVisibility(View.GONE);
            }
        }
        if (state == STATE_EMPTY || state == STATE_ERROR || state == STATE_SUCCESS) {
            // 关闭动画
            stopAnimation();
        }


        if (emptyView != null) {
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.GONE);
        }

        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE : View.GONE);
        }

        if (contentView != null) {
            contentView.setVisibility(state == STATE_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    /***
     * 创建成功的界面
     */
    public abstract View createSuccessView();

    /**
     * 3、
     * 当网络获取失败 点击进行网络请求时调用
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     */
    protected abstract void retryRequestData();


}
