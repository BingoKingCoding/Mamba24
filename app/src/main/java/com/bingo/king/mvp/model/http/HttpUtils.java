package com.bingo.king.mvp.model.http;


import com.bingo.king.app.base.IView;
import com.bingo.king.app.utils.RxUtils;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/4:21:49.
 *
 * @Email:634051075@qq.com
 */

public class HttpUtils
{
    public static <T> void requestData(IView<T> mView, Observable<T> observable, HttpCallback<T> httpCallback)
    {

        httpCallback.setTarget(mView);
        /**
         * 先判断网络连接状态和网络是否可用，放在回调那里好呢，还是放这里每次请求都去判断下网络是否可用好呢？
         * 如果放在请求前面太耗时了，如果放回掉提示的速度慢，要10秒钟请求超时后才提示。
         * 最后采取的方法是判断网络是否连接放在外面，网络是否可用放在回掉。
         */
        if (!NetworkUtils.isConnected())
        {
            ToastUtils.showShort("网络连接已断开");
            if (mView != null)
            {
                mView.setState(LoadingPage.STATE_ERROR);
            }
            return;
        }

        //doOnSubscribe、doAfterTerminate可以用于上拉加载下来刷新
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable ->
                {
                    //显示进度条
//                    mView.showLoading();
                    mView.setState(LoadingPage.STATE_LOADING);
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
//                .doAfterTerminate(view::hideLoading)
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(httpCallback);

    }


    /**
     * @date 2017/11/5
     * @author wwb
     * @Description 上拉加载下来刷新使用
     */
    public static <T> void requestDataOnPullToRefresh(boolean pullToRefresh, IView<T> mView, Observable<T> observable, HttpCallback<T> httpCallback)
    {


        httpCallback.setTarget(mView);
        /**
         * 先判断网络连接状态和网络是否可用，放在回调那里好呢，还是放这里每次请求都去判断下网络是否可用好呢？
         * 如果放在请求前面太耗时了，如果放回掉提示的速度慢，要10秒钟请求超时后才提示。
         * 最后采取的方法是判断网络是否连接放在外面，网络是否可用放在回掉。
         */
        if (!NetworkUtils.isConnected())
        {
            ToastUtils.showShort("网络连接已断开");
            if (mView != null)
            {
                mView.setState(LoadingPage.STATE_ERROR);
            }
            return;
        }

        //doOnSubscribe、doAfterTerminate可以用于上拉加载下来刷新
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable ->
                {
                    if (pullToRefresh)
                        mView.showLoading();
                    else
                        mView.startLoadMore();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() ->
                {
                    if (pullToRefresh)
                        mView.hideLoading();
                    else
                        mView.endLoadMore();
                })
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(httpCallback);


    }


}
