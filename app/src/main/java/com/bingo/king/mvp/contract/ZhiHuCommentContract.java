package com.bingo.king.mvp.contract;

import com.bingo.king.app.base.IModel;
import com.bingo.king.app.base.IView;
import com.bingo.king.mvp.model.entity.zhihu.CommentBean;

import java.util.List;

import io.reactivex.Observable;


public interface ZhiHuCommentContract
{
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView
    {
        void refreshView(List<CommentBean.CommentsBean> list);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel
    {
        Observable<CommentBean> requestLongCommentInfo(int id);
        Observable<CommentBean> requestShortCommentInfo(int id);
    }
}
