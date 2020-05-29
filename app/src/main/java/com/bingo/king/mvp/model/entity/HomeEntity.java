package com.bingo.king.mvp.model.entity;

/**
 * Created by WangWB on 2020/5/29 15:03.
 * Email:634051075@qq.com
 */
public class HomeEntity {

    private String title;
    private Class<?> activity;

    public HomeEntity(String title, Class<?> activity) {
        this.title = title;
        this.activity = activity;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getActivity() {
        return activity;
    }

    public void setActivity(Class<?> activity) {
        this.activity = activity;
    }

}
