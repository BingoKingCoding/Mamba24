package com.bingo.king.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bingo.king.BuildConfig;
import com.bingo.king.R;
import com.bingo.king.app.greendao.GreenDaoHelper;
import com.bingo.king.di.component.AppComponent;
import com.bingo.king.di.component.DaggerAppComponent;
import com.bingo.king.di.module.AppModule;
import com.bingo.king.di.module.NetworkModule;
import com.bingo.king.mvp.model.entity.GsonObjectConverter;
import com.bingo.king.mvp.model.entity.dao.UserBeanDao;
import com.bingo.king.mvp.model.entity.event.EUserLogout;
import com.bingo.king.mvp.ui.activity.LoginActivity;
import com.bingo.king.mvp.ui.widget.CustomRefreshHeader;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.fanwe.lib.cache.SDDisk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.socialize.UMShareAPI;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/2 16:32.
 */

public class App extends Application
{
    private static App instance;
    private AppComponent mAppComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        Utils.init(this);
        initTimber();
        initLeakCanary();
        setupInjector();
        initX5();
        initBugLy();
        GreenDaoHelper.getInstance().initDatabase(this);
        if (BuildConfig.DEBUG)
        {
            ButterKnife.setDebug(true);
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        SDDisk.init(this);
        SDDisk.setGlobalObjectConverter(new GsonObjectConverter());
        SDDisk.setDebug(BuildConfig.DEBUG);

        UMShareAPI.get(this);
    }

    private void initBugLy()
    {
        //第三个参数为SDK调试模式开关，调试模式的行为特性如下：
//        输出详细的Bugly SDK的Log；
//        每一条Crash都会被立即上报；
//        自定义日志将会在Logcat中输出。
//        建议在测试阶段建议设置成true，发布时设置为false。
        CrashReport.initCrashReport(getApplicationContext(), Constants.APPID_BUGLY, BuildConfig.DEBUG);
    }

    private void initX5()
    {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback()
        {
            @Override
            public void onViewInitFinished(boolean arg0)
            {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished()
            {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initTimber()
    {
        if (BuildConfig.LOG_DEBUG)
        {
            //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
            //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
            //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initLeakCanary()
    {
        if (BuildConfig.USE_CANARY)
        {
            if (LeakCanary.isInAnalyzerProcess(this))
            {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
    }


    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, layout) ->
        {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
//                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            return new CustomRefreshHeader(context);
        });
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        // 这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
        MultiDex.install(this);
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }

    private void setupInjector()
    {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(this))
                .build();
    }

    public static App getApplication()
    {
        return instance;
    }

    public AppComponent getAppComponent()
    {
        return mAppComponent;
    }

    public void logout(boolean post){
        UserBeanDao.delete();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityUtils.getTopActivity().startActivity(intent);

        if (post)
        {
            EUserLogout event = new EUserLogout();
            EventBus.getDefault().post(event);
        }
    }

}
