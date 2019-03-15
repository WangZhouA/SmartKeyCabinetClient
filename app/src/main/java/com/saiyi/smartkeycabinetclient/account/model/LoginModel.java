package com.saiyi.smartkeycabinetclient.account.model;

import android.content.Context;
import android.text.TextUtils;

import com.lib.fast.common.error.ErrorEngine;
import com.lib.fast.common.http.BaseHttpObserver;
import com.lib.fast.common.http.BaseResponse;
import com.lib.fast.common.http.ResponseListener;
import com.lib.fast.common.mvp.ModelImpl;
import com.saiyi.smartkeycabinetclient.core.error.AppError;
import com.saiyi.smartkeycabinetclient.core.http.error.ResponseError;
import com.saiyi.smartkeycabinetclient.core.http.service.HttpServiceApi;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;

/**
 * created by siwei on 2018/11/5
 */
public class LoginModel extends ModelImpl {

    public LoginModel(Context context) {
        super(context);
    }

    /**
     * 登陆
     */
    public void login(String phone, String pwd, ResponseListener<User> listener) {
        excuteRetorfitRequest(HttpServiceApi.instance().login(phone, pwd), new BaseHttpObserver<User>(getCompositeDisposable(), listener) {
            @Override
            public void onResponse(BaseResponse<User> response) {
                if (response.isSuccess()) {
                    UserHelper.instance().userLogin(response.getData());
                    dispatchListenerResponse(response.getData());
                } else {
                    dispatchListenerFaild(ResponseError.handleError(response.getCode()));
                }
            }
        });
    }

    /**
     * 检查用户是否已填写信息
     */
    public void checkUserFillInformation(long uid, ResponseListener<User> listener) {
        excuteRetorfitRequest(HttpServiceApi.instance().getUserByUserId(uid), new BaseHttpObserver<User>(getCompositeDisposable(), listener) {
            @Override
            public void onResponse(BaseResponse<User> response) {
                if (response.isSuccess()) {
                    User user = response.getData();
                    if (!checkFullInformation(user)) {
                        //用户信息未完善
                        dispatchListenerFaild(ErrorEngine.handleCustomError(AppError.NotFullUserInfomation));
                    } else {
                        //用户信息已完善
                        UserHelper.instance().fullUserInformation(user.getName(), user.getGender(), user.getJob(), user.getJobno());
                        dispatchListenerResponse(response.getData());
                    }
                } else {
                    dispatchListenerFaild(ResponseError.handleError(response.getCode()));
                }
            }
        });
    }

    /**
     * 检查用户信息是否已完善
     */
    private boolean checkFullInformation(User user) {
        boolean bool = false;
        if (user != null) {
            bool = !TextUtils.isEmpty(user.getJobno())
                    && !TextUtils.isEmpty(user.getJob())
                    && !TextUtils.isEmpty(user.getName());
        }
        return bool;
    }
}
