package com.saiyi.smartkeycabinetclient.user.presenter;

import android.content.Context;

import com.lib.fast.common.http.BaseResponseListener;
import com.lib.fast.common.http.exception.ErrorStatus;
import com.lib.fast.common.mvp.PresenterImpl;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.user.model.UserInfoModel;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;
import com.saiyi.smartkeycabinetclient.user.ui.UserInfoActivity;

import java.io.File;

/**
 * created by siwei on 2018/11/8
 */
public class UserInfoPresenter extends PresenterImpl<UserInfoActivity, UserInfoModel> {

    public UserInfoPresenter(Context context) {
        super(context);
    }

    @Override
    public UserInfoModel initModel(Context context) {
        return new UserInfoModel(context);
    }

    /**
     * 获取用户详细信息
     */
    public void getUserDetials() {
        User user = UserHelper.instance().getLoginUser();
        getModel().getUserDetials(user.getUid(), new BaseResponseListener<User>() {
            @Override
            public void onResponse(User data) {
                super.onResponse(data);
                getView().onGetUserDetialsSuccess(data);
            }

            @Override
            public void onFaild(ErrorStatus e) {
                super.onFaild(e);
                //获取失败,则从缓存中获取
                getView().onGetUserDetialsSuccess(UserHelper.instance().getLoginUser());
            }
        });
    }

    /**
     * 更新用户信息
     */
    public boolean updateUser(final User newUser, final User oldUser) {
        if (newUser != null && oldUser != null) {
            getModel().updateUser(newUser, new BaseResponseListener<Void>() {
                @Override
                public void onResponse(Void data) {
                    super.onResponse(data);
                    getView().onUpdateUserComplate(newUser);
                }

                @Override
                public void onFaild(ErrorStatus e) {
                    super.onFaild(e);
                    //修改失败,回滚结果
                    getView().toastErrorMsg(e);
                    getView().onUpdateUserComplate(oldUser);
                }
            });
            return true;
        } else {
            getView().toast("参数错误");
            return false;
        }

    }

    /**
     * 上传头像
     */
    public void uploadAvater(File avaterFile) {
        getModel().uploadAvator(avaterFile, new BaseResponseListener<String>() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                getView().onUploadAvaterSuccess(UserHelper.instance().getLoginUser().getAvater());
            }

            @Override
            public void onFaild(ErrorStatus e) {
                super.onFaild(e);
                getView().toastErrorMsg(e);
            }
        });
    }
}
