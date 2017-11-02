package com.bingo.king.app.base;

/**
 * Created by adou on 2017/11/2:22:03.
 */

public interface IView
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
     * 显示信息
     */
    void showMessage(String message);
}
