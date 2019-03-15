package com.saiyi.smartkeycabinetclient.user.presenter;

import android.content.Context;

import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.user.model.ApprovedModel;
import com.saiyi.smartkeycabinetclient.user.ui.ApprovedFragment;

/**
 * created by siwei on 2018/12/5
 */
public class ApprovedPresenter extends PresenterImpl<ApprovedFragment, ApprovedModel> {

    public ApprovedPresenter(Context context) {
        super(context);
    }

    @Override
    public ApprovedModel initModel(Context context) {
        return new ApprovedModel(context);
    }
}
