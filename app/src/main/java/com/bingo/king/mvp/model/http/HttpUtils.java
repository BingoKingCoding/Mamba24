package com.bingo.king.mvp.model.http;


import com.bingo.king.app.base.IView;
import com.bingo.king.app.utils.RxUtils;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;
import com.bingo.king.mvp.model.http.rxerrorhandler.StatefulCallback;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.blankj.utilcode.util.NetworkUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/4:21:49.
 */

public class HttpUtils {
    /**
     * 页面初始化请求数据(因为页面有封装了一个loadingpager的动画所以不用显示进度条)
     */
    public static <T> void requestInitializeData(IView mView, Observable<T> observable, HttpCallback<T> httpCallback) {

        httpCallback.setTarget(mView);
        /**
         * 先判断网络连接状态和网络是否可用，放在回调那里好呢，还是放这里每次请求都去判断下网络是否可用好呢？
         * 如果放在请求前面太耗时了，如果放回掉提示的速度慢，要10秒钟请求超时后才提示。
         * 最后采取的方法是判断网络是否连接放在外面，网络是否可用放在回掉。
         */
        if (!NetworkUtils.isConnected()) {
//            ToastUtils.showShort("网络连接已断开");
            if (mView != null) {
                mView.showMessage("网络连接已断开");
                mView.setState(LoadingPage.STATE_ERROR);
            }
            return;
        }

        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable ->
                {
//                    if (mView instanceof StatefulCallback)
//                    {
//                        ((StatefulCallback) mView).setState(LoadingPage.STATE_LOADING);
//                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(httpCallback);
    }


    public static <T> void requestData(IView mView, String msg, Observable<T> observable, HttpCallback<T> httpCallback) {
        if (!NetworkUtils.isConnected()) {
            if (mView != null) {
                mView.showMessage("网络连接已断开");
            }
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable ->
                        mView.showLoadingDialog(msg))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() ->
                        mView.closeLoadingDialog())
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(httpCallback);
    }


    /**
     * 2017/11/5
     * <p>
     * 上拉加载下来刷新使用, 如果有适用loadingpage的话会出现两次加载，此时showLoading不需要执行任何逻辑
     */
    public static <T> void requestDataOnPullToRefresh(boolean pullToRefresh, IView mView, Observable<T> observable, HttpCallback<T> httpCallback) {
        httpCallback.setTarget(mView);
        //doOnSubscribe、doAfterTerminate可以用于上拉加载下来刷新

        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() ->
                {
                    if (pullToRefresh)
                        mView.hidePullLoading();
                })
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(httpCallback);
    }


}
