package com.bingo.king.mvp.ui.adapter;

import android.view.View;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/30 09:03.
 */

public interface DDItemClickCallback<T>
{
    void onItemClick(int position, T item, View view);
}
