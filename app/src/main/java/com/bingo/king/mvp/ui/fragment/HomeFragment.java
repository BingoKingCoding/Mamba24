package com.bingo.king.mvp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.app.base.BaseLazyFragment;
import com.bingo.king.app.utils.CategoryType;
import com.bingo.king.app.utils.ViewBinder;
import com.bingo.king.di.component.DaggerHomeComponent;
import com.bingo.king.di.module.HomeModule;
import com.bingo.king.mvp.contract.HomeContract;
import com.bingo.king.mvp.presenter.HomePresenter;
import com.bingo.king.mvp.ui.adapter.MainViewPagerAdapter;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.blankj.utilcode.util.SPUtils;

import butterknife.BindView;

/**
 * <首页>
 * Created by adou on 2017/11/6:20:26.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.mainPager)
    ViewPager mainPager;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initComponent() {
        DaggerHomeComponent.builder()
                .appComponent(getAppComponent())
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void hidePullLoading() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPager = null;
        tabs = null;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ViewBinder.setTextView(toolbar_title, "首页");
        //初始化首页栏目顺序
        SPUtils spUtils = SPUtils.getInstance();
        if (!spUtils.getBoolean(ZhiHuFragment.ZH_LIST_IS_CHANGE)) {
            spUtils.put(ZhiHuFragment.ZH_LIST, "知乎日报&&知乎热门&&知乎主题&&知乎专栏&&");
            spUtils.put(ZhiHuFragment.ZH_LIST_IS_CHANGE, true);
        }

        mainPager.setOffscreenPageLimit(5);
        mainPager.setAdapter(new MainViewPagerAdapter(getChildFragmentManager(), CategoryType.getFragments(), CategoryType.getPageTitleList()));
        tabs.setupWithViewPager(mainPager);
        setState(LoadingPage.STATE_SUCCESS);
    }
}
