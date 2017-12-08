package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ZhihuDetailContract;
import com.bingo.king.mvp.model.entity.zhihu.DetailExtraBean;
import com.bingo.king.mvp.model.entity.zhihu.ZhihuDetailBean;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class ZhihuDetailModel extends BaseModel implements ZhihuDetailContract.Model
{

    private Application mApplication;

    @Inject
    public ZhihuDetailModel(IRepository repository, Application application)
    {
        super(repository);
        this.mApplication = application;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<ZhihuDetailBean> requestDetailInfo(int id)
    {
        return mRepository.requestDetailInfo(id);
    }

    @Override
    public Observable<DetailExtraBean> fetchDetailExtraInfo(int id)
    {
        return mRepository.requestDetailExtraInfo(id);
    }
}