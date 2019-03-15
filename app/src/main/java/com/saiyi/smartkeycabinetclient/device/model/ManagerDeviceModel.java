package com.saiyi.smartkeycabinetclient.device.model;

import android.content.Context;

import com.lib.fast.common.http.BaseHttpObserver;
import com.lib.fast.common.http.BaseResponse;
import com.lib.fast.common.http.ResponseListener;
import com.lib.fast.common.mvp.ModelImpl;
import com.saiyi.smartkeycabinetclient.core.http.error.ResponseError;
import com.saiyi.smartkeycabinetclient.core.http.service.HttpServiceApi;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;

import java.util.List;

/**
 * created by siwei on 2018/11/6
 */
public class ManagerDeviceModel extends ModelImpl {

    public ManagerDeviceModel(Context context) {
        super(context);
    }

    public void getDeviceList(ResponseListener<List<KeyCabinetDevice>> listener) {
        User user = UserHelper.instance().getLoginUser();
        excuteRetorfitRequest(HttpServiceApi.instance().getDeviceList(user.getUid()), new BaseHttpObserver<List<KeyCabinetDevice>>(getCompositeDisposable(), listener) {
            @Override
            public void onResponse(BaseResponse<List<KeyCabinetDevice>> response) {
                if (response.isSuccess()) {
                    dispatchListenerResponse(response.getData());
                } else {
                    dispatchListenerFaild(ResponseError.handleError(response.getCode()));
                }
            }
        });
    }

    /**
     * 删除设备
     */
    public void deleteDevice(long did, ResponseListener<Void> listener) {
        excuteRetorfitRequest(HttpServiceApi.instance().delectDeviceinfo(did), new BaseHttpObserver<Void>(getCompositeDisposable(), listener) {
            @Override
            public void onResponse(BaseResponse<Void> response) {
                if (response.isSuccess()) {
                    dispatchListenerResponse(response.getData());
                } else {
                    dispatchListenerFaild(ResponseError.handleError(response.getCode()));
                }
            }
        });
    }

    /**
     * 更新设备名称
     */
    public void updateDeviceName(long did, String name, ResponseListener<Void> listener) {
        excuteRetorfitRequest(HttpServiceApi.instance().updateDeviceinfoName(did, name), new BaseHttpObserver<Void>(getCompositeDisposable(), listener) {
            @Override
            public void onResponse(BaseResponse<Void> response) {
                if (response.isSuccess()) {
                    dispatchListenerResponse(response.getData());
                } else {
                    dispatchListenerFaild(ResponseError.handleError(response.getCode()));
                }
            }
        });
    }
}
