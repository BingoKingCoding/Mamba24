package com.bingo.king.di.module;


import dagger.Module;
import dagger.Provides;

import com.bingo.king.di.scope.ActivityScope;

import com.bingo.king.mvp.contract.ZhihuThemeContract;
import com.bingo.king.mvp.model.ZhihuThemeModel;


@Module
public class ZhihuThemeModule
{
    private ZhihuThemeContract.View view;

    /**
     * 构建ZhihuThemeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ZhihuThemeModule(ZhihuThemeContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ZhihuThemeContract.View provideZhihuThemeView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    ZhihuThemeContract.Model provideZhihuThemeModel(ZhihuThemeModel model)
    {
        return model;
    }
}