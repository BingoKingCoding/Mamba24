package com.bingo.king.app.imageloader;

import android.graphics.Bitmap;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/06/27  11:12.
 */

public interface BitmapCallBack {

    void onBitmapLoader(Bitmap bitmap);

    void onBitmapFailed(Exception e);

    public static class EmptyCallback implements BitmapCallBack {

        @Override
        public void onBitmapLoader(Bitmap bitmap) {

        }

        @Override
        public void onBitmapFailed(Exception e) {

        }
    }

}
