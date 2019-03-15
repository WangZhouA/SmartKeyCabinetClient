package com.saiyi.smartkeycabinetclient.spalish.ui;

import android.content.Context;
import android.os.Bundle;

import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.account.ui.LoginActivity;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPActivity;
import com.saiyi.smartkeycabinetclient.home.ui.HomeActivity;
import com.saiyi.smartkeycabinetclient.spalish.presenter.SpalishPresenter;

public class SpalishActivity extends BKMVPActivity<SpalishPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalish);
    }

    @Override
    public SpalishPresenter initPresenter(Context context) {
        return new SpalishPresenter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        getTitleBar().hidden();
        getPresenter().waitSpalish();
    }

    public void goLogin() {
        openActivity(LoginActivity.class);
        finish();
    }

    public void goHome() {
        openActivity(HomeActivity.class);
        finish();
    }
}
