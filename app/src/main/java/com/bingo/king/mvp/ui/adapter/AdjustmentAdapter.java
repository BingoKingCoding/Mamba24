package com.bingo.king.mvp.ui.adapter;

import com.bingo.king.R;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 */

public class AdjustmentAdapter extends BaseItemDraggableAdapter<String,BaseViewHolder>
{
    public AdjustmentAdapter(List<String> data)
    {
        super(R.layout.item_adjustment,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item)
    {
        helper.setText(R.id.tv_adjustment_name, item);
    }
}
