package com.saiyi.smartkeycabinetclient.user.presenter;

import android.content.Context;

import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.user.model.SearchApplicationModel;
import com.saiyi.smartkeycabinetclient.user.ui.SearchApplicationActivity;

/**
 * created by siwei on 2018/12/5
 */
public class SearchApplicationPresenter extends PresenterImpl<SearchApplicationActivity, SearchApplicationModel> {

    public SearchApplicationPresenter(Context context) {
        super(context);
    }

    @Override
    public SearchApplicationModel initModel(Context context) {
        return new SearchApplicationModel(context);
    }
}
