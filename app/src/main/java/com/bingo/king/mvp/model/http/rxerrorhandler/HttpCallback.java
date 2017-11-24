package com.bingo.king.mvp.model.http.rxerrorhandler;

import com.bingo.king.mvp.ui.widget.LoadingPage;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/3 16:45.
 */

public abstract class HttpCallback<T> implements Observer<T>
{

    private Stateful mStateful;

    public void setTarget(Stateful target)
    {
        this.mStateful = target;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d)
    {

    }

    @Override
    public void onNext(@NonNull T data)
    {
        // TODO: 2017/3/22 这边网络请求成功返回都不一样所以不能在这里统一写了（如果是自己公司需要规定一套返回方案）
        // TODO: 2017/3/22 这里先统一处理为成功   我们要是想检查返回结果的集合是否是空，只能去子类回掉中完成了。
        mStateful.setState(LoadingPage.STATE_SUCCESS);
        onSuccess(data);
    }

    @Override
    public void onError(@NonNull Throwable e)
    {
        e.printStackTrace();
        onFail();
    }

    @Override
    public void onComplete()
    {

    }

    /**
     * 如果喜欢统一处理成功回掉也是可以的。
     * 不过获取到的数据都是不规则的，理论上来说需要判断该数据是否为null或者list.size()是否为0
     * 只有不成立的情况下，才能调用成功方法refreshView/()。如果统一处理就放在每个refreshView中处理。
     */
    public abstract void onSuccess(T data);

    private void onFail()
    {
        if (!NetworkUtils.isAvailableByPing())
        {
//            mView.showMessage("你连接的网络有问题，请检查路由器");
            ToastUtils.showShort("网络异常，请检查网络连接");
            if (mStateful != null)
            {
                mStateful.setState(LoadingPage.STATE_ERROR);
            }
            return;
        }
//        mView.showMessage("程序员哥哥偷懒去了，快去举报他");
        ToastUtils.showShort("程序员哥哥偷懒去了，快去举报他");
        if (mStateful != null)
        {
            mStateful.setState(LoadingPage.STATE_EMPTY);
        }
    }

    public void detachView()
    {
        if (mStateful != null)
        {
            mStateful = null;
        }
    }


}
