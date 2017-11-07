package com.bingo.king.mvp.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 */

public abstract class DDPagerAdapter<T> extends PagerAdapter implements IAdapter<T>
{
    private List<T> mListModel = new ArrayList<>();
    private Activity mActivity;
    private SparseArray<View> mArrCacheView = new SparseArray<>();
    private boolean mAutoCacheView = false;
    private DDItemClickCallback<T> mItemClickCallback;

    public DDPagerAdapter(List<T> listModel, Activity activity){
        this.setData(listModel);
        this.mActivity = activity;
    }

    @Override
    public int getCount()
    {
        return this.getDataCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view ==object;
    }


    public void setItemClickCallback(DDItemClickCallback<T> itemClickCallback) {
        this.mItemClickCallback = itemClickCallback;
    }

    public void notifyItemClickCallback(int position, T item, View view) {
        if(this.mItemClickCallback != null) {
            this.mItemClickCallback.onItemClick(position, item, view);
        }

    }

    public void setAutoCacheView(boolean autoSaveView) {
        if(this.mAutoCacheView != autoSaveView) {
            this.mAutoCacheView = autoSaveView;
            if(!autoSaveView) {
                this.mArrCacheView.clear();
            }
        }

    }

    private void saveCacheViewIfNeed(int position, View view) {
        if(view != null && this.mAutoCacheView) {
            this.mArrCacheView.put(position, view);
        }
    }

    public View removeCacheView(int position) {
        View view = (View)this.mArrCacheView.get(position);
        if(view != null) {
            this.mArrCacheView.remove(position);
        }

        return view;
    }

    public void clearCacheView() {
        this.mArrCacheView.clear();
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View view = (View)this.mArrCacheView.get(position);
        if(view == null) {
            view = this.getView(container, position);
            this.saveCacheViewIfNeed(position, view);
        }

        container.addView(view);
        return view;
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    public abstract View getView(ViewGroup container, int position);

    @Override
    public Activity getActivity()
    {
        return this.mActivity;
    }

    @Override
    public View inflate(int resource, ViewGroup root)
    {
        return this.getActivity().getLayoutInflater().inflate(resource, root, false);
    }

    @Override
    public boolean isPositionLegal(int position)
    {
        return position >= 0 && position < this.mListModel.size();
    }

    @Override
    public T getData(int position)
    {
        return this.isPositionLegal(position)?this.mListModel.get(position):null;
    }

    @Override
    public int getDataCount()
    {
        return this.mListModel != null?this.mListModel.size():0;
    }

    @Override
    public int indexOf(T model)
    {
        return this.mListModel.indexOf(model);
    }

    @Override
    public void updateData(List<T> listModel)
    {
        this.setData(listModel);
        this.notifyDataSetChanged();
    }

    @Override
    public void setData(List<T> listModel)
    {
        if(listModel != null) {
            this.mListModel = listModel;
        } else {
            this.mListModel.clear();
        }
    }

    @Override
    public List<T> getData()
    {
        return this.mListModel;
    }

    @Override
    public void appendData(T model)
    {
        if(model != null) {
            this.mListModel.add(model);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void appendData(List<T> list)
    {
        if(list != null && !list.isEmpty()) {
            this.mListModel.addAll(list);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void removeData(T model)
    {
        if(model != null) {
            int position = this.mListModel.indexOf(model);
            this.removeData(position);
        }
    }

    @Override
    public T removeData(int position)
    {
        T model = null;
        if(this.isPositionLegal(position)) {
            model = this.mListModel.remove(position);
            this.notifyDataSetChanged();
        }
        return model;
    }

    @Override
    public void insertData(int position, T model)
    {
        if(model != null) {
            this.mListModel.add(position, model);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void insertData(int position, List<T> list)
    {
        if(list != null && !list.isEmpty()) {
            this.mListModel.addAll(position, list);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void updateData(int position, T model)
    {
        if(model != null && this.isPositionLegal(position)) {
            this.mListModel.set(position, model);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void updateData(int position)
    {
        if(this.isPositionLegal(position)) {
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void clearData()
    {
        this.updateData(null);
    }
}
