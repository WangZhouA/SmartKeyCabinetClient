package com.saiyi.smartkeycabinetclient.user.model;

import android.content.Context;

import com.lib.fast.common.http.BaseHttpObserver;
import com.lib.fast.common.http.BaseResponse;
import com.lib.fast.common.http.ResponseListener;
import com.lib.fast.common.mvp.ModelImpl;
import com.saiyi.smartkeycabinetclient.core.http.error.ResponseError;
import com.saiyi.smartkeycabinetclient.core.http.service.HttpServiceApi;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;

import java.io.File;

/**
 * created by siwei on 2018/11/8
 */
public class UserInfoModel extends ModelImpl {

    public UserInfoModel(Context context) {
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

    /**
     * 更新用户的信息
     */
    public void updateUser(final User user, ResponseListener<Void> listener) {
        excuteRetorfitRequest(HttpServiceApi.instance().updateSomeUserInfo(user), new BaseHttpObserver<Void>(getCompositeDisposable(), listener) {
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

    /**
     * 上传头像
     */
    public void uploadAvator(File avaterFile, ResponseListener<String> listener) {
        User user = UserHelper.instance().getLoginUser();
        excuteRetorfitRequest(HttpServiceApi.instance().addHeadPicture(user.getUid(), avaterFile),
                new BaseHttpObserver<String>(getCompositeDisposable(), listener) {
                    @Override
                    public void onResponse(BaseResponse<String> response) {
                        if (response.isSuccess()) {
                            UserHelper.instance().updateAvator(response.getData());
                            dispatchListenerResponse(response.getData());
                        } else {
                            dispatchListenerFaild(ResponseError.handleError(response.getCode()));
                        }
                    }
                });
    }
}
