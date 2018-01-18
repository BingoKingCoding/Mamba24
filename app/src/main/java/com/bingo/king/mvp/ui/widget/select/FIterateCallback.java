package com.bingo.king.mvp.ui.widget.select;

import java.util.Iterator;

/**
 * <遍历回调>
 */

public interface FIterateCallback<T>
{
    /**
     * 返回true，结束遍历
     *
     * @param i
     * @param item
     * @param it
     * @return
     */
    boolean next(int i, T item, Iterator<T> it);
}
