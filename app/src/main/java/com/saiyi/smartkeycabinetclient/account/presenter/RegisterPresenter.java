package com.saiyi.smartkeycabinetclient.account.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.lib.fast.common.http.BaseResponseListener;
import com.lib.fast.common.http.exception.ErrorStatus;
import com.lib.fast.common.mvp.PresenterImpl;
import com.lib.fast.common.utils.StringUtils;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.account.model.RegisterModel;
import com.saiyi.smartkeycabinetclient.account.ui.RegisterActivity;

/**
 * created by siwei on 2018/11/5
 */
public class RegisterPresenter extends PresenterImpl<RegisterActivity, RegisterModel> {

    public RegisterPresenter(Context context) {
        super(context);
    }

    @Override
    public RegisterModel initModel(Context context) {
        return new RegisterModel(context);
    }

    /**
     * 获取短信验证码
     */
    public boolean getSmsVerfiyCode(String phone) {
        if (TextUtils.isEmpty(phone) || !StringUtils.isMobileNum(phone)) {
            getView().toast("请输入正确的手机号");
            return false;
        } else {
            getModel().sendSmsCode(phone, new BaseResponseListener<Void>() {
                @Override
                public void onResponse(Void data) {
                    super.onResponse(data);
                    getView().onGetSmsVerifyCodeSuccess();
                }

                @Override
                public void onFaild(ErrorStatus e) {
                    super.onFaild(e);
                    getView().toastErrorMsg(e);
                }
            });
            return true;
        }
    }

    /**
     * 注册账户
     */
    public boolean registerAccount(final String phone, String smsCode, String pwd, String rePwd) {
        int smsVerifyLength = getContext().getResources().getInteger(R.integer.sms_verify_length);
        if (TextUtils.isEmpty(phone) || !StringUtils.isMobileNum(phone)) {
            getView().toast("请输入正确的手机号");
        } else if (TextUtils.isEmpty(smsCode) || !StringUtils.matchLengthNum(smsCode, smsVerifyLength)) {
            getView().toast("请输入验证码");
        } else if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(rePwd)) {
            getView().toast("请输入密码");
        } else if (!TextUtils.equals(pwd, rePwd)) {
            getView().toast("密码输入不一致");
        } else if (!StringUtils.matchesUserPwd(pwd) || !StringUtils.matchesUserPwd(rePwd)) {
            getView().toast("输入8位以上母字和数字密码");
        } else {
            getModel().register(phone, pwd, rePwd, smsCode, new BaseResponseListener<Void>() {
                @Override
                public void onResponse(Void data) {
                    super.onResponse(data);
                    getView().onRegisterSuccess();
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
}
