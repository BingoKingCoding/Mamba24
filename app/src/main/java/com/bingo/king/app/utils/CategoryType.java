package com.bingo.king.app.utils;

import android.support.v4.app.Fragment;

import com.bingo.king.mvp.ui.fragment.CategoryFragment;
import com.bingo.king.mvp.ui.fragment.ZhiHuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class CategoryType
{

    public static final String ZHIHU_NEWS = "知乎日报";
    public static final String NEW_MOVIE = "最新电影";
    public static final String ANDROID_STR = "Android";
    public static final String IOS_STR = "iOS";
    public static final String QIAN_STR = "前端";
    public static final String GIRLS_STR = "福利";


    public static List<Fragment> getFragments()
    {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(new ZhiHuFragment());
        mFragments.add(CategoryFragment.newInstance(CategoryType.QIAN_STR));
        mFragments.add(CategoryFragment.newInstance(CategoryType.ANDROID_STR));
        mFragments.add(CategoryFragment.newInstance(CategoryType.IOS_STR));
        mFragments.add(CategoryFragment.newInstance(CategoryType.QIAN_STR));
        return mFragments;
    }

    public static List<String> getPageTitleList()
    {
        List<String> list = new ArrayList<>();
        list.add(ZHIHU_NEWS);
        list.add(NEW_MOVIE);
        list.add(ANDROID_STR);
        list.add(IOS_STR);
        list.add(QIAN_STR);
        return list;
    }

}
