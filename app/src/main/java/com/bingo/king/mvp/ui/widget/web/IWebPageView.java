package com.bingo.king.mvp.ui.widget.web;

import android.view.View;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/7/4 17:23.
 */

public interface IWebPageView
{
    // 显示webview
    void showWebView();

    // 隐藏进度条
    void hindProgressBar();

    //  进度条先加载到90%,然后再加载到100%
    void startProgress();

    /**
     * 进度条变化时调用
     */
    void progressChanged(int newProgress);

    // 隐藏webview
    void hindWebView();

    /**
     * 播放网络视频全屏调用
     */
    void fullViewAddView(View view);

    void showVideoFullView();

    void hindVideoFullView();
}
