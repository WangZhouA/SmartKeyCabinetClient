package com.saiyi.smartkeycabinetclient.key.presenter;

import android.content.Context;

import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.key.model.ApplicationKeyModel;
import com.saiyi.smartkeycabinetclient.key.ui.ApplicationKeyFragment;

/**
 * created by siwei on 2018/11/6
 */
public class ApplicationKeyPresenter extends PresenterImpl<ApplicationKeyFragment, ApplicationKeyModel> {

    public ApplicationKeyPresenter(Context context) {
        super(context);
    }

    @Override
    public ApplicationKeyModel initModel(Context context) {
        return new ApplicationKeyModel(context);
    }
}
