package com.bingo.king.di.module;


import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ZhiHuCommentContract;
import com.bingo.king.mvp.model.ZhiHuCommentModel;

import dagger.Module;
import dagger.Provides;


@Module
public class ZhiHuCommentModule
{
    private ZhiHuCommentContract.View view;

    /**
     * 构建ZhiHuCommentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ZhiHuCommentModule(ZhiHuCommentContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ZhiHuCommentContract.View provideZhiHuCommentView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    ZhiHuCommentContract.Model provideZhiHuCommentModel(ZhiHuCommentModel model)
    {
        return model;
    }
}