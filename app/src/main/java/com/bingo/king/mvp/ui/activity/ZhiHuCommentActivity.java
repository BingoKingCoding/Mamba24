package com.bingo.king.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseTitleActivity;
import com.bingo.king.mvp.ui.adapter.MainViewPagerAdapter;
import com.bingo.king.mvp.ui.fragment.ZhiHuCommentFragment;
import com.bingo.king.mvp.ui.widget.LoadingPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;


public class ZhiHuCommentActivity extends BaseTitleActivity
{
    @BindView(R.id.tab_zhihu_comment)
    TabLayout tabZhihuComment;
    @BindView(R.id.vp_zhihu_comment)
    ViewPager vpZhihuComment;

    private int shortNum;
    private int longNum;
    private int id;

    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private MainViewPagerAdapter mAdapter;


    @Override
    public void setupActivityComponent()
    {
    }

    @Override
    public int getContentLayoutId()
    {
        return R.layout.activity_zhi_hu_comment; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void loadData(Bundle savedInstanceState)
    {
        super.loadData(savedInstanceState);
        setState(LoadingPage.STATE_SUCCESS);//如果不需要网络请求的话可以去掉注释 直接设置成功状态
        init();//可以在此进行初始化
    }

    private void init()
    {
        getIntentData();
        initFragment();
    }

    private void getIntentData()
    {
        Intent intent = getIntent();
        int allNum = intent.getExtras().getInt("allNum");
        shortNum = intent.getExtras().getInt("shortNum");
        longNum = intent.getExtras().getInt("longNum");
        id = intent.getExtras().getInt("id");
        setToolBarTitle(String.format(Locale.getDefault(), getResources().getString(R.string.zh_comment), allNum));
    }

    private void initFragment()
    {
        if (mTitleList.size() != 0)
        {
            return;
        }
        mTitleList.add(String.format(Locale.getDefault(),getString(R.string.zh_short_comment), shortNum));
        mFragmentList.add(ZhiHuCommentFragment.newInstance(true));
        mTitleList.add(String.format(Locale.getDefault(),getString(R.string.zh_long_comment),longNum));
        mFragmentList.add(ZhiHuCommentFragment.newInstance(false));

        mAdapter = new MainViewPagerAdapter(getSupportFragmentManager(),mFragmentList,mTitleList);
        vpZhihuComment.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tabZhihuComment.setupWithViewPager(vpZhihuComment);
    }

    @Override
    protected void retryRequestData()
    {

    }

    public int getId()
    {
        return id;
    }
}
