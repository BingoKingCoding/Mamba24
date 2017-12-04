package com.bingo.king.app.base;

/**
 * Created by wwb on 2017/11/2:22:03.
 *
 * 页面初始化的时候是由 {@link com.bingo.king.mvp.ui.widget.LoadingPage}进行加载动画
 *
 *  初始化之后进行网络交互的loadingDialog由{@link com.bingo.king.mvp.model.http.rxerrorhandler.StatefulCallback}控制
 *
 * hidePullLoading、endLoadMore 在列表加载的时候调用用来控制 刷新和加载的进度框
 *
 * 具体用法可以查看{@link com.bingo.king.mvp.model.http.HttpUtils}
 *
 */

public interface IView
{
    /**
     * 隐藏下拉加载
     */
    void hidePullLoading();


    /**
     * 显示信息
     */
    void showMessage(String message);


}
