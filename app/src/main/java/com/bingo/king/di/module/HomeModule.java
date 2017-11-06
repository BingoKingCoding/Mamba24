package com.bingo.king.di.module;

import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.HomeContract;
import com.bingo.king.mvp.model.HomeModel;

import dagger.Module;
import dagger.Provides;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/6:20:26.
 */

@Module
public class HomeModule
{
    private HomeContract.View view;

    public HomeModule(HomeContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HomeContract.View provideHomeView()
    {
        return this.view;
    }


    @ActivityScope
    @Provides
    HomeContract.Model provideHomeModel(HomeModel model)
    {
        return model;
    }


}
