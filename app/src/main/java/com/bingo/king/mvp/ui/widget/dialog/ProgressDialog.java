package com.bingo.king.mvp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bingo.king.R;


/**
 * <请描述这个类是干什么的>
 *
 * @data: 2017/5/10 17:27
 */
public class ProgressDialog extends Dialog {

    private ProgressWheel progress_wheel;
    private TextView tvContent;

    private Context mContext;

    public ProgressDialog(Context context) {
        super(context, R.style.ProgressDialogTheme);
        this.mContext = context;
        init();
    }

    private void init() {
        setContentView();

        //布局相关
        tvContent = findViewById(R.id.tvContent);
        progress_wheel = findViewById(R.id.progress_wheel);

        this.setCancelable(false);// 不可以用“返回键”取消
        this.setCanceledOnTouchOutside(false);
    }

    private void setContentView() {
        FrameLayout tempLayout = new FrameLayout(this.getContext());
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_progress, tempLayout, false);
        tempLayout.removeView(view);
        super.setContentView(view, view.getLayoutParams());
    }

    public ProgressDialog setText(String msg) {
        tvContent.setText(msg);
        return this;
    }


}
