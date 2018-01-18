package com.bingo.king.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bingo.king.R;
import com.bingo.lib.select.config.FImageViewSelectConfig;
import com.bingo.lib.select.config.FTextViewSelectConfig;
import com.bingo.lib.select.view.FSelectView;

/**
 * <请描述这个类是干什么的>
 * Created by wwb on 2018/1/17 17:36.
 */

public class TabMainMenuView extends FSelectView
{
    public TabMainMenuView(Context context)
    {
        super(context);
        init();
    }

    public TabMainMenuView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public TabMainMenuView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ImageView iv_tab_image;
    private TextView tv_tab_name;

    private void init()
    {
        setContentView(R.layout.view_tab_main_menu);
        iv_tab_image = findViewById(R.id.iv_tab_image);
        tv_tab_name = findViewById(R.id.tv_tab_name);
    }

    public FImageViewSelectConfig configImage()
    {
        return configImage(iv_tab_image);
    }

    public FTextViewSelectConfig configTextView()
    {
        return configText(tv_tab_name);
    }

    public TextView getTabNameTextView()
    {
        return tv_tab_name;
    }
}
