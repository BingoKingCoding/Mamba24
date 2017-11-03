package com.bingo.king.di.module;

import com.bingo.king.app.App;
import com.bingo.king.app.Constants;
import com.bingo.king.app.utils.FileUtil;
import com.bingo.king.mvp.model.http.IRepository;
import com.bingo.king.mvp.model.http.RepositoryImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/3 10:30.
 */
@Module
public class NetworkModule
{
    private App mApp;

    public NetworkModule(App app)
    {
        this.mApp = app;
    }

    @Provides
    @Singleton
    IRepository provideRepository(@Named("one") Retrofit kugou, @Named("two") Retrofit lastfm)
    {
        return new RepositoryImpl(mApp, kugou, lastfm);
    }


    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder()
    {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder()
    {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    public Gson provideGson()
    {
        return new GsonBuilder().create();
    }


    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder)
    {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.cache(new Cache(FileUtil.getHttpCacheDir(), Constants.HTTP_CACHE_SIZE));
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(loggingInterceptor);
        return builder.build();
    }


    @Provides
    @Named("one")
    @Singleton
    Retrofit provideOneRetrofit(OkHttpClient client, Retrofit.Builder builder, Gson gson)
    {
        String baseUrl = Constants.BASE_API_URL_ONE;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Named("two")
    @Singleton
    Retrofit provideTwoRetrofit(OkHttpClient client, Retrofit.Builder builder, Gson gson)
    {
        String baseUrl = Constants.BASE_API_URL_TWO;

        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }


}
