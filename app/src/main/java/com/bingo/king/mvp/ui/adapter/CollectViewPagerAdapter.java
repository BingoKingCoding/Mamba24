package com.bingo.king.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/27 16:29.
 */

public class CollectViewPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> mFragments;
    String[] title = {"妹子","文章"};
    public CollectViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments)
    {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return title[position];
    }

    @Override
    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }

    @Override
    public int getCount()
    {
        return mFragments.size();
    }
}
