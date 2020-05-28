package com.bingo.king.mvp.ui.adapter;

import android.view.View;

import com.bingo.king.R;
import com.bingo.king.app.utils.GlideUtils;
import com.bingo.king.mvp.model.entity.zhihu.ZhiHuListBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import static com.bingo.king.R.id.iv_three_one_three;

/*
 * Created by wwb on 2017/12/4 12:58.
 */

public class ZhiHuAdapter extends BaseMultiItemQuickAdapter<ZhiHuListBean, BaseViewHolder>
{
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ZhiHuAdapter(List<ZhiHuListBean> data)
    {
        super(data);
        addItemType(ZhiHuListBean.TITLE, R.layout.item_zhihu_title);
        addItemType(ZhiHuListBean.DAILY, R.layout.item_zhihu_daily);
        addItemType(ZhiHuListBean.HOT, R.layout.item_zhihu_hot);
        addItemType(ZhiHuListBean.THEME, R.layout.item_zhihu_theme);
        addItemType(ZhiHuListBean.SECTION, R.layout.item_zhihu_section);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhiHuListBean item)
    {
        switch (helper.getItemViewType())
        {
            case ZhiHuListBean.TITLE:
                helper.setText(R.id.tv_title, item.getTitle());
                break;
            case ZhiHuListBean.DAILY:
                helper.setText(R.id.tv_daily_dec, item.getDailyList().getTitle());
                GlideUtils.getInstance().loadImage(2, item.getDailyList().getImages().get(0), helper.getView(R.id.iv_daily));
                helper.getView(R.id.ll_section).setOnClickListener(v -> onItemClickListener.onItemClickListener(item.getDailyList().getId(), helper.getView(R.id.iv_daily)));
                break;
            case ZhiHuListBean.HOT:
                helper.setText(R.id.tv_three_one_one_title, item.getHotList().get(0).getTitle());
                helper.setText(R.id.tv_three_one_two_title, item.getHotList().get(1).getTitle());
                helper.setText(R.id.tv_three_one_three_title, item.getHotList().get(2).getTitle());
                GlideUtils.getInstance().loadImage(3, item.getHotList().get(0).getThumbnail(), helper.getView(R.id.iv_three_one_one));
                GlideUtils.getInstance().loadImage(3, item.getHotList().get(1).getThumbnail(), helper.getView(R.id.iv_three_one_two));
                GlideUtils.getInstance().loadImage(3, item.getHotList().get(2).getThumbnail(), helper.getView(iv_three_one_three));
                onItemClick(helper, R.id.iv_three_one_one, item.getHotList().get(0).getNews_id());
                onItemClick(helper, R.id.iv_three_one_two, item.getHotList().get(1).getNews_id());
                onItemClick(helper, iv_three_one_three, item.getHotList().get(2).getNews_id());
                break;
            case ZhiHuListBean.THEME:
                if (item.getThemeList().size()>0) {
                    helper.setText(R.id.tv_two_one_one_title, item.getThemeList().get(0).getDescription());
                    helper.setText(R.id.tv_two_one_two_title, item.getThemeList().get(1).getDescription());
                    GlideUtils.getInstance().loadImage(3, item.getThemeList().get(0).getThumbnail(), helper.getView(R.id.iv_two_one_one));
                    GlideUtils.getInstance().loadImage(3, item.getThemeList().get(1).getThumbnail(), helper.getView(R.id.iv_two_one_two));
                    OnItemThemeClick(helper, R.id.iv_two_one_one, item.getThemeList().get(0).getId());
                    OnItemThemeClick(helper, R.id.iv_two_one_two, item.getThemeList().get(1).getId());
                }
                break;
            case ZhiHuListBean.SECTION:
                helper.setText(R.id.tv_one_photo_title, item.getSectionList().get(0).getName());
                helper.setText(R.id.tv_two_one_one_title, item.getSectionList().get(1).getName());
                helper.setText(R.id.tv_two_one_two_title, item.getSectionList().get(2).getName());
                GlideUtils.getInstance().loadImage(3, item.getSectionList().get(0).getThumbnail(), helper.getView(R.id.iv_one_photo));
                GlideUtils.getInstance().loadImage(3, item.getSectionList().get(1).getThumbnail(), helper.getView(R.id.iv_two_one_one));
                GlideUtils.getInstance().loadImage(3, item.getSectionList().get(2).getThumbnail(), helper.getView(R.id.iv_two_one_two));
                OnItemSectionClick(helper, R.id.iv_one_photo, item.getSectionList().get(0).getId());
                OnItemSectionClick(helper, R.id.iv_two_one_one, item.getSectionList().get(1).getId());
                OnItemSectionClick(helper, R.id.iv_two_one_two, item.getSectionList().get(2).getId());
                break;
        }
    }

    /**
     * 抽取成一个方法不然每一个都要重新写setOnClickListener(new View.OnClickListener()
     *
     */
    public void onItemClick(BaseViewHolder helper, int id, final int urlId)
    {
        helper.getView(id).setOnClickListener(v -> onItemClickListener.onItemClickListener(urlId, v));
    }

    public void OnItemThemeClick(BaseViewHolder helper, int id, final int urlId)
    {
        helper.getView(id).setOnClickListener(v -> onItemClickListener.OnItemThemeClickListener(urlId, v));
    }

    public void OnItemSectionClick(BaseViewHolder helper, int id, final int urlId)
    {
        helper.getView(id).setOnClickListener(v -> onItemClickListener.OnItemSectionClickListener(urlId, v));
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void onItemClickListener(int id, View view);

        void OnItemThemeClickListener(int id, View view);

        void OnItemSectionClickListener(int id, View view);
    }
}
