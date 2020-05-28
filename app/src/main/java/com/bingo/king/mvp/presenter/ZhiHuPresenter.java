package com.bingo.king.mvp.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bingo.king.app.base.BasePresenter;
import com.bingo.king.di.scope.ActivityScope;
import com.bingo.king.mvp.contract.ZhiHuContract;
import com.bingo.king.mvp.model.entity.zhihu.DailyListBean;
import com.bingo.king.mvp.model.entity.zhihu.HotListBean;
import com.bingo.king.mvp.model.entity.zhihu.SectionListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeListBean;
import com.bingo.king.mvp.model.entity.zhihu.ZhiHuListBean;
import com.bingo.king.mvp.model.http.rxerrorhandler.HttpCallback;
import com.bingo.king.mvp.ui.fragment.ZhiHuFragment;
import com.blankj.utilcode.util.SPUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/1 17:16.
 */
@ActivityScope
public class ZhiHuPresenter extends BasePresenter<ZhiHuContract.Model, ZhiHuContract.View>
{
    @Inject
    public ZhiHuPresenter(ZhiHuContract.Model model, ZhiHuContract.View rootView)
    {
        super(model, rootView);
    }

    private List<ZhiHuListBean> homeList = new ArrayList<>();


    public List<ZhiHuListBean> getListZhiHu()
    {
        List<ZhiHuListBean> newHomeList = new ArrayList<>();
        int daily = 0;
        int hot = 0;
        int theme = 0;
        int section = 0;
        SPUtils spUtils = SPUtils.getInstance();
        String homeListString = spUtils.getString(ZhiHuFragment.ZH_LIST);
        String[] split = homeListString.split("&&");
        //for循环确定位置
        for (int i = 0; i < split.length; i++)
        {
            if ("知乎日报".equals(split[i]))
            {
                daily = i + 1;
                continue;
            }
            if ("知乎热门".equals(split[i]))
            {
                hot = i + 1;
                continue;
            }
            if ("知乎主题".equals(split[i]))
            {
                theme = i + 1;
                continue;
            }
            if ("知乎专栏".equals(split[i]))
            {
                section = i + 1;
            }
        }
        //添加标题
        for (int y = 1; y <= 4; y++)
        {
            if (daily == y)
            {
                checkAddToNewHomeList("知乎日报", 1, newHomeList);
                continue;
            }
            if (hot == y)
            {
                checkAddToNewHomeList("知乎热门", 2, newHomeList);
                continue;
            }
            if (theme == y)
            {
                checkAddToNewHomeList("知乎主题", 3, newHomeList);
                continue;
            }
            if (section == y)
            {
                checkAddToNewHomeList("知乎专栏", 4, newHomeList);
            }
        }
        return newHomeList;
    }

    private void checkAddToNewHomeList(String title, int type, List<ZhiHuListBean> newHomeList)
    {
        for (int i = 1; i <= homeList.size(); i++)
        {
            if (!TextUtils.isEmpty(title) && title.equals(homeList.get(i - 1).getTitle()))
            {
                newHomeList.add(homeList.get(i - 1));
            }

            if (homeList.get(i - 1).getType() == type)
            {
                newHomeList.add(homeList.get(i - 1));
            }
        }
    }

    private List<DailyListBean.TopStoriesBean> topStoriesList = new ArrayList<>();

    public List<DailyListBean.TopStoriesBean> getTopStoriesList()
    {
        return topStoriesList;
    }

    public void requestZhiHuData()
    {
        requestDailyData();
    }

    private void requestDailyData()
    {
        requestInitializeData(mModel.requestDailyList(), new HttpCallback<DailyListBean>()
        {
            @Override
            public void onSuccess(DailyListBean data)
            {
                topStoriesList = data.getTop_stories();
                List<DailyListBean.StoriesBean> storiesBeanList = data.getStories();
                settitle("知乎日报");
                for (int i = 0; i < 3; i++)
                {
                    ZhiHuListBean homeListBean = settype(1);
                    homeListBean.setDailyList(storiesBeanList.get(i));
                    homeList.add(homeListBean);
                }
                requestHotList();
            }
        });
    }

    private void requestHotList()
    {
        requestInitializeData(mModel.requestHotList(), new HttpCallback<HotListBean>()
        {
            @Override
            public void onSuccess(HotListBean data)
            {
                List<HotListBean.RecentBean> recent = data.getRecent();
                settitle("知乎热门");
                List<HotListBean.RecentBean> hotList = new ArrayList<>();
                List<HotListBean.RecentBean> hotList2 = new ArrayList<>();
                for (int i = 0; i < 6; i++)
                {
                    if (i < 3)
                    {
                        hotList.add(recent.get(i));
                    } else
                    {
                        hotList2.add(recent.get(i));
                    }
                }
                ZhiHuListBean homeListBean = settype(2);
                homeListBean.setHotList(hotList);
                homeList.add(homeListBean);
                ZhiHuListBean homeListBean2 = settype(2);
                homeListBean2.setHotList(hotList2);
                homeList.add(homeListBean2);
                requestThemeList();
            }
        });
    }

    private void requestThemeList()
    {
        requestInitializeData(mModel.requestThemeList(), new HttpCallback<ThemeListBean>()
        {
            @Override
            public void onSuccess(ThemeListBean data)
            {
                List<ThemeListBean.OthersBean> others = data.getOthers();
                settitle("知乎主题");
                List<ThemeListBean.OthersBean> themeList = new ArrayList<>();
                List<ThemeListBean.OthersBean> themeList2 = new ArrayList<>();
//                int random = new Random().nextInt(4);
//                for (int i = random; i < random + 4; i++)
//                {
//                    if (i < random + 2)
//                    {
//                        themeList.add(others.get(i));
//                    } else
//                    {
//                        themeList2.add(others.get(i));
//                    }
//                }
                ZhiHuListBean homeListBean = settype(3);
                homeListBean.setThemeList(themeList);
                homeList.add(homeListBean);
                ZhiHuListBean homeListBean2 = settype(3);
                homeListBean2.setThemeList(themeList2);
                homeList.add(homeListBean2);
                fetchSectionList();
            }
        });
    }

    private void fetchSectionList()
    {
        requestInitializeData(mModel.requestSectionList(), new HttpCallback<SectionListBean>()
        {
            @Override
            public void onSuccess(SectionListBean data)
            {
                List<SectionListBean.DataBean> data1 = data.getData();
                settitle("知乎专栏");
                List<SectionListBean.DataBean> sectionList = new ArrayList<>();
                int random = new Random().nextInt(4);
                for (int i = random; i < random + 3; i++)
                {
                    sectionList.add(data1.get(i));
                }
                ZhiHuListBean homeListBean = settype(4);
                homeListBean.setSectionList(sectionList);
                homeList.add(homeListBean);
                mRootView.refreshView(getListZhiHu());
            }
        });

    }

    private void settitle(String title)
    {
        ZhiHuListBean homeListBean = settype(0);
        homeListBean.setTitle(title);
        homeList.add(homeListBean);
    }

    @NonNull
    private ZhiHuListBean settype(int type)
    {
        ZhiHuListBean homeListBean = new ZhiHuListBean();
        homeListBean.setType(type);
        return homeListBean;
    }
}
