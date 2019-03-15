package com.saiyi.smartkeycabinetclient.user.ui;

import android.content.Context;
import android.os.Bundle;

import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPActivity;
import com.saiyi.smartkeycabinetclient.user.presenter.SearchApplicationPresenter;

public class SearchApplicationActivity extends BKMVPActivity<SearchApplicationPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_application);
    }

    @Override
    public SearchApplicationPresenter initPresenter(Context context) {
        return new SearchApplicationPresenter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        getTitleBar().hidden();
    }
}
