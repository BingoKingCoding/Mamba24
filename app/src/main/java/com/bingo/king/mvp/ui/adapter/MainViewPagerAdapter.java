package com.bingo.king.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bingo.king.app.utils.CollectionUtil;

import java.util.List;

/**
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> mFragments;
    private List<String> mTitleList;

    public MainViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments)
    {
        super(fm);
        this.mFragments = mFragments;
    }

    public MainViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments, List<String> mTitleList)
    {
        super(fm);
        this.mFragments = mFragments;
        this.mTitleList = mTitleList;
    }


    @Override
    public CharSequence getPageTitle(int position)
    {
        if (CollectionUtil.isEmpty(mTitleList))
        {
            return "";
        } else
        {
            return mTitleList.get(position);
        }
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
