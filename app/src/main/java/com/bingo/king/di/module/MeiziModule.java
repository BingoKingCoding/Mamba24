package com.bingo.king.di.module;


import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.MeiziContract;
import com.bingo.king.mvp.model.MeiziModel;

import dagger.Module;
import dagger.Provides;


@Module
public class MeiziModule
{
    private MeiziContract.View view;

    /**
     * 构建MeiziModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MeiziModule(MeiziContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MeiziContract.View provideMeiziView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    MeiziContract.Model provideMeiziModel(MeiziModel model)
    {
        return model;
    }
}