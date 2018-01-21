package com.bingo.king.mvp.model.entity;

import com.bingo.king.mvp.ui.widget.select.FSelectManager;

import java.io.Serializable;

/**
 * <用户信息>
 */

public class UserBean implements FSelectManager.Selectable, Serializable
{
    private static final long serialVersionUID = 0;
    private boolean selected;

    private String user_id = ""; // 用户id
    private String nick_name; // 昵称
    private String signature; // 签名
    private int sex; // 0-未知，1-男，2-女
    private int login_type; //0：微信；1：QQ；2：手机；3：微博 ;4 : 游客登录
    private String city; // 所在城市
    private String province;//所在省份
    private String emotional_state;//情感状态
    private String birthday;//生日
    private String job;//职业
    private String head_image; // 头像

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getNick_name()
    {
        return nick_name;
    }

    public void setNick_name(String nick_name)
    {
        this.nick_name = nick_name;
    }

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public int getLogin_type()
    {
        return login_type;
    }

    public void setLogin_type(int login_type)
    {
        this.login_type = login_type;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getEmotional_state()
    {
        return emotional_state;
    }

    public void setEmotional_state(String emotional_state)
    {
        this.emotional_state = emotional_state;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getJob()
    {
        return job;
    }

    public void setJob(String job)
    {
        this.job = job;
    }

    public String getHead_image()
    {
        return head_image;
    }

    public void setHead_image(String head_image)
    {
        this.head_image = head_image;
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}
