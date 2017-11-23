package com.bingo.king.app.base;

/**
 * Created by adou on 2017/11/2:22:03.
 *
 * showLoading()、hideLoading、startLoadMore、endLoadMore
 * 四个方法在列表加载的时候调用用来控制刷新和加载的进度框
 *
 */

public interface IView<T>
{

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示加载更多
     */
    void startLoadMore();

    /**
     * 隐藏加载更多
     */
    void endLoadMore();


    /**
     * 显示信息
     */
    void showMessage(String message);



    void refreshView(T data);
}
