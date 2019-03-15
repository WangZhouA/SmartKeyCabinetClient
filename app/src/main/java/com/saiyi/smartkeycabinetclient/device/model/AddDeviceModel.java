package com.saiyi.smartkeycabinetclient.device.model;

import android.content.Context;

import com.lib.fast.common.http.BaseHttpObserver;
import com.lib.fast.common.http.BaseResponse;
import com.lib.fast.common.http.ResponseListener;
import com.lib.fast.common.mvp.ModelImpl;
import com.saiyi.smartkeycabinetclient.core.http.error.ResponseError;
import com.saiyi.smartkeycabinetclient.core.http.service.HttpServiceApi;
import com.saiyi.smartkeycabinetclient.core.model.Action;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;

import org.greenrobot.eventbus.EventBus;

/**
 * created by siwei on 2018/11/8
 */
public class AddDeviceModel extends ModelImpl {

    public AddDeviceModel(Context context) {
        super(context);
    }

    public void bindDevice(String deviceCode, ResponseListener<Void> listener) {
        User user = UserHelper.instance().getLoginUser();
        excuteRetorfitRequest(HttpServiceApi.instance().addDeviceinfo(deviceCode, user.getUid()), new BaseHttpObserver<Void>(getCompositeDisposable(), listener) {
            @Override
            public void onResponse(BaseResponse<Void> response) {
                if (response.isSuccess()) {
                    EventBus.getDefault().post(Action.AddNewDevice);
                    dispatchListenerResponse(response.getData());
                } else {
                    dispatchListenerFaild(ResponseError.handleError(response.getCode()));
                }
            }
        });
    }
}
