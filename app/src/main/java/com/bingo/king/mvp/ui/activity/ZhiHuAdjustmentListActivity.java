package com.bingo.king.mvp.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseTitleActivity;
import com.bingo.king.app.utils.ResourcesUtil;
import com.bingo.king.mvp.ui.adapter.AdjustmentAdapter;
import com.bingo.king.mvp.ui.fragment.ZhiHuFragment;
import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/12/6 10:53.
 */

public class ZhiHuAdjustmentListActivity extends BaseTitleActivity
{
    private List<String> mlist;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_default)
    TextView tv_default;

    private SPUtils spUtils;
    private AdjustmentAdapter mAdjustmentAdapter;

    @Override
    public int getContentLayoutId()
    {
        return R.layout.activity_zhihu_adjustment;
    }

    @Override
    public void setupActivityComponent()
    {

    }

    @Override
    protected void loadData(Bundle savedInstanceState)
    {
        super.loadData(savedInstanceState);
        setState(LoadingPage.STATE_SUCCESS);
//        setToolbarMiddleTitle("调整栏目顺序");
        setToorBarTitle("调整栏目顺序");
        init();
    }

    @OnClick({R.id.tv_default})
    public void onClick(View v)
    {
        restoreDefault();
    }

    private void restoreDefault()
    {
        SPUtils spUtils = SPUtils.getInstance();
        spUtils.put(ZhiHuFragment.ZH_LIST, "知乎日报&&知乎热门&&知乎主题&&知乎专栏&&");
        spUtils.put(ZhiHuFragment.ZH_LIST_IS_CHANGE, true);
        mAdjustmentAdapter.setNewData(getListString());
    }

    private List<String> getListString()
    {
        if (mlist == null)
        {
            mlist = new ArrayList<>();
        }
        mlist.clear();
        String zhListString = spUtils.getString(ZhiHuFragment.ZH_LIST);
        final String[] split = zhListString.split("&&");
        for (int i = 0; i < split.length; i++)
        {
            mlist.add(split[i]);
        }
        return mlist;
    }

    private void init()
    {
        tv_default.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        spUtils = SPUtils.getInstance();
        mAdjustmentAdapter = new AdjustmentAdapter(getListString());

        mRecyclerView.setAdapter(mAdjustmentAdapter);

        OnItemDragListener onItemDragListener = new OnItemDragListener()
        {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos)
            {
                TextView  tv = viewHolder.itemView.findViewById(R.id.tv_adjustment_name);
                tv.setTextColor(ResourcesUtil.getColor(R.color.C13));
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to)
            {
                StringBuilder homeListString = new StringBuilder();
                for (String name : mlist)
                {
                    homeListString.append(name).append("&&");
                }
                spUtils.put(ZhiHuFragment.ZH_LIST, homeListString.toString());
                spUtils.put(ZhiHuFragment.ZH_LIST_IS_CHANGE, true);
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos)
            {
                TextView  tv = viewHolder.itemView.findViewById(R.id.tv_adjustment_name);
                tv.setTextColor(ResourcesUtil.getColor(R.color.C6));
            }
        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(mAdjustmentAdapter));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        // 开启拖拽
        mAdjustmentAdapter.enableDragItem(mItemTouchHelper, R.id.iv, true);
        mAdjustmentAdapter.setOnItemDragListener(onItemDragListener);

    }

    @Override
    protected void retryRequestData()
    {

    }


}
