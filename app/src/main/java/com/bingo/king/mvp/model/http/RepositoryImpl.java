package com.bingo.king.mvp.model.http;

import android.content.Context;

import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.model.entity.zhihu.CommentBean;
import com.bingo.king.mvp.model.entity.zhihu.DailyListBean;
import com.bingo.king.mvp.model.entity.zhihu.DetailExtraBean;
import com.bingo.king.mvp.model.entity.zhihu.HotListBean;
import com.bingo.king.mvp.model.entity.zhihu.SectionChildListBean;
import com.bingo.king.mvp.model.entity.zhihu.SectionListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeChildListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeListBean;
import com.bingo.king.mvp.model.entity.zhihu.ZhihuDetailBean;
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
    private GankIoService gankIoRetrofit;
    private ZhiHuService zhiHuRetrofit;
    private Context mContext;

    public RepositoryImpl(Context context, Retrofit gankIoRetrofit,Retrofit zhiHuRetrofit)
    {
        this.mContext = context;
        this.gankIoRetrofit = gankIoRetrofit.create(GankIoService.class);
        this.zhiHuRetrofit = zhiHuRetrofit.create(ZhiHuService.class);
    }

    @Override
    public Observable<GankEntity> gank(String type, int pageSize, String page)
    {
        return gankIoRetrofit.gank(type,pageSize,page);
    }

    @Override
    public Observable<GankEntity> getRandomGirl()
    {
        return gankIoRetrofit.getRandomGirl();
    }

    @Override
    public Observable<DailyListBean> requestDailyList()
    {
        return zhiHuRetrofit.requestDailyList();
    }

    @Override
    public Observable<ThemeListBean> requestThemeList()
    {
        return zhiHuRetrofit.requestThemeList();
    }

    @Override
    public Observable<ThemeChildListBean> requestThemeChildList(int id)
    {
        return zhiHuRetrofit.requestThemeChildList(id);
    }

    @Override
    public Observable<SectionListBean> requestSectionList()
    {
        return zhiHuRetrofit.requestSectionList();
    }

    @Override
    public Observable<SectionChildListBean> requestSectionChildList(int id)
    {
        return zhiHuRetrofit.requestSectionChildList(id);
    }

    @Override
    public Observable<HotListBean> requestHotList()
    {
        return zhiHuRetrofit.requestHotList();
    }

    @Override
    public Observable<ZhihuDetailBean> requestDetailInfo(int id)
    {
        return zhiHuRetrofit.requestDetailInfo(id);
    }

    @Override
    public Observable<DetailExtraBean> requestDetailExtraInfo(int id)
    {
        return zhiHuRetrofit.requestDetailExtraInfo(id);
    }

    @Override
    public Observable<CommentBean> requestLongCommentInfo(int id)
    {
        return zhiHuRetrofit.requestLongCommentInfo(id);
    }

    @Override
    public Observable<CommentBean> requestShortCommentInfo(int id)
    {
        return zhiHuRetrofit.requestShortCommentInfo(id);
    }


}
