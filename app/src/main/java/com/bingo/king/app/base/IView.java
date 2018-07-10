package com.bingo.king.app.base;

/**
 * Created by wwb on 2017/11/2:22:03.
 * <p>
 * 页面初始化的时候是由 {@link com.bingo.king.mvp.ui.widget.LoadingPage}进行加载动画
 * <p>
 * <p>
 * hidePullLoading、endLoadMore 在列表加载的时候调用用来控制 刷新和加载的进度框
 */

public interface IView {

    /**
     * 设置页面状态(封装了LoadingPager)
     */
    void setState(int state);

    /**
     * 显示进度框
     */
    void showLoadingDialog();

    /**
     * 显示进度框
     */
    void showLoadingDialog(String msg);


    /**
     * 隐藏进度框
     */
    void closeLoadingDialog();


    /**
     * 隐藏下拉加载
     */
    void hidePullLoading();

}
