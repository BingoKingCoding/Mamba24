package com.bingo.king.mvp.model.entity;

import com.fanwe.lib.cache.IObjectConverter;
import com.google.gson.Gson;

/**
 * <对象转换器>
 */

public class GsonObjectConverter implements IObjectConverter
{
    private static final Gson GSON = new Gson();

    @Override
    public String objectToString(Object o)
    {
        return GSON.toJson(o);//对象转json
    }

    @Override
    public <T> T stringToObject(String s, Class<T> clazz)
    {
        return GSON.fromJson(s,clazz);//json转对象
    }
}
