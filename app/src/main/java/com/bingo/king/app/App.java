package com.bingo.king.app;

import android.app.Application;
import android.content.Context;

import com.bingo.king.BuildConfig;
import com.squareup.leakcanary.LeakCanary;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/2 16:32.
 */

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        if (BuildConfig.LOG_DEBUG)
        {
            //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
            //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
            //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
            Timber.plant(new Timber.DebugTree());
            ButterKnife.setDebug(true);
        }
        if (BuildConfig.USE_CANARY)
        {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        // 这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }
}
