package com.bingo.king.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.bingo.king.R;
import com.bingo.king.app.utils.GlideUtils;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * <收藏页面妹子福利>
 * Created by wwb on 2017/9/27 16:43.
 */

public class MeiziAdapter extends BaseQuickAdapter<DaoGankEntity,BaseViewHolder>
{
    public MeiziAdapter(@Nullable List<DaoGankEntity> data)
    {
        super(R.layout.item_collection_girls,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DaoGankEntity item)
    {
        GlideUtils.loadImage(1,item.url,helper.getView(R.id.network_imageview));
    }

}
