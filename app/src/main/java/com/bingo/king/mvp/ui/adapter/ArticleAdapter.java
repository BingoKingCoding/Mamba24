package com.bingo.king.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bingo.king.R;
import com.bingo.king.app.utils.CategoryType;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/27 17:12.
 */

public class ArticleAdapter extends BaseQuickAdapter<DaoGankEntity,BaseViewHolder>
{
    public ArticleAdapter(@Nullable List<DaoGankEntity> data)
    {
        super(R.layout.item_collection,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DaoGankEntity item)
    {
        helper.setText(R.id.tvDesc, item.desc);
        ImageView ivImage = helper.getView(R.id.ivImage);
        if (item.type.equals(CategoryType.ANDROID_STR)){
            ivImage.setImageResource(R.mipmap.ic_android);
        }else  if (item.type.equals(CategoryType.IOS_STR)){
            ivImage.setImageResource(R.mipmap.ic_apple);
        }else  if (item.type.equals(CategoryType.QIAN_STR)){
            ivImage.setImageResource(R.mipmap.ic_html);
        }
    }
}
