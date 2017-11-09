package com.bingo.king.mvp.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.bingo.king.app.utils.GlideUtils;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 */

public class PhotoViewPageAdapter extends DDPagerAdapter<String>
{
    public PhotoViewPageAdapter(List<String> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public View getView(ViewGroup container, int position)
    {
        String url = getData(position);
        PhotoView photoView = new PhotoView(getActivity());
        GlideUtils.getInstance().loadImage(url,photoView);
        return photoView;
    }
}
