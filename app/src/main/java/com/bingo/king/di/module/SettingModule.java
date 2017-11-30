package com.bingo.king.di.module;

import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.SettingContract;
import com.bingo.king.mvp.model.SettingModel;

import dagger.Module;
import dagger.Provides;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/11/30 16:44.
 */
@Module
public class SettingModule
{
    private SettingContract.View view;

    public SettingModule(SettingContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SettingContract.View provideSettingView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    SettingContract.Model provideSettingModel(SettingModel model)
    {
        return model;
    }
}
