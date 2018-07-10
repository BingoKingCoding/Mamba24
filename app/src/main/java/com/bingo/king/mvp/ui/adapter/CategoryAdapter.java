package com.bingo.king.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseAdapter;
import com.bingo.king.app.utils.CategoryType;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/27 17:12.
 */

public class CategoryAdapter extends BaseAdapter<GankEntity.ResultsBean, BaseViewHolder>
{
    public CategoryAdapter(@Nullable List<GankEntity.ResultsBean> data)
    {
        super(R.layout.item_category, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankEntity.ResultsBean item)
    {
        helper.setText(R.id.tvDesc, item.desc);
        helper.setText(R.id.tvDate,item.publishedAt);
        helper.setText(R.id.tvDate,item.publishedAt);
        helper.setText(R.id.tvAuthor,item.who);
        ImageView ivImage = helper.getView(R.id.ivImage);
        switch (item.type) {
            case CategoryType.ANDROID_STR:
                ivImage.setImageResource(R.mipmap.ic_android);
                break;
            case CategoryType.IOS_STR:
                ivImage.setImageResource(R.mipmap.ic_apple);
                break;
            case CategoryType.QIAN_STR:
                ivImage.setImageResource(R.mipmap.ic_html);
                break;
        }
    }


}
