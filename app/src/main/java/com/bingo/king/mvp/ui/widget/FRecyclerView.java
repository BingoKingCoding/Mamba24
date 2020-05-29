package com.bingo.king.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * <继承RecyclerView进行拓展>
 * Created by WangWB on 2018/06/06  19:57.
 * Email:634051075@qq.com
 */
public class FRecyclerView extends RecyclerView {

    public FRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public FRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
        setItemAnimator(new DefaultItemAnimator());
        setLinearLayout(RecyclerView.VERTICAL);
    }


    //-----------------Linear------------------------------

    /**
     * @param orientation {@link RecyclerView#VERTICAL} 或者 {@link RecyclerView#HORIZONTAL}
     * @author wwb
     */
    public void setLinearLayout(int orientation) {
        if (orientation == RecyclerView.VERTICAL || orientation == RecyclerView.HORIZONTAL) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(orientation);
            setLayoutManager(linearLayoutManager);
        } else {
            throw new IllegalArgumentException("orientation must be RecyclerView.VERTICAL or RecyclerView.HORIZONTAL");
        }
    }


    /**
     * author wwb
     * Description 返回线性布局管理器
     */
    public LinearLayoutManager getLinearLayoutManager() {
        LayoutManager manager = getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            return (LinearLayoutManager) manager;
        }
        return null;
    }


    //------------grid-------------

    /**
     * author wwb
     * Description 设置网格布局管理器
     *
     * @param orientation {@link RecyclerView#VERTICAL} 或者 {@link RecyclerView#HORIZONTAL}
     */
    public void setGridLayoutManager(int orientation, int spanCount) {
        if (orientation == RecyclerView.VERTICAL || orientation == RecyclerView.HORIZONTAL) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
            gridLayoutManager.setOrientation(orientation);
            setLayoutManager(gridLayoutManager);
        } else {
            throw new IllegalArgumentException("orientation must be RecyclerView.VERTICAL or RecyclerView.HORIZONTAL");
        }
    }

    /**
     * author wwb
     * Description 返回网格布局管理器
     */
    public GridLayoutManager getGridLayoutManager() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return (GridLayoutManager) layoutManager;
        }
        return null;
    }


    /**
     * author wwb
     * Description 流式布局
     */
    public void setStaggeredGridLayout(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        setLayoutManager(staggeredGridLayoutManager);
    }


    //滑动到顶部
    public void scrollToTop() {
        scrollToPosition(0);
    }

    /**
     * 滚动到布局结束的位置
     */
    public void scrollToEnd() {
        final int count = getItemCount();
        if (count > 0) {
            scrollToPosition(count - 1);
        }
    }

    /**
     * 获得item的数量
     */
    public int getItemCount() {
        final Adapter adapter = getAdapter();
        if (adapter != null) {
            return adapter.getItemCount();
        }
        return 0;
    }


    //-------------divider start----------------

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};


    /**
     * 添加横分割线
     */
    public void addDividerHorizontal() {
        final TypedArray a = getContext().obtainStyledAttributes(ATTRS);
        Drawable drawable = a.getDrawable(0);
        a.recycle();
        addDividerHorizontal(drawable, 0);
    }

    /**
     * 添加横分割线
     *
     * @param drawable
     */
    public void addDividerHorizontal(Drawable drawable) {
        addDividerHorizontal(drawable, 0);
    }

    /**
     * 添加横分割线
     *
     * @param drawable
     * @param padding  分割线左右padding
     */
    public void addDividerHorizontal(Drawable drawable, int padding) {
        DividerItemDecorationExtend divider = new DividerItemDecorationExtend(DividerItemDecorationExtend.HORIZONTAL);
        divider.setDrawable(drawable);
        divider.setPadding(padding);
        addItemDecoration(divider);
    }


    public void addDividerHorizontalPaddingLeft(Drawable drawable, int padding) {
        DividerItemDecorationExtend divider = new DividerItemDecorationExtend(DividerItemDecorationExtend.HORIZONTAL);
        divider.setDrawable(drawable);
        divider.setPadding(padding);
        addItemDecoration(divider);
    }

    /**
     * 添加竖分割线
     */
    public void addDividerVertical() {
        final TypedArray a = getContext().obtainStyledAttributes(ATTRS);
        Drawable drawable = a.getDrawable(0);
        a.recycle();
        addDividerVertical(drawable, 0);
    }


    /**
     * 添加竖分割线
     *
     * @param drawable
     */
    public void addDividerVertical(Drawable drawable) {
        addDividerVertical(drawable, 0);
    }

    /**
     * 添加竖分割线
     *
     * @param drawable
     * @param padding  分割线上下padding
     */
    public void addDividerVertical(Drawable drawable, int padding) {
        DividerItemDecorationExtend divider = new DividerItemDecorationExtend(DividerItemDecorationExtend.VERTICAL);
        divider.setDrawable(drawable);
        divider.setPadding(padding);
        addItemDecoration(divider);
    }

    /**
     * 网格分割线
     */
    public void addDividerGrid(Drawable drawable) {
        addDividerHorizontal(drawable, 0);
        addDividerVertical(drawable, 0);
    }

    /**
     * 网格分割线
     */
    public void addDividerGrid(Drawable drawable, int padding) {
        addDividerHorizontal(drawable, padding);
        addDividerVertical(drawable, padding);
    }

    //----------divider end----------


}
