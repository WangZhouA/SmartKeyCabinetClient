package com.saiyi.smartkeycabinetclient.account.model;

import android.content.Context;

import com.lib.fast.common.http.BaseHttpObserver;
import com.lib.fast.common.http.BaseResponse;
import com.lib.fast.common.http.ResponseListener;
import com.lib.fast.common.mvp.ModelImpl;
import com.saiyi.smartkeycabinetclient.core.http.error.ResponseError;
import com.saiyi.smartkeycabinetclient.core.http.service.HttpServiceApi;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;

/**
 * created by siwei on 2018/11/5
 */
public class PerfectInfomationModel extends ModelImpl {

    public PerfectInfomationModel(Context context) {
        super(context);
    }

    /**
     * 填写用户信息
     */
    public void fullUserInformation(final User user, ResponseListener<Void> listener) {
        excuteRetorfitRequest(HttpServiceApi.instance().updateUserByUserId(user), new BaseHttpObserver<Void>(getCompositeDisposable(), listener) {
            @Override
            public void onResponse(BaseResponse<Void> response) {
                if (response.isSuccess()) {
                    UserHelper.instance().fullUserInformation(user.getName(), user.getGender(), user.getJob(), user.getJobno());
                    dispatchListenerResponse(response.getData());
                } else {
                    dispatchListenerFaild(ResponseError.handleError(response.getCode()));
                }
            }
        });
    }
}
