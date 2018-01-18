package com.bingo.king.mvp.model.entity;

/**
 * <初始化接口获取的实体类>
 */

public class InitActBean
{
    // APP Key
    private String sina_app_key;
    private String sina_app_secret;
    private String sina_bind_url;
    private String qq_app_key;
    private String qq_app_secret;
    private String wx_app_key;
    private String wx_app_secret;

    private int has_sdk_login;
    private int has_sina_login;//是否显示新浪登录
    private int has_wx_login;//是否显示微信登录
    private int has_qq_login;//是否显示QQ登录
    private int has_mobile_login;//是否显示手机登录
    private int has_visitors_login;//是否显示游客登录

    public String getSina_app_key()
    {
        return sina_app_key;
    }

    public void setSina_app_key(String sina_app_key)
    {
        this.sina_app_key = sina_app_key;
    }

    public String getSina_app_secret()
    {
        return sina_app_secret;
    }

    public void setSina_app_secret(String sina_app_secret)
    {
        this.sina_app_secret = sina_app_secret;
    }

    public String getSina_bind_url()
    {
        return sina_bind_url;
    }

    public void setSina_bind_url(String sina_bind_url)
    {
        this.sina_bind_url = sina_bind_url;
    }

    public String getQq_app_key()
    {
        return qq_app_key;
    }

    public void setQq_app_key(String qq_app_key)
    {
        this.qq_app_key = qq_app_key;
    }

    public String getQq_app_secret()
    {
        return qq_app_secret;
    }

    public void setQq_app_secret(String qq_app_secret)
    {
        this.qq_app_secret = qq_app_secret;
    }

    public String getWx_app_key()
    {
        return wx_app_key;
    }

    public void setWx_app_key(String wx_app_key)
    {
        this.wx_app_key = wx_app_key;
    }

    public String getWx_app_secret()
    {
        return wx_app_secret;
    }

    public void setWx_app_secret(String wx_app_secret)
    {
        this.wx_app_secret = wx_app_secret;
    }

    public int getHas_sdk_login()
    {
        return has_sdk_login;
    }

    public void setHas_sdk_login(int has_sdk_login)
    {
        this.has_sdk_login = has_sdk_login;
    }

    public int getHas_sina_login()
    {
        return has_sina_login;
    }

    public void setHas_sina_login(int has_sina_login)
    {
        this.has_sina_login = has_sina_login;
    }

    public int getHas_wx_login()
    {
        return has_wx_login;
    }

    public void setHas_wx_login(int has_wx_login)
    {
        this.has_wx_login = has_wx_login;
    }

    public int getHas_qq_login()
    {
        return has_qq_login;
    }

    public void setHas_qq_login(int has_qq_login)
    {
        this.has_qq_login = has_qq_login;
    }

    public int getHas_mobile_login()
    {
        return has_mobile_login;
    }

    public void setHas_mobile_login(int has_mobile_login)
    {
        this.has_mobile_login = has_mobile_login;
    }

    public int getHas_visitors_login()
    {
        return has_visitors_login;
    }

    public void setHas_visitors_login(int has_visitors_login)
    {
        this.has_visitors_login = has_visitors_login;
    }
}
