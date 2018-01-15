package com.bingo.king.mvp.ui.widget;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.bingo.king.R;
import com.bingo.king.app.utils.ResourcesUtil;

/**
 * 可以只传入一张背景图就有点击效果
 * 通过setBackgroundDrawable方法设置背景,具体可以参考http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0924/1712.html
 */
public class PressedStateListDrawable extends StateListDrawable
{
    public static PressedStateListDrawable get(@DrawableRes int normal){
        PressedStateListDrawable stateListDrawable = new PressedStateListDrawable(normal, R.color.state_press_cover);
        return stateListDrawable;
    }

    private int mSelectionColor;

    public PressedStateListDrawable(@DrawableRes int res, @ColorRes int selectionColor) {
        super();
        this.mSelectionColor = ResourcesUtil.getColor(selectionColor);
        Drawable drawable = ResourcesUtil.getDrawable(res);
        addState(new int[]{android.R.attr.state_pressed}, drawable);
        addState(new int[]{}, drawable);
    }

    @Override
    protected boolean onStateChange(int[] states) {
        boolean isStatePressedInArray = false;
        for (int state : states) {
            if (state == android.R.attr.state_pressed) {
                isStatePressedInArray = true;
            }
        }
        if (isStatePressedInArray) {
            super.setColorFilter(mSelectionColor, PorterDuff.Mode.SRC_ATOP);
        } else {
            super.clearColorFilter();
        }
        return super.onStateChange(states);
    }

    @Override
    public boolean isStateful() {
        return true;
    }
}
