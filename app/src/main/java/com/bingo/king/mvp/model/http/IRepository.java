package com.bingo.king.mvp.model.http;

import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.entity.douban.HotMovieBean;
import com.bingo.king.mvp.model.entity.douban.MovieDetailBean;
import com.bingo.king.mvp.model.entity.zhihu.CommentBean;
import com.bingo.king.mvp.model.entity.zhihu.DailyListBean;
import com.bingo.king.mvp.model.entity.zhihu.DetailExtraBean;
import com.bingo.king.mvp.model.entity.zhihu.HotListBean;
import com.bingo.king.mvp.model.entity.zhihu.SectionChildListBean;
import com.bingo.king.mvp.model.entity.zhihu.SectionListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeChildListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeListBean;
import com.bingo.king.mvp.model.entity.zhihu.ZhihuDetailBean;

import io.reactivex.Observable;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * <获取数据的接口>
 * Created by adou on 2017/11/2:22:25.
 */
public interface IRepository
{
    /**
     * 干货
     */
    Observable<GankEntity> gank(String type,int pageSize,String page);
    /**
     * 福利
     */
    Observable<GankEntity> getRandomGirl();
    /**
     * 最新日报
     */
    Observable<DailyListBean> requestDailyList();
    /**
     * 主题日报
     */
    Observable<ThemeListBean> requestThemeList();
    /**
     * 主题日报详情
     */
    Observable<ThemeChildListBean> requestThemeChildList(int id);

    /**
     * 专栏日报
     */
    Observable<SectionListBean> requestSectionList();


    /**
     * 专栏日报详情
     */
    Observable<SectionChildListBean> requestSectionChildList(int id);

    /**
     * 热门日报
     */
    Observable<HotListBean> requestHotList();

    /**
     * 日报详情
     */
    Observable<ZhihuDetailBean> requestDetailInfo(int id);

    /**
     * 日报的额外信息
     */
    Observable<DetailExtraBean> requestDetailExtraInfo(int id);

    /**
     * 日报的长评论
     */
    Observable<CommentBean> requestLongCommentInfo(int id);

    /**
     * 日报的短评论
     */
    Observable<CommentBean> requestShortCommentInfo(int id);



    /**
     * 豆瓣热映电影，每日更新
     */
    Observable<HotMovieBean> requestHotMovie();

    /**
     * 获取电影详情
     *
     * @param id 电影bean里的id
     */
    Observable<MovieDetailBean> requestMovieDetail(@Path("id") String id);

    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    Observable<HotMovieBean> requestMovieTop250(@Query("start") int start, @Query("count") int count);

}
