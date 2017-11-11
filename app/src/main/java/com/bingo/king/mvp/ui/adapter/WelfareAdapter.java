package com.bingo.king.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.bingo.king.R;
import com.bingo.king.app.utils.GlideUtils;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class WelfareAdapter extends BaseQuickAdapter<GankEntity.ResultsBean,BaseViewHolder>
{
    public WelfareAdapter(@Nullable List<GankEntity.ResultsBean> data)
    {
        super(R.layout.item_girls, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankEntity.ResultsBean item)
    {
        GlideUtils.getInstance().loadImageCenterCrop(item.url,helper.getView(R.id.ivImage));
    }

}
