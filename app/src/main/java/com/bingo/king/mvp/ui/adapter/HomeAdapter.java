package com.bingo.king.mvp.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.bingo.king.R;
import com.bingo.king.app.Constants;
import com.bingo.king.mvp.model.entity.HomeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * <首页列表>
 * Created by WangWB on 2020/5/29 15:00.
 * Email:634051075@qq.com
 */
public class HomeAdapter extends BaseQuickAdapter<HomeEntity, BaseViewHolder> {


    public HomeAdapter(@Nullable List<HomeEntity> data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeEntity item) {
        helper.setText(R.id.tv_title, item.getTitle());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, item.getActivity());
                intent.putExtra(Constants.EXTRA_TITLE, item.getTitle());
                mContext.startActivity(intent);
            }
        });

    }


}
