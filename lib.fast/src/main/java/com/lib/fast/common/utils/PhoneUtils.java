package com.lib.fast.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.lib.fast.common.R;

/**
 * Created by Administrator on 2015/8/12.
 */
public class PhoneUtils {

    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context){
        TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    @SuppressLint("MissingPermission")
    public static String getIMSI(Context context){
        TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }
}
