package com.saiyi.smartkeycabinetclient.key.presenter;

import android.content.Context;

import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.key.model.SelectKeyPositionModel;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyPosition;
import com.saiyi.smartkeycabinetclient.key.ui.SelectKeyPositionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * created by siwei on 2018/11/9
 */
public class SelectKeyPositionPresenter extends PresenterImpl<SelectKeyPositionActivity, SelectKeyPositionModel> {

    public SelectKeyPositionPresenter(Context context) {
        super(context);
    }

    @Override
    public SelectKeyPositionModel initModel(Context context) {
        return new SelectKeyPositionModel(context);
    }

    public void getKeyPositions() {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<KeyPosition> keyPositions = new ArrayList<>();
                for (int i = 0; i < 50; i++) {
                    KeyPosition keyPosition = new KeyPosition();
                    keyPosition.setPosition(String.valueOf(i+1));
                    int status = (i % 8 == 0 || i % 9 == 0) ? KeyPosition.StatusUseing :  KeyPosition.StatusNormal;
                    keyPosition.setStatus(status);
                    keyPositions.add(keyPosition);
                }
                getView().getKeyPositionsSuccess(keyPositions);
            }
        }, 1000);
    }
}
