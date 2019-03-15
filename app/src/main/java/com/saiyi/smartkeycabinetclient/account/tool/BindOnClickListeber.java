package com.saiyi.smartkeycabinetclient.account.tool;

import android.view.View;

/**
 * created by siwei on 2018/11/5
 * 绑定2和View之间的点击关系;其中fromView被点击的同时,bindView也会触发点击
 */
public class BindOnClickListeber implements View.OnClickListener {

    private View fromView, bindView;

    public BindOnClickListeber(View fromView, View bindView) {
        this.fromView = fromView;
        this.bindView = bindView;
        bindClick();
    }

    private void bindClick() {
        if (fromView != null) {
            fromView.setClickable(true);
            fromView.setOnClickListener(mOnClickListener);
        }
        if (bindView != null) {
            bindView.setClickable(true);
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (bindView != null) {
                bindView.performClick();
            }
        }
    };


    @Override
    public void onClick(View v) {

    }
}
