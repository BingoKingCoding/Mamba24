package com.bingo.king.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseFragment;
import com.bingo.king.di.component.DaggerZhiHuComponent;
import com.bingo.king.di.module.ZhiHuModule;
import com.bingo.king.mvp.contract.ZhiHuContract;
import com.bingo.king.mvp.model.entity.zhihu.ZhiHuListBean;
import com.bingo.king.mvp.presenter.ZhiHuPresenter;
import com.blankj.utilcode.util.SPUtils;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/1 17:18.
 */

public class ZhiHuFragment extends BaseFragment<ZhiHuPresenter> implements ZhiHuContract.View
{
    private final static String ZH_LIST_IS_CHANGE = "zh_list_is_change";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private Banner mBanner;

    private List<ZhiHuListBean> homeList;

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.layout_refresh_list;
    }

    @Override
    protected void initComponent()
    {
        DaggerZhiHuComponent.builder()
                .appComponent(getAppComponent())
                .zhiHuModule(new ZhiHuModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        SPUtils spUtils = SPUtils.getInstance();
        if (spUtils.getBoolean(ZH_LIST_IS_CHANGE))
        {

        }
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {

    }

    @Override
    protected void retryRequestData()
    {
        mPresenter.requestData();
    }

    @Override
    public void showMessage(String message)
    {
        showSnackbar(message);
    }

    @Override
    public void hidePullLoading()
    {

    }

}
