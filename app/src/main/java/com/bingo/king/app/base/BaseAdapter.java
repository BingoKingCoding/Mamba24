package com.bingo.king.app.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.bingo.king.R;
import com.bingo.king.mvp.ui.widget.EasyLoadMoreView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * <适配器基类>
 * Created by wwb on 2017/12/12 09:57.
 */

public abstract class BaseAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K>
{
    public BaseAdapter(@LayoutRes int layoutResId)
    {
        super(layoutResId);
        init();
    }

    public BaseAdapter(@Nullable List<T> data)
    {
        super(data);
        init();
    }

    public BaseAdapter(@LayoutRes int layoutResId, @Nullable List<T> data)
    {
        super(layoutResId, data);
        init();
    }

    private void init()
    {
        EasyLoadMoreView easyLoadMoreView = new EasyLoadMoreView();
        this.setLoadMoreView(easyLoadMoreView);
        this.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        this.isFirstOnly(false);
    }

    /**
     * 需要先绑定recyclerView后再调用,否则会报错
     */
    public void setDefaultEmptyView(RecyclerView recyclerView)
    {
        this.setEmptyView(R.layout.loadingpage_state_empty, recyclerView);
    }


    public void setScaleinloadAnimation()
    {
        this.openLoadAnimation(BaseQuickAdapter.SCALEIN);
    }

    public void setAlphainloadAnimation()
    {
        this.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }


}
