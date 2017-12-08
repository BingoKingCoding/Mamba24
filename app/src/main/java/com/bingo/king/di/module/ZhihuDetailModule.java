package com.bingo.king.di.module;


import dagger.Module;
import dagger.Provides;

import com.bingo.king.di.scope.ActivityScope;

import com.bingo.king.mvp.contract.ZhihuDetailContract;
import com.bingo.king.mvp.model.ZhihuDetailModel;


@Module
public class ZhihuDetailModule
{
    private ZhihuDetailContract.View view;

    /**
     * 构建ZhihuDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ZhihuDetailModule(ZhihuDetailContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ZhihuDetailContract.View provideZhihuDetailView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    ZhihuDetailContract.Model provideZhihuDetailModel(ZhihuDetailModel model)
    {
        return model;
    }
}