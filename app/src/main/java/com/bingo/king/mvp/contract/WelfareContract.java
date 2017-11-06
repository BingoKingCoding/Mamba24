package com.bingo.king.mvp.contract;

import android.os.Message;

import com.bingo.king.app.base.IModel;
import com.bingo.king.app.base.IView;
import com.bingo.king.mvp.model.entity.DaoGankEntity;
import com.bingo.king.mvp.model.entity.GankEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2017/9/22 16:50.
 */

public interface WelfareContract
{
    interface View extends IView
    {
        void setNewData(List<GankEntity.ResultsBean> mData);
        void setAddData(List<GankEntity.ResultsBean> results);
    }

    interface Model extends IModel
    {
        Observable<GankEntity> getRandomGirl();
        Message addGankEntity(DaoGankEntity daoGankEntity);
    }

}
