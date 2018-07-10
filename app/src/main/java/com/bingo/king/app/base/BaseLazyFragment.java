package com.bingo.king.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bingo.king.mvp.model.http.rxerrorhandler.StatefulCallback;
import com.orhanobut.logger.Logger;

import timber.log.Timber;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/2:20:22.
 * <p>
 * Email:634051075@qq.com
 */

public abstract class BaseLazyFragment<P extends IPresenter> extends BaseFragment<P> implements StatefulCallback {

    /**
     * 是否初始化过布局
     */
    protected boolean isViewInitiated;
    /**
     * 当前界面是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareFetchData();
        }
    }

    public void prepareFetchData() {
        prepareFetchData(false);
    }

    /**
     * 判断懒加载条件
     *
     * @param forceUpdate 强制更新，好像没什么用？
     */
    public void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
        }
    }

    /**
     * 懒加载
     */
    public abstract void fetchData();

}
