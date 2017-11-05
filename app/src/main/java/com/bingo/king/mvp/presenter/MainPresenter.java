package com.bingo.king.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.MainContract;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 15:54.
 */
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View>
{
    private Application mApplication;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView
            , Application application)
    {
        super(model, rootView);
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mApplication = null;
    }

    public void requestData(boolean pullToRefresh)
    {
        mModel.getRandomGirl()
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3,2))
                .doOnSubscribe(disposable ->
                {
//                    if (pullToRefresh)
//                        mRootView.showLoading();
//                    else
//                        mRootView.startLoadMore();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() ->
                {
//                    if (pullToRefresh)
//                        mRootView.hideLoading();
//                    else
//                        mRootView.endLoadMore();
                })
//                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(
                        new HttpCallback<GankEntity>()
                        {
                            @Override
                            public void onNext(@NonNull GankEntity gankEntity)
                            {
                                super.onNext(gankEntity);
                                Log.d(TAG, "onNext:" + gankEntity.results);
                            }

                            @Override
                            public void onError(@NonNull Throwable e)
                            {
                                super.onError(e);
                            }

                            @Override
                            public void onComplete()
                            {
                                super.onComplete();
                                mRootView.hideLoading();
                            }
                        }

//                        new ErrorHandleSubscriber<GankEntity>(mErrorHandler)
//                {
//                    @Override
//                    public void onNext(@NonNull GankEntity gankEntity)
//                    {
//                        if (pullToRefresh)
//                        {
//                            mRootView.setNewData(gankEntity.results);
//                        }else {
//                            mRootView.setAddData(gankEntity.results);
//                        }
//                    }
//                }
                );
    }


    public void requestDataOnPullToRefresh(boolean pullToRefresh)
    {
//        requestData(mModel.getRandomGirl(), new HttpCallback<GankEntity>()
//        {
//            @Override
//            public void onSuccess(GankEntity gankEntity)
//            {
//                super.onSuccess(gankEntity);
//                Log.d(TAG, "onNext:"+gankEntity.results);
//            }
//        });

        requestDataOnPullToRefresh(pullToRefresh, mModel.getRandomGirl(), new HttpCallback<GankEntity>()
                {
                    @Override
                    public void onSuccess(GankEntity gankEntity)
                    {
                        if (pullToRefresh)
                        {
//                            mRootView.setNewData(gankEntity.results);
                        } else
                        {
//                            mRootView.setAddData(gankEntity.results);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e)
                    {


                    }
                }

        );

    }


}
