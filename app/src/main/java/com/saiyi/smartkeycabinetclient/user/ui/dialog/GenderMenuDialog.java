package com.saiyi.smartkeycabinetclient.user.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lib.fast.common.dialog.MenuDialog;
import com.saiyi.smartkeycabinetclient.R;


/**
 * 性别菜单弹窗
 */
public class GenderMenuDialog extends MenuDialog implements View.OnClickListener {

    /**
     * 点击取消
     */
    public static final int WHICH_CANCLE = 1;
    /**
     * 男
     */
    public static final int WHICH_MAN = 2;
    /**
     * 女
     */
    public static final int WHICH_FEMAN = 3;

    TextView manTv;

    TextView femanTv;

    TextView cancleTv;

    public GenderMenuDialog(@NonNull Context context) {
        super(context);
    }

    public GenderMenuDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected GenderMenuDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void initDialog() {
        super.initDialog();
        setContentView(R.layout.layout_dialog_gender_menu);
    }


    @Override
    protected void initView(View view) {
        manTv = view.findViewById(R.id.man_tv);
        femanTv = view.findViewById(R.id.feman_tv);
        cancleTv = view.findViewById(R.id.cancle_tv);
        manTv.setOnClickListener(this);
        femanTv.setOnClickListener(this);
        cancleTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        dismiss();
        int i = v.getId();
        if (i == R.id.cancle_tv) {
            onWhichOneClick(WHICH_CANCLE);
        } else if (i == R.id.man_tv) {
            onWhichOneClick(WHICH_MAN);
        } else if (i == R.id.feman_tv) {
            onWhichOneClick(WHICH_FEMAN);
        }
    }

}
