package com.saiyi.smartkeycabinetclient.device.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.lib.fast.common.http.BaseResponseListener;
import com.lib.fast.common.http.exception.ErrorStatus;
import com.lib.fast.common.mvp.PresenterImpl;
import com.lib.fast.common.utils.StringUtils;
import com.saiyi.smartkeycabinetclient.device.model.AddDeviceModel;
import com.saiyi.smartkeycabinetclient.device.ui.AddDeviceActivity;

/**
 * created by siwei on 2018/11/8
 */
public class AddDevicePresenter extends PresenterImpl<AddDeviceActivity, AddDeviceModel> {

    public AddDevicePresenter(Context context) {
        super(context);
    }

    @Override
    public AddDeviceModel initModel(Context context) {
        return new AddDeviceModel(context);
    }

    /**
     * 绑定设备
     */
    public boolean bindDevice(String deviceCode) {
        if (checkDeviceCode(deviceCode)) {
            getModel().bindDevice(deviceCode, new BaseResponseListener<Void>() {
                @Override
                public void onResponse(Void data) {
                    super.onResponse(data);
                    getView().showAddDeviceSuccess();
                }

                @Override
                public void onFaild(ErrorStatus e) {
                    super.onFaild(e);
                    getView().showAddDeviceFaild(e);
                }
            });
        } else {
            getView().toast("无效的设备编码");
        }
        return false;
    }

    //检查编码是否有效
    private boolean checkDeviceCode(String code) {
        return (!TextUtils.isEmpty(code) && StringUtils.matchLengthNum(code, 4));
    }
}
