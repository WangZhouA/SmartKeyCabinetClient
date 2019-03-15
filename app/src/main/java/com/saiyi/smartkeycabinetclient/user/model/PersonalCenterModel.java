package com.saiyi.smartkeycabinetclient.user.model;

import android.content.Context;

import com.lib.fast.common.http.BaseHttpObserver;
import com.lib.fast.common.http.BaseResponse;
import com.lib.fast.common.http.ResponseListener;
import com.lib.fast.common.mvp.ModelImpl;
import com.saiyi.smartkeycabinetclient.core.http.error.ResponseError;
import com.saiyi.smartkeycabinetclient.core.http.service.HttpServiceApi;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;

/**
 * created by siwei on 2018/11/6
 */
public class PersonalCenterModel extends ModelImpl {

    public PersonalCenterModel(Context context) {
        super(context);
    }

    /**
     * 获取用户详细信息
     */
    public void getUserDetials(long uid, ResponseListener<User> listener) {
        excuteRetorfitRequest(HttpServiceApi.instance().getUserByUserId(uid), new BaseHttpObserver<User>(getCompositeDisposable(), listener) {
            @Override
            public void onResponse(BaseResponse<User> response) {
                if (response.isSuccess()) {
                    dispatchListenerResponse(response.getData());
                } else {
                    dispatchListenerFaild(ResponseError.handleError(response.getCode()));
                }
            }
        });
    }
}
