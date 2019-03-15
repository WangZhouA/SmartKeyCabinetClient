package com.saiyi.smartkeycabinetclient.user.presenter;

import android.content.Context;

import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.user.model.PendingModel;
import com.saiyi.smartkeycabinetclient.user.ui.PendingFragment;

/**
 * created by siwei on 2018/12/5
 */
public class PendingPresenter extends PresenterImpl<PendingFragment, PendingModel> {

    public PendingPresenter(Context context) {
        super(context);
    }

    @Override
    public PendingModel initModel(Context context) {
        return new PendingModel(context);
    }
}
