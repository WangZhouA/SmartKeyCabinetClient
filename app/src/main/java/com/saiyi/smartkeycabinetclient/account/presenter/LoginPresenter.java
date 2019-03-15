package com.saiyi.smartkeycabinetclient.account.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.lib.fast.common.http.BaseResponseListener;
import com.lib.fast.common.http.exception.ErrorStatus;
import com.lib.fast.common.mvp.PresenterImpl;
import com.lib.fast.common.utils.StringUtils;
import com.saiyi.smartkeycabinetclient.account.model.LoginModel;
import com.saiyi.smartkeycabinetclient.account.ui.LoginActivity;
import com.saiyi.smartkeycabinetclient.core.error.AppError;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;

/**
 * created by siwei on 2018/11/5
 */
public class    LoginPresenter extends PresenterImpl<LoginActivity, LoginModel> {

    public LoginPresenter(Context context) {
        super(context);
    }

    @Override
    public LoginModel initModel(Context context) {
        return new LoginModel(context);
    }

    /**
     * 登陆
     */
    public boolean login(final String phone, final String pwd, final boolean isRemember) {
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
            getView().toast("请输入手机号或密码");
        } else if (!StringUtils.isMobileNum(phone)) {
            getView().toast("请输入正确的手机号");
        } else {
            getModel().login(phone, pwd, new BaseResponseListener<User>() {
                @Override
                public void onResponse(User data) {
                    super.onResponse(data);
                    //缓存当前登陆的用户手机号
                    UserHelper.instance().cacheUserAccount(phone);
                    if (isRemember) {
                        //记住密码
                        UserHelper.instance().rememberPwd(phone, pwd);
                    }
                    checkUserFillInformation(data);
                }

                @Override
                public void onFaild(ErrorStatus e) {
                    super.onFaild(e);
                    getView().toastErrorMsg(e);
                }
            });
            return true;
        }
        return false;
    }

    /**
     * 检查用户时候已填写自己的信息
     */
    private void checkUserFillInformation(User user) {
        getModel().checkUserFillInformation(user.getUid(), new BaseResponseListener<User>() {
            @Override
            public void onResponse(User data) {
                super.onResponse(data);
                getView().goHomeActivity();
            }

            @Override
            public void onFaild(ErrorStatus e) {
                super.onFaild(e);
                if (e.code == AppError.NotFullUserInfomation.getCode()) {
                    getView().goPerfectInfomationActivity();
                } else {
                    getView().toastErrorMsg(e);
                }
            }
        });
    }
}
