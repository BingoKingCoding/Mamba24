package com.bingo.king.di.module;

import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.MeContract;
import com.bingo.king.mvp.model.MeModel;

import dagger.Module;
import dagger.Provides;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/11/29 11:34.
 */
@Module
public class MeModule
{
    private MeContract.View mView;

    public MeModule(MeContract.View view)
    {
        this.mView = view;
    }

    @Provides
    @ActivityScope
    MeContract.View provideMeView()
    {
        return mView;
    }

    @Provides
    @ActivityScope
    MeContract.Model provideMeModel(MeModel model)
    {
        return model;
    }

}
