package com.bingo.king.mvp.model.http;

import android.content.Context;

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
import com.bingo.king.mvp.model.http.apiservice.DoubanService;
import com.bingo.king.mvp.model.http.apiservice.GankIoService;
import com.bingo.king.mvp.model.http.apiservice.ZhiHuService;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * <获取数据具体实现>
 * Created by adou on 2017/11/2:22:29.
 */

public class RepositoryImpl implements IRepository
{
    private GankIoService mGankIoService;
    private ZhiHuService mZhiHuService;
    private DoubanService mDoubanService;
    private Context mContext;

    public RepositoryImpl(Context context, Retrofit gankIoRetrofit,Retrofit zhiHuRetrofit,Retrofit doubanRetrofit)
    {
        this.mContext = context;
        this.mGankIoService = gankIoRetrofit.create(GankIoService.class);
        this.mZhiHuService = zhiHuRetrofit.create(ZhiHuService.class);
        this.mDoubanService = doubanRetrofit.create(DoubanService.class);
    }

    @Override
    public Observable<GankEntity> gank(String type, int pageSize, String page)
    {
        return mGankIoService.gank(type,pageSize,page);
    }

    @Override
    public Observable<GankEntity> getRandomGirl()
    {
        return mGankIoService.getRandomGirl();
    }

    @Override
    public Observable<DailyListBean> requestDailyList()
    {
        return mZhiHuService.requestDailyList();
    }

    @Override
    public Observable<ThemeListBean> requestThemeList()
    {
        return mZhiHuService.requestThemeList();
    }

    @Override
    public Observable<ThemeChildListBean> requestThemeChildList(int id)
    {
        return mZhiHuService.requestThemeChildList(id);
    }

    @Override
    public Observable<SectionListBean> requestSectionList()
    {
        return mZhiHuService.requestSectionList();
    }

    @Override
    public Observable<SectionChildListBean> requestSectionChildList(int id)
    {
        return mZhiHuService.requestSectionChildList(id);
    }

    @Override
    public Observable<HotListBean> requestHotList()
    {
        return mZhiHuService.requestHotList();
    }

    @Override
    public Observable<ZhihuDetailBean> requestDetailInfo(int id)
    {
        return mZhiHuService.requestDetailInfo(id);
    }

    @Override
    public Observable<DetailExtraBean> requestDetailExtraInfo(int id)
    {
        return mZhiHuService.requestDetailExtraInfo(id);
    }

    @Override
    public Observable<CommentBean> requestLongCommentInfo(int id)
    {
        return mZhiHuService.requestLongCommentInfo(id);
    }

    @Override
    public Observable<CommentBean> requestShortCommentInfo(int id)
    {
        return mZhiHuService.requestShortCommentInfo(id);
    }

    @Override
    public Observable<HotMovieBean> requestHotMovie()
    {
        return mDoubanService.requestHotMovie();
    }

    @Override
    public Observable<MovieDetailBean> requestMovieDetail(String id)
    {
        return mDoubanService.requestMovieDetail(id);
    }

    @Override
    public Observable<HotMovieBean> requestMovieTop250(int start, int count)
    {
        return mDoubanService.requestMovieTop250(start,count);
    }


}
