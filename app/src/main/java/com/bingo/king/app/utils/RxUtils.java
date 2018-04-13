package com.bingo.king.app.utils;

import com.bingo.king.app.base.IView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.Observable;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/4:23:16.
 *
 */

public class RxUtils {
    public static <T> LifecycleTransformer<T> bindToLifecycle(IView view) {
        if (view instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity) view).bindToLifecycle();
        } else if (view instanceof RxFragment) {
            return ((RxFragment) view).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }
    }

    public static <T> ObservableTransformer<T, T> io2Main() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
