package com.bingo.king.mvp.model.entity.zhihu;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * 知乎页面列表
 */

public class ZhiHuListBean implements MultiItemEntity
{
   private String title;

    /**
     * type = 0是表示标题 1表示文字加一张图片 2：3张图片 3：2张图片 4：2图片+1张图片
     */
    public static final int TITLE = 0;//标题
    public static final int DAILY = 1;//日报
    public static final int HOT = 2;//热门
    public static final int THEME = 3;//主题
    public static final int SECTION = 4;//专栏

    private int type;

    private DailyListBean.StoriesBean dailyList;

    private List<HotListBean.RecentBean> hotList;

    private List<ThemeListBean.OthersBean> themeList;

    private List<SectionListBean.DataBean> sectionList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DailyListBean.StoriesBean getDailyList() {
        return dailyList;
    }

    public void setDailyList(DailyListBean.StoriesBean dailyList) {
        this.dailyList = dailyList;
    }

    public List<HotListBean.RecentBean> getHotList() {
        return hotList;
    }

    public void setHotList(List<HotListBean.RecentBean> hotList) {
        this.hotList = hotList;
    }

    public List<ThemeListBean.OthersBean> getThemeList() {
        return themeList;
    }

    public void setThemeList(List<ThemeListBean.OthersBean> themeList) {
        this.themeList = themeList;
    }

    public List<SectionListBean.DataBean> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<SectionListBean.DataBean> sectionList) {
        this.sectionList = sectionList;
    }

    @Override
    public String toString() {
        return "HomeListBean{" +
                "title='" + title + '\'' +
                ", type=" + type +
                ", dailyList=" + dailyList +
                ", hotList=" + hotList +
                ", themeList=" + themeList +
                ", sectionList=" + sectionList +
                '}';
    }

    @Override
    public int getItemType() {
        return getType();
    }
}
