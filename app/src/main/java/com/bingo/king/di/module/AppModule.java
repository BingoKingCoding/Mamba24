package com.bingo.king.di.module;

import android.app.Application;

import com.bingo.king.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 */
@Module
public class AppModule
{
    private App mApp;

    public AppModule(App mApp) {
        this.mApp = mApp;
    }

    @Provides
    @Singleton
    public App provideApp() {
        return mApp;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApp;
    }
}
