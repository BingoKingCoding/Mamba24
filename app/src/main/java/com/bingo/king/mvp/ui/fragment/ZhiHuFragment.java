package com.bingo.king.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseLazyFragment;
import com.bingo.king.app.utils.BannerImageLoader;
import com.bingo.king.di.component.DaggerZhiHuComponent;
import com.bingo.king.di.module.ZhiHuModule;
import com.bingo.king.mvp.contract.ZhiHuContract;
import com.bingo.king.mvp.model.entity.zhihu.DailyListBean;
import com.bingo.king.mvp.model.entity.zhihu.ZhiHuListBean;
import com.bingo.king.mvp.presenter.ZhiHuPresenter;
import com.bingo.king.mvp.ui.activity.WebActivity;
import com.bingo.king.mvp.ui.activity.ZhiHuAdjustmentListActivity;
import com.bingo.king.mvp.ui.activity.ZhihuDetailActivity;
import com.bingo.king.mvp.ui.activity.ZhihuThemeActivity;
import com.bingo.king.mvp.ui.adapter.ZhiHuAdapter;
import com.blankj.utilcode.util.SPUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/1 17:18.
 */

public class ZhiHuFragment extends BaseLazyFragment<ZhiHuPresenter> implements ZhiHuContract.View {
    public final static String ZH_LIST_IS_CHANGE = "zh_list_is_change";
    public final static String ZH_LIST = "zh_list";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private Banner mBanner;

    private ZhiHuAdapter mAdapter;

    private List<ZhiHuListBean> mZhiHuList;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_zhihu;
    }

    @Override
    protected void initComponent() {
        DaggerZhiHuComponent.builder()
                .appComponent(getAppComponent())
                .zhiHuModule(new ZhiHuModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        SPUtils spUtils = SPUtils.getInstance();
        if (spUtils.getBoolean(ZH_LIST_IS_CHANGE)) {
            mZhiHuList = mPresenter.getListZhiHu();
            if (mAdapter != null) {
                mAdapter.setNewData(mZhiHuList);
                mAdapter.notifyDataSetChanged();
                spUtils.put(ZH_LIST_IS_CHANGE, false);
            }
        }
        if (mBanner != null) {
            mBanner.start();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initView();
        mPresenter.requestZhiHuData();
        SPUtils spUtils = SPUtils.getInstance();
        if (spUtils.getBoolean(ZH_LIST_IS_CHANGE)) {
            if (mPresenter != null) {
                mZhiHuList = mPresenter.getListZhiHu();
            }
            if (mAdapter != null) {
                mAdapter.setNewData(mZhiHuList);
                spUtils.put(ZH_LIST_IS_CHANGE, false);
            }
        }
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        View footerView = getActivity().getLayoutInflater().inflate(R.layout.item_zhihu_footer, (ViewGroup) mRecyclerView.getParent(), false);
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.item_zhihu_header, (ViewGroup) mRecyclerView.getParent(), false);

        mAdapter = new ZhiHuAdapter(null);
        mAdapter.addHeaderView(headerView);
        mAdapter.addFooterView(footerView);

        mBanner = headerView.findViewById(R.id.banner);

        ImageButton ibXiandu = headerView.findViewById(R.id.ib_xiandu);
        TextView tvZhihuFooter = footerView.findViewById(R.id.tv_zhihu_footer);
        tvZhihuFooter.setOnClickListener(v -> startActivity(new Intent(getActivity(), ZhiHuAdjustmentListActivity.class)));
        ibXiandu.setOnClickListener(v -> WebActivity.loadUrl(getActivity(), "https://gank.io/xiandu", "加载中..."));

        mAdapter.setOnItemClickListener(new ZhiHuAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int id, View view) {
                startZhiHuDetailActivity(id, view);
            }

            @Override
            public void OnItemThemeClickListener(int id, View view) {
                startZhihuThemeActivity("id", id, view);
            }

            @Override
            public void OnItemSectionClickListener(int id, View view) {
                startZhihuThemeActivity("section_id", id, view);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void retryRequestData() {
        mPresenter.requestZhiHuData();
    }


    @Override
    public void hidePullLoading() {

    }

    @Override
    public void refreshView(List<ZhiHuListBean> zhiHuList) {
        mZhiHuList = zhiHuList;
        List<DailyListBean.TopStoriesBean> topStoriesList = mPresenter.getTopStoriesList();
        if (mZhiHuList.size() == 12) {
            initBanner(topStoriesList);
            mAdapter.setNewData(mZhiHuList);
        }
    }

    private void initBanner(final List<DailyListBean.TopStoriesBean> topStoriesList) {
        mBanner.startAutoPlay();
        mBanner.setDelayTime(3000);
        List<String> imageList = new ArrayList<>();
        for (DailyListBean.TopStoriesBean topStoriesBean : topStoriesList) {
            imageList.add(topStoriesBean.getImage());
        }
        mBanner.setImages(imageList).setImageLoader(new BannerImageLoader()).start();
        mBanner.setOnBannerListener(position ->
        {
            int id = topStoriesList.get(position).getId();
            startZhiHuDetailActivity(id, mBanner);
        });
    }

    private void startZhihuThemeActivity(String name, int id, View view) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ZhihuThemeActivity.class);
        intent.putExtra(name, id);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                view, getActivity().getResources().getString(R.string.transition_image));
        getActivity().startActivity(intent, options.toBundle());
    }

    private void startZhiHuDetailActivity(int id, View view) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ZhihuDetailActivity.class);
        intent.putExtra(ZhihuDetailActivity.EXTRA_ID, id);
//        intent.putExtra("isNotTransition", true);
        /**
         * 用这个ActivityOptionsCompat比用ActivityOptions兼容性更好，前者是V4下的兼容到16后者到21.
         * ActivityOptionsCompat.makeSceneTransitionAnimation(）的第三个参数则是跳转后图片显示的transitionName的值
         *
         *     < android.support.design.widget.AppBarLayout
         *       android:transitionName="transition_image"
         *       android:fitsSystemWindows="true">
         */
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                view, getActivity().getResources().getString(R.string.transition_image));
        getActivity().startActivity(intent, options.toBundle());
    }


}
