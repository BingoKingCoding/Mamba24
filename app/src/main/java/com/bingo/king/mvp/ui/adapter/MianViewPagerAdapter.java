package com.bingo.king.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bingo.king.app.utils.CategoryType;

import java.util.List;

/**
 */

public class MianViewPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> mFragments;
    public MianViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CategoryType.getPageTitleByPosition(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
