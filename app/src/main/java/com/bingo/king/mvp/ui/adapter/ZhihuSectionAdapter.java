package com.bingo.king.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseAdapter;
import com.bingo.king.app.utils.GlideUtils;
import com.bingo.king.mvp.model.entity.zhihu.SectionChildListBean;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 */

public class ZhihuSectionAdapter extends BaseAdapter<SectionChildListBean.StoriesBean,BaseViewHolder>
{

    public ZhihuSectionAdapter(List<SectionChildListBean.StoriesBean> data) {
        super(R.layout.item_theme,data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SectionChildListBean.StoriesBean item) {

        if (item.getImages()!=null&&item.getImages().size()>0){
            GlideUtils.getInstance().load(mContext,item.getImages().get(0), (ImageView) helper.getView(R.id.iv_theme_item_image));
        }
        TextView tvThemeItemTitle= helper.getView(R.id.tv_theme_item_title);
        tvThemeItemTitle.setText(item.getTitle());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick!=null){
                    onItemClick.onItemClick(item.getId(),helper.getView(R.id.iv_theme_item_image));
                }
            }
        });

    }
    private  OnItemClick onItemClick;
    public interface OnItemClick{
        void onItemClick(int id, View view);
    }
    public void setOnZhihuThemeItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }
}
