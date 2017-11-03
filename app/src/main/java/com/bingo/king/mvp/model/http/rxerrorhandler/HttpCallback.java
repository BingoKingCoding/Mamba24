package com.bingo.king.mvp.model.http.rxerrorhandler;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * <请描述这个类是干什么的>
 * Created by wang on 2017/11/3 16:45.
 */

public class HttpCallback<T> implements Observer<T>
{

    @Override
    public void onSubscribe(@NonNull Disposable d)
    {

    }

    @Override
    public void onNext(@NonNull T t)
    {

    }

    @Override
    public void onError(@NonNull Throwable e)
    {

    }

    @Override
    public void onComplete()
    {

    }
}
