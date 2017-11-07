package com.bingo.king.mvp.model.http.apiservice;

import com.bingo.king.mvp.model.entity.GankEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/3 16:57.
 */

public interface OneService
{
    @GET("api/data/{type}/{pageSize}/{page}")
    Observable<GankEntity> gank(@Path("type") String type, @Path("pageSize") int pageSize, @Path("page") String page);

    // 随机获取一个妹子
    @GET("api/random/data/福利/1")
    Observable<GankEntity> getRandomGirl();
}
