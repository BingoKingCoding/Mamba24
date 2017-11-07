package com.bingo.king.mvp.model.http;

import com.bingo.king.mvp.model.entity.GankEntity;

import io.reactivex.Observable;

/**
 * <获取数据的接口>
 * Created by adou on 2017/11/2:22:25.
 */
public interface IRepository
{
    Observable<GankEntity> gank(String type,int pageSize,String page);
    Observable<GankEntity> getRandomGirl();
}
