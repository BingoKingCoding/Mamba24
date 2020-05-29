package com.bingo.king.mvp.ui.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * <基于DividerItemDecoration修改>
 * Created by WangWB on 2018/06/15  10:05.
 * Email:634051075@qq.com
 */
public class DividerItemDecorationExtend extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    //分割线
    private Drawable mDivider;
    //分割线方向
    private int mOrientation;

    private final Rect mBounds = new Rect();

    //分割线padding
    private int mPadding;
    //是否左边设置padding
    private boolean mIsPaddingLeft;

    /**
     * @param orientation 分割线的方向
     */
    public DividerItemDecorationExtend(int orientation) {
        mOrientation = orientation;
    }

    /**
     * 设置分割线drawable
     */
    public void setDrawable(Drawable drawable) {
        mDivider = drawable;
    }

    /**
     * 设置分割线padding
     */
    public void setPadding(int padding) {
        mPadding = padding;
    }

    public void setPaddingLeft(int padding, boolean isPaddingLeft) {
        mPadding = padding;
        mIsPaddingLeft = isPaddingLeft;
    }


    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        //画横分割线
        if (mOrientation == VERTICAL) {
            drawRight(c, parent);
        } else if (mOrientation == HORIZONTAL) {
            //画纵向分割线
            drawBottom(c, parent);
        }
    }

    /**
     * item底部画分割线
     */
    private void drawBottom(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left = 0;
        final int right = parent.getWidth();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (!isLastRow(parent, parent.getChildAdapterPosition(child))) {
                parent.getDecoratedBoundsWithMargins(child, mBounds);
                final int bottom = mBounds.bottom + Math.round(child.getTranslationX());
                final int top = bottom - mDivider.getIntrinsicHeight();
                if (mIsPaddingLeft) {
                    mDivider.setBounds(left + mPadding, top, right, bottom);
                } else {
                    mDivider.setBounds(left + mPadding, top, right - mPadding, bottom);
                }
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }


    /**
     * item右边画分割线
     */
    private void drawRight(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top = 0;
        final int bottom = parent.getHeight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (!isLastColumn(parent, parent.getChildAdapterPosition(child))) {
                parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
                final int right = mBounds.right + Math.round(child.getTranslationX());
                final int left = right - mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top + mPadding, right, bottom - mPadding);
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (mOrientation == VERTICAL) {
            if (isLastColumn(parent, position)) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        } else if (mOrientation == HORIZONTAL) {
            if (isLastRow(parent, position)) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }
        }
    }


    /**
     * position是否是最后一列
     *
     * @param parent
     * @param position
     * @return
     */
    private boolean isLastColumn(RecyclerView parent, int position) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            int childCount = parent.getAdapter().getItemCount();
            int spanCount = manager.getSpanCount();
            int orientation = manager.getOrientation();

            if (orientation == VERTICAL) {
                return (position + 1) % spanCount == 0;
            } else {
                int remainder = childCount % spanCount;
                if (remainder == 0) {
                    return position >= childCount - spanCount;
                } else {
                    return position >= childCount - remainder;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            int childCount = parent.getAdapter().getItemCount();
            int orientation = manager.getOrientation();
            return orientation == VERTICAL || position == childCount - 1;
        }
        return false;
    }

    /**
     * position是否是最后一行
     *
     * @param parent
     * @param position
     * @return
     */
    private boolean isLastRow(RecyclerView parent, int position) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            int childCount = parent.getAdapter().getItemCount();
            int spanCount = manager.getSpanCount();
            int orientation = manager.getOrientation();

            if (orientation == VERTICAL) {
                int remainder = childCount % spanCount;
                if (remainder == 0) {
                    return position >= childCount - spanCount;
                } else {
                    return position >= childCount - remainder;
                }
            } else {
                return (position + 1) % spanCount == 0;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            int childCount = parent.getAdapter().getItemCount();
            int orientation = manager.getOrientation();
            return orientation != VERTICAL || position == childCount - 1;
        }
        return false;
    }


}
