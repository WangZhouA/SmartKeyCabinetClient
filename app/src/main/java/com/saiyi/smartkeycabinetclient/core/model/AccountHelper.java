package com.saiyi.smartkeycabinetclient.core.model;

import android.text.TextUtils;

import com.lib.fast.common.cache.SharePerferenceHelper;
import com.saiyi.smartkeycabinetclient.user.model.bean.Account;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

/**
 * created by siwei on 2018/12/4
 */
public class AccountHelper {

    private static final String SP_NAME = "core.model.AccountHelper#account_sp_name";
    private static final String SP_KEY_ACOOUNT = "core.model.UserHelper#sp_key_account";

    private SharePerferenceHelper mSharePerferenceHelper;

    private static AccountHelper INSTANCE;

    public static AccountHelper instance() {
        if (INSTANCE == null) {
            synchronized (AccountHelper.class) {
                INSTANCE = new AccountHelper();
            }
        }
        return INSTANCE;
    }

    private AccountHelper() {
        mSharePerferenceHelper = SharePerferenceHelper.createSharePerference(SP_NAME);
    }

    public void cachePhone(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            mSharePerferenceHelper.putString(SP_KEY_ACOOUNT, phone);
            EventBus.getDefault().post(Action.UserAccountChanged);
        }
    }

    public String getCachePhone() {
        return mSharePerferenceHelper.getString(SP_KEY_ACOOUNT, "");
    }

    /**记住账户信息*/
    public void rememberAccount(Account account) {
        if (account != null) {
            DataSupport.deleteAll(Account.class);
            account.save();
        }
    }

    /**获取被记住的账户信息*/
    public Account getRememberAccount(String phone) {
        return getAccountByPhone(phone);
    }

    private Account getAccountByPhone(String phone) {
        Account account = null;
        if (!TextUtils.isEmpty(phone)) {
            account = DataSupport.where(String.format("phone = '%s'", phone)).findFirst(Account.class);
        }
        return account;
    }
}
