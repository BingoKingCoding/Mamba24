package com.bingo.king.app.utils;

import com.bingo.king.app.base.IView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * <请描述这个类是干什么的>
 * Created by adou on 2017/11/4:23:16.
 *
 * @Email:634051075@qq.com
 */

public class RxUtils
{
    public static <T> LifecycleTransformer<T> bindToLifecycle(IView view) {
            if (view instanceof RxAppCompatActivity) {
                return ((RxAppCompatActivity) view).bindToLifecycle();
            } else if (view instanceof RxFragment) {
                return ((RxFragment) view).bindToLifecycle();
            } else {
                throw new IllegalArgumentException("view isn't activity or fragment");
            }
        }
}
