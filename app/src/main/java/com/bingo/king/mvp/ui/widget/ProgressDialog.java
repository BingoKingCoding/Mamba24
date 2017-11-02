package com.bingo.king.mvp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.bingo.king.R;


/**
 * <请描述这个类是干什么的>
 *
 * @data: 2017/5/10 17:27
 */
public class ProgressDialog extends Dialog
{

    boolean cancelAble;

    protected ProgressDialog(Context context) {
        super(context, R.style.ProgressDialogTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
        setCancelable(cancelAble);
    }

    public static ProgressDialog show(Context context) {
        return show(context, false);
    }

    public static ProgressDialog show(Context context, boolean cancelAble) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.cancelAble = cancelAble;
        dialog.show();
        return dialog;
    }
}
