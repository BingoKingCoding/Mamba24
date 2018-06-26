package com.bingo.king.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bingo.king.mvp.model.http.rxerrorhandler.StatefulCallback;

import timber.log.Timber;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/2:20:22.
 * <p>
 * Email:634051075@qq.com
 */

public abstract class BaseLazyFragment<P extends IPresenter> extends BaseFragment<P> implements StatefulCallback {


    private boolean isFragmentVisible = false;     // fragment是否显示了

    private boolean isFirstVisible = true; //只加载一次界面


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }


    @Override
    public void onStart() {
        super.onStart();
        Timber.d(TAG, "onStart");
        if (getUserVisibleHint()) {
            if (isFirstVisible) {
                onFragmentFirstVisible();
                isFirstVisible = false;
            }
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }


    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Timber.d(TAG, "setUserVisibleHint");
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }

    }


    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        Timber.d(TAG, "onFragmentVisibleChange");
    }


    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     */
    protected void onFragmentFirstVisible() {
        Timber.d(TAG, "onFragmentFirstVisible");
    }


    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d(TAG, "onDestroy");
        initVariable();
    }


}
