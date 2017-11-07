package com.bingo.king.di.module;


import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.PhotoViewContract;
import com.bingo.king.mvp.model.PhotoViewModel;

import dagger.Module;
import dagger.Provides;


@Module
public class PhotoViewModule
{
    private PhotoViewContract.View view;

    /**
     * 构建PhotoViewModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PhotoViewModule(PhotoViewContract.View view)
    {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PhotoViewContract.View providePhotoViewView()
    {
        return this.view;
    }

    @ActivityScope
    @Provides
    PhotoViewContract.Model providePhotoViewModel(PhotoViewModel model)
    {
        return model;
    }
}