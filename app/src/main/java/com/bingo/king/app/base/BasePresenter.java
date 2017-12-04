package com.bingo.king.app.base;

import com.bingo.king.mvp.model.http.HttpUtils;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;

import org.simple.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by wang on 2017/11/2 17:32.
 */

public class BasePresenter<M extends IModel, V extends IView> implements IPresenter
{
    protected final String TAG = this.getClass().getSimpleName();
    private CompositeDisposable mCompositeDisposable;

    protected M mModel;
    protected V mRootView;


    private HttpCallback mHttpCallback;

    public BasePresenter(M model, V rootView)
    {
        this.mModel = model;
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter(V rootView)
    {
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter()
    {
        onStart();
    }


    @Override
    public void onStart()
    {
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);//注册eventbus
    }

    @Override
    public void onDestroy()
    {
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);//解除注册eventbus
        unDispose();//解除订阅
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mRootView = null;
        this.mCompositeDisposable = null;
        if (mHttpCallback != null)
        {
            mHttpCallback.detachView();
        }
    }


    /**
     * 是否使用eventBus,默认为使用(true)，
     */
    public boolean useEventBus()
    {
        return true;
    }


    public void addDispose(Disposable disposable)
    {
        if (mCompositeDisposable == null)
        {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有disposable放入,集中处理
    }

    private void unDispose()
    {
        if (mCompositeDisposable != null)
        {
            mCompositeDisposable.clear();//保证activity结束时取消所有正在执行的订阅
        }
    }


    /**
     * 页面初始化请求数据
     */
    protected <T> void requestInitializeData(Observable<T> observable, HttpCallback<T> httpCallback)
    {
        this.mHttpCallback = httpCallback;
        HttpUtils.requestInitializeData(mRootView, observable, httpCallback);
    }

    /**
     * 初始化之后的网络访问
     */
    protected <T> void requestData(String msg, Observable<T> observable, HttpCallback<T> httpCallback)
    {
        this.mHttpCallback = httpCallback;
        HttpUtils.requestData(mRootView, msg, observable, httpCallback);
    }

    /**
     * 上拉加载和下拉刷新 使用
     */
    protected <T> void requestDataOnPullToRefresh(boolean pullToRefresh, Observable<T> observable, HttpCallback<T> httpCallback)
    {
        this.mHttpCallback = httpCallback;
        HttpUtils.requestDataOnPullToRefresh(pullToRefresh, mRootView, observable, httpCallback);
    }


}
