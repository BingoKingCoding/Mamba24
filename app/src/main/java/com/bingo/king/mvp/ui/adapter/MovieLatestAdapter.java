package com.bingo.king.mvp.ui.adapter;

import android.view.View;

import com.bingo.king.R;
import com.bingo.king.app.base.BaseAdapter;
import com.bingo.king.app.utils.GlideUtils;
import com.bingo.king.app.utils.StringFormatUtil;
import com.bingo.king.mvp.model.entity.douban.HotMovieBean;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 */

public class MovieLatestAdapter extends BaseAdapter<HotMovieBean.SubjectsBean,BaseViewHolder>
{
    public MovieLatestAdapter(List<HotMovieBean.SubjectsBean> data) {
        super(R.layout.item_movie_latest,data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HotMovieBean.SubjectsBean item) {
        helper.setText(R.id.tv_one_title,item.getTitle());
        helper.setText(R.id.tv_one_directors,  StringFormatUtil.formatLatestName(item.getDirectors()));
        helper.setText(R.id.tv_one_casts,  StringFormatUtil.formatLatestCastsName(item.getCasts()));
        helper.setText(R.id.tv_one_genres,"评分：" + item.getRating().getAverage());
        helper.setText(R.id.tv_collect_count,item.getCollect_count()+"人看过");
        GlideUtils.getInstance().loadMovieLatestImg( helper.getView(R.id.iv_one_photo),item.getImages().getLarge());
        helper.itemView.setOnClickListener(v -> onItemClickListener.onItemClickListener(item, helper.getView(R.id.iv_one_photo)));
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(HotMovieBean.SubjectsBean positionData, View view);}
}
