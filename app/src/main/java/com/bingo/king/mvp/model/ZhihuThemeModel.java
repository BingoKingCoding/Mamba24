package com.bingo.king.mvp.model;

import android.app.Application;

import com.bingo.king.app.base.BaseModel;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ZhihuThemeContract;
import com.bingo.king.mvp.model.entity.zhihu.SectionChildListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeChildListBean;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class ZhihuThemeModel extends BaseModel implements ZhihuThemeContract.Model
{

    private Application mApplication;

    @Inject
    public ZhihuThemeModel(IRepository repository, Application application)
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
    public Observable<ThemeChildListBean> requestThemeChildList(int id)
    {
       return mRepository.requestThemeChildList(id);
    }

    @Override
    public Observable<SectionChildListBean> requestSectionChildList(int id)
    {
        return mRepository.requestSectionChildList(id);
    }
}