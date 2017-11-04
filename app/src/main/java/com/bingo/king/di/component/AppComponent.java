package com.bingo.king.di.component;

import android.app.Application;

import com.bingo.king.app.App;
import com.bingo.king.di.module.AppModule;
import com.bingo.king.di.module.NetworkModule;
import com.bingo.king.mvp.model.http.IRepository;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/3 11:17.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent
{
    Application application();

    App MVPLearnApplication();

    OkHttpClient okHttpClient();

    IRepository repository();
}
