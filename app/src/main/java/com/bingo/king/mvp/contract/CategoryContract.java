package com.bingo.king.mvp.contract;


import com.bingo.king.app.base.IModel;
import com.bingo.king.app.base.IView;
import com.bingo.king.mvp.model.entity.GankEntity;
import com.bingo.king.mvp.ui.adapter.CategoryAdapter;

import io.reactivex.Observable;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/20 17:55.
 */

public interface CategoryContract
{
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView
    {
        void setAdapter(CategoryAdapter mAdapter);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel
    {
        Observable<GankEntity> gank(String type, String page);
    }
}
