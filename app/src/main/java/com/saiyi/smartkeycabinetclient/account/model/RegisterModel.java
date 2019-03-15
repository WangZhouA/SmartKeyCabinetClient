package com.saiyi.smartkeycabinetclient.account.model;

import android.content.Context;

import com.lib.fast.common.http.BaseHttpObserver;
import com.lib.fast.common.http.BaseResponse;
import com.lib.fast.common.http.ResponseListener;
import com.lib.fast.common.mvp.ModelImpl;
import com.saiyi.smartkeycabinetclient.core.http.error.ResponseError;
import com.saiyi.smartkeycabinetclient.core.http.service.HttpServiceApi;

/**
 * created by siwei on 2018/11/5
 */
public class RegisterModel extends ModelImpl {

    public RegisterModel(Context context) {
        super(context);
    }

    /**
     * 发送验证码
     */
    public void sendSmsCode(String phone, ResponseListener<Void> listener) {
        excuteRetorfitRequest(HttpServiceApi.instance().sendCode(phone), new BaseHttpObserver<Void>(getCompositeDisposable(), listener) {
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
     * 注册
     */
    public void register(String phone, String password, String repassword, String code, ResponseListener<Void> listener) {
        excuteRetorfitRequest(HttpServiceApi.instance().register(phone, password, repassword, code),
                new BaseHttpObserver<Void>(getCompositeDisposable(), listener) {
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