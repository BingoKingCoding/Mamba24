package com.bingo.king.mvp.contract;

import com.bingo.king.app.base.IModel;
import com.bingo.king.app.base.IView;
import com.bingo.king.mvp.model.entity.zhihu.DailyListBean;
import com.bingo.king.mvp.model.entity.zhihu.HotListBean;
import com.bingo.king.mvp.model.entity.zhihu.SectionListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeListBean;
import com.bingo.king.mvp.model.entity.zhihu.ZhiHuListBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/1 17:14.
 */

public interface ZhiHuContract
{
    interface View extends IView
    {
        void refreshView(List<ZhiHuListBean> zhiHuList);
    }

    interface Model extends IModel
    {
        Observable<DailyListBean> requestDailyList();

        Observable<HotListBean> requestHotList();

        Observable<ThemeListBean> requestThemeList();

        Observable<SectionListBean> requestSectionList();
    }
}
