package com.bingo.king.mvp.model;

import com.bingo.king.mvp.model.entity.TestBean;

import io.reactivex.Observable;

/**
 * <获取数据的接口>
 * Created by adou on 2017/11/2:22:25.
 */
public interface IRepository
{
    Observable<TestBean> requestPraiseMovie();
}
