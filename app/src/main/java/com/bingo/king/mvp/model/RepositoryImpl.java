package com.bingo.king.mvp.model;

import android.content.Context;

import com.bingo.king.mvp.model.entity.TestBean;

import io.reactivex.Observable;

/**
 * <获取数据具体实现>
 * Created by adou on 2017/11/2:22:29.
 */

public class RepositoryImpl implements IRepository
{

    private ApiService mApiService;
    private Context mContext;

    public RepositoryImpl(Context context, Retrofit retrofit)
    {
        this.mContext = context;
        this.mApiService = retrofit.create(ApiService.class);
    }

    @Override
    public Observable<TestBean> requestPraiseMovie()
    {
        return mApiService.requestPraiseMovie();
    }
}
