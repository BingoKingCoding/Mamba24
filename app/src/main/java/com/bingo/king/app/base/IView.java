package com.bingo.king.app.base;

/**
 * Created by adou on 2017/11/2:22:03.
 *
 * 页面初始化的时候是由 loadingPage进行加载动画
 *
 * showLoadingDialog()、closeLoadingDialog 这两个方法是进行页面初始化后调用的
 *
 * hidePullLoading、endLoadMore 在列表加载的时候调用用来控制 刷新和加载的进度框
 *
 */

public interface IView
{

    /**
     * 进度框
     */
    void showLoadingDialog();

    /**
     * 隐藏进度框
     */
    void closeLoadingDialog();

    /**
     * 隐藏下拉加载
     */
    void hidePullLoading();

    /**
     * 隐藏加载更多
     */
    void endLoadMore();

    /**
     * 显示信息
     */
    void showMessage(String message);


}
