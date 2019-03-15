package com.saiyi.smartkeycabinetclient.core.model;

import com.lib.fast.common.activity.BaseApplication;
import com.lib.fast.common.cache.ACache;
import com.lib.fast.common.cache.SharePerferenceHelper;
import com.saiyi.smartkeycabinetclient.user.model.bean.Account;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;

import org.greenrobot.eventbus.EventBus;

/**
 * created by siwei on 2018/11/5
 */
public class UserHelper {

    private static UserHelper INSTANCE;

    private static final String SP_NAME = "core.model.UserHelper#user_sp_name";

    private static final String CACHE_KEY_LOGIN_USER = "core.model.UserHelper#cache_key_login_user";

    private SharePerferenceHelper mSharePerferenceHelper;
    private ACache mACache;
    private User mLoginUser;

    public static UserHelper instance() {
        if (INSTANCE == null) {
            synchronized (UserHelper.class) {
                INSTANCE = new UserHelper();
            }
        }
        return INSTANCE;
    }

    private UserHelper() {
        mSharePerferenceHelper = SharePerferenceHelper.createSharePerference(SP_NAME);
        mACache = BaseApplication.getInstance().getCache();
    }

    /**
     * 缓存账户信息
     */
    public void cacheUserAccount(String phone) {
        AccountHelper.instance().cachePhone(phone);
    }

    /**
     * 获取缓存的用户账户信息
     */
    public String getCachePhone() {
        return AccountHelper.instance().getCachePhone();
    }

    /**
     * 记住密码
     */
    public void rememberPwd(String phone, String pwd) {
        AccountHelper.instance().rememberAccount(new Account(phone, pwd));
    }

    /**
     * 获取记住的密码
     */
    public Account getRememberPwd(String phone) {
        return AccountHelper.instance().getRememberAccount(phone);
    }

    /**是否登录*/
    public boolean isLogined(){
        return getLoginUser() != null;
    }

    /**
     * 设置当前操作的用户
     */
    public void userLogin(User user) {
        updateLoginUser(user);
    }

    private void updateLoginUser(User user){
        if (user != null) {
            mLoginUser = user;
            mACache.put(CACHE_KEY_LOGIN_USER, user);
        }
    }

    /**
     * 获取当前登陆的用户
     */
    public User getLoginUser() {
        if (mLoginUser == null) {
            mLoginUser = (User) mACache.getAsObject(CACHE_KEY_LOGIN_USER);
        }
        return mLoginUser;
    }

    /**
     * 退出登陆
     */
    public void exitLogin() {
        mLoginUser = null;
        mACache.remove(CACHE_KEY_LOGIN_USER);
    }

    /**
     * 填写用户信息
     */
    public void fullUserInformation(String name, int gender, String job, String jobno) {
        User user = getLoginUser();
        if (user != null) {
            user.setName(name);
            user.setGender(gender);
            user.setJob(job);
            user.setJobno(jobno);
            updateLoginUser(user);
            EventBus.getDefault().post(Action.FullUserInformation);
        }
    }

    /**
     * 更改头像
     */
    public void updateAvator(String avator) {
        User user = getLoginUser();
        if (user != null) {
            user.setAvater(avator);
            updateLoginUser(user);
            EventBus.getDefault().post(Action.UpdateAvator);
        }
    }
}
