package com.bingo.king.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseZhihuThemeActivity;
import com.bingo.king.app.utils.GlideUtils;
import com.bingo.king.di.component.DaggerZhihuThemeComponent;
import com.bingo.king.di.module.ZhihuThemeModule;
import com.bingo.king.mvp.contract.ZhihuThemeContract;
import com.bingo.king.mvp.model.entity.zhihu.SectionChildListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeChildListBean;
import com.bingo.king.mvp.presenter.ZhihuThemePresenter;
import com.bingo.king.mvp.ui.adapter.ZhihuSectionAdapter;
import com.bingo.king.mvp.ui.adapter.ZhihuThemeAdapter;
import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;


public class ZhihuThemeActivity extends BaseZhihuThemeActivity<ZhihuThemePresenter> implements ZhihuThemeContract.View
{

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    private ZhihuThemeAdapter zhihuThemeAdapter;
    private ZhihuSectionAdapter zhihuSectionAdapter;
    private int id;
    private List storiesList;
    private int sectionId;
    private String isSection;

    @Override
    public void setupActivityComponent()
    {
        DaggerZhihuThemeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .zhihuThemeModule(new ZhihuThemeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int getContentLayoutId()
    {
        return R.layout.activity_zhihu_theme; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void loadData(Bundle savedInstanceState)
    {
        //setState(LoadingPage.STATE_SUCCESS);//如果不需要网络请求的话可以去掉注释 直接设置成功状态
        getIntentData();
        init();//可以在此进行初始化
        requestData();
    }

    private void requestData()
    {
        if (sectionId > 0)
        {
            mPresenter.requestSectionChildList(sectionId);
        } else
        {
            mPresenter.requestThemeChildList(id);
        }
    }

    private void getIntentData()
    {
        id = getIntent().getIntExtra("id", 0);
        sectionId = getIntent().getIntExtra("section_id", 0);
    }

    private void init()
    {
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (sectionId > 0)
        {
            zhihuSectionAdapter = new ZhihuSectionAdapter(storiesList);
            zhihuSectionAdapter.setOnZhihuThemeItemClick(this::startZhiHuDetailActivity);
            zhihuSectionAdapter.setDefaultEmptyView(mRecyclerView);
            mRecyclerView.setAdapter(zhihuSectionAdapter);
        } else
        {
            zhihuThemeAdapter = new ZhihuThemeAdapter(storiesList);
            zhihuThemeAdapter.setOnZhihuThemeItemClick(this::startZhiHuDetailActivity);
            zhihuThemeAdapter.setDefaultEmptyView(mRecyclerView);
            mRecyclerView.setAdapter(zhihuThemeAdapter);
        }

        appbarThemeChild.addOnOffsetChangedListener((appBarLayout, verticalOffset) ->
        {
            if (verticalOffset >= 0)
            {
                swipeRefresh.setEnabled(true);
            } else
            {
                swipeRefresh.setEnabled(false);
                float rate = (float) (ConvertUtils.dp2px(256) + verticalOffset * 2) / ConvertUtils.dp2px(256);
                if (rate >= 0)
                    ivThemeChildOrigin.setAlpha(rate);
            }
        });

        swipeRefresh.setOnRefreshListener(() ->
        {
            if (sectionId > 0)
            {
                mPresenter.requestSectionChildList(sectionId);
            } else
            {
                mPresenter.requestThemeChildList(id);
            }
        });


    }

    @Override
    protected void retryRequestData()
    {
        requestData();
    }


    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void closeLoadingDialog() {

    }

    @Override
    public void hidePullLoading()
    {

    }

    @Override
    public void showMessage(String message)
    {
        showSnackbar(message);
    }



    private void startZhiHuDetailActivity(int id, View view) {
        Intent intent = new Intent();
        intent.setClass(this, ZhihuDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("isNotTransition", true);
        /*
         * 用这个ActivityOptionsCompat比用ActivityOptions兼容性更好，前者是V4下的兼容到16后者到21.
         * ActivityOptionsCompat.makeSceneTransitionAnimation(）的第三个参数则是跳转后图片显示的transitionName的值
         *     <android.support.design.widget.AppBarLayout
         android:transitionName="zhihu_detail_title"
         android:fitsSystemWindows="true">
         */
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                view, this.getResources().getString(R.string.transition_image));
        startActivity(intent, options.toBundle());
    }


    @Override
    public void refreshView(ThemeChildListBean data) {
        storiesList = data.getStories();
        zhihuThemeAdapter.setNewData(storiesList);
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        initToolBar(toolbarThemeBase,true, data.getName());
        GlideUtils.getInstance().load(this, data.getBackground(), ivThemeChildOrigin);
        Glide.with(this).load(data.getBackground()).bitmapTransform(new BlurTransformation(this)).into(ivThemeChildBlur);
        tvThemeChildDes.setText(data.getDescription());
    }

    @Override
    public void refreshSectionData(SectionChildListBean data) {
        storiesList = data.getStories();
        zhihuSectionAdapter.setNewData(storiesList);
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        initToolBar(toolbarThemeBase, true,data.getName());
        tvThemeChildDes.setText(data.getName());
    }




}
