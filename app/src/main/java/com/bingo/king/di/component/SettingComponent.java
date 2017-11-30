package com.bingo.king.di.component;

import com.bingo.king.di.module.SettingModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.activity.SettingActivity;

import dagger.Component;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/11/30 16:45.
 */
@ActivityScope
@Component(modules = SettingModule.class, dependencies = AppComponent.class)
public interface SettingComponent
{
    void inject(SettingActivity settingActivity);
}
