package com.bingo.king.mvp.model.http.apiservice;


import com.bingo.king.mvp.model.entity.douban.HotMovieBean;
import com.bingo.king.mvp.model.entity.douban.MovieDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 */

public interface DoubanService
{
    /**
     * 豆瓣热映电影，每日更新
     */
    @GET("v2/movie/in_theaters?apikey=0df993c66c0c636e29ecbb5344252a4a")
    Observable<HotMovieBean> requestHotMovie();

    /**
     * 获取电影详情
     *
     * @param id 电影bean里的id
     */
    @GET("v2/movie/subject/{id}")
    Observable<MovieDetailBean> requestMovieDetail(@Path("id") String id);

    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @GET("v2/movie/top250")
    Observable<HotMovieBean> requestMovieTop250(@Query("start") int start, @Query("count") int count);
}
