package com.bingo.king.mvp.ui.widget.web;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/7/4 17:23.
 */

public interface IWebPageView
{

    // 隐藏进度条
    void hindProgressBar();

    //  进度条先加载
    void startProgress();

    /**
     * 进度条变化时调用
     */
    void progressChanged(int newProgress);

}
