package com.saiyi.smartkeycabinetclient.core.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lib.fast.common.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * created by siwei on 2018/11/5
 */
public abstract class BKActivity extends BaseActivity {

    @Override
    protected void initView() {
        //修复ButterKnife框架在分model下开发无法注入的bug
        //https://github.com/JakeWharton/butterknife/issues/1127
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
