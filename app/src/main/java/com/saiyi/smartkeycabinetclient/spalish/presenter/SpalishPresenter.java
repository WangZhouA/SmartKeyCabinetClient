package com.saiyi.smartkeycabinetclient.spalish.presenter;

import android.content.Context;

import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.spalish.model.SpalishModel;
import com.saiyi.smartkeycabinetclient.spalish.ui.SpalishActivity;

/**
 * created by siwei on 2018/11/5
 */
public class SpalishPresenter extends PresenterImpl<SpalishActivity, SpalishModel> {

    private static final long WAIT_SPALISH_TIME = 3 * 1000;

    public SpalishPresenter(Context context) {
        super(context);
    }

    @Override
    public SpalishModel initModel(Context context) {
        return new SpalishModel(context);
    }

    public void waitSpalish(){
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //getView().goHome();
                getView().goLogin();
            }
        }, WAIT_SPALISH_TIME);
    }
}
