package com.bingo.king.mvp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.bingo.king.R;


/**
 * <请描述这个类是干什么的>
 *
 * @data: 2017/5/10 17:27
 */
public class ProgressDialog extends Dialog
{
    private TextView tvContent;
    boolean cancelAble;

    public ProgressDialog(Context context)
    {
        super(context, R.style.ProgressDialogTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
        setCancelable(cancelAble);
        tvContent = findViewById(R.id.tvContent);
    }


    public ProgressDialog setTextMsg(String message)
    {
        tvContent.setText(message);
        return this;
    }
}
