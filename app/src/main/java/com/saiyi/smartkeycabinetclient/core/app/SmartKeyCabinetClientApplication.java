package com.saiyi.smartkeycabinetclient.core.app;

import android.support.annotation.NonNull;

import com.lib.fast.common.activity.BaseApplication;
import com.lib.fast.common.config.IBuildConfig;
import com.saiyi.smartkeycabinetclient.core.http.intercept.TokenIntercept;

import okhttp3.Interceptor;

/**
 * created by siwei on 2018/11/3
 */
public class SmartKeyCabinetClientApplication extends BaseApplication {
    @NonNull
    @Override
    public IBuildConfig getBuildConfig() {
        return new SmartKeyCabinetClientBuildConfig();
    }

    @Override
    protected Interceptor[] addOkHttpIntercept() {
        return new Interceptor[]{new TokenIntercept()};
    }
}
