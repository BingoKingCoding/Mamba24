package com.bingo.king.mvp.model.http;

import android.content.Context;

import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.http.apiservice.OneService;
import com.bingo.king.mvp.model.http.apiservice.TestTwoService;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * <获取数据具体实现>
 * Created by adou on 2017/11/2:22:29.
 */

public class RepositoryImpl implements IRepository
{
    private OneService mOneService;
    private TestTwoService mTestTwoService;
    private Context mContext;

    public RepositoryImpl(Context context, Retrofit oneRetrofit,Retrofit twoRetrofit)
    {
        this.mContext = context;
        this.mOneService = oneRetrofit.create(OneService.class);
        this.mTestTwoService = twoRetrofit.create(TestTwoService.class);
    }

    @Override
    public Observable<GankEntity> gank(String type, int pageSize, String page)
    {
        return mOneService.gank(type,pageSize,page);
    }

    @Override
    public Observable<GankEntity> getRandomGirl()
    {
        return mOneService.getRandomGirl();
    }
}
