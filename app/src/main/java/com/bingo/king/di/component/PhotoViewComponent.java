package com.bingo.king.di.component;


import com.bingo.king.di.module.PhotoViewModule;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.ui.activity.PhotoViewActivity;

import dagger.Component;

@ActivityScope
@Component(modules = PhotoViewModule.class, dependencies = AppComponent.class)
public interface PhotoViewComponent
{
    void inject(PhotoViewActivity activity);
}