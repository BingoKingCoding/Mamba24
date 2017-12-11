package com.bingo.king.mvp.presenter;

import android.app.Application;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ZhihuDetailContract;
import com.bingo.king.mvp.model.entity.zhihu.DetailExtraBean;
import com.bingo.king.mvp.model.entity.zhihu.ZhihuDetailBean;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;

import javax.inject.Inject;


@ActivityScope
public class ZhihuDetailPresenter extends BasePresenter<ZhihuDetailContract.Model, ZhihuDetailContract.View>
{

    private Application mApplication;

    @Inject
    public ZhihuDetailPresenter(ZhihuDetailContract.Model model, ZhihuDetailContract.View rootView, Application application)
    {
        super(model, rootView);

        this.mApplication = application;
    }


    public void requestDetailInfo(int id){

        requestInitializeData(mModel.requestDetailInfo(id), new HttpCallback<ZhihuDetailBean>()
        {
            @Override
            public void onSuccess(ZhihuDetailBean data)
            {
                mRootView.showDetailInfo(data);
            }
        });

    }


    public void requestDetailExtraInfo(int id) {
        requestInitializeData(mModel.requestDetailExtraInfo(id), new HttpCallback<DetailExtraBean>()
        {
            @Override
            public void onSuccess(DetailExtraBean data)
            {
                mRootView.showExtraInfo(data);
            }
        });
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();

        this.mApplication = null;
    }

}
