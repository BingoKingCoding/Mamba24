package com.bingo.king.mvp.contract;

import com.bingo.king.app.base.IModel;
import com.bingo.king.app.base.IView;
import com.bingo.king.mvp.model.entity.zhihu.SectionChildListBean;
import com.bingo.king.mvp.model.entity.zhihu.ThemeChildListBean;

import io.reactivex.Observable;


public interface ZhihuThemeContract
{
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView
    {
        void refreshView(ThemeChildListBean data);
        void refreshSectionData(SectionChildListBean data);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel
    {
        Observable<ThemeChildListBean> requestThemeChildList(int id);
        Observable<SectionChildListBean> requestSectionChildList(int id);
    }
}
