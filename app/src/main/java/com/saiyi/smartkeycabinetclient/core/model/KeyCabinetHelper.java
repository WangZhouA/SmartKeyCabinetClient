package com.saiyi.smartkeycabinetclient.core.model;

import com.lib.fast.common.activity.BaseApplication;
import com.lib.fast.common.cache.ACache;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyPosition;
import com.saiyi.smartkeycabinetclient.key.model.bean.ApplicationKeyPosition;
import com.saiyi.smartkeycabinetclient.key.model.bean.Approver;

import org.greenrobot.eventbus.EventBus;

/**
 * created by siwei on 2018/11/9
 */
public class KeyCabinetHelper {

    private static KeyCabinetHelper INSTANCE;
    private static final String APPLICATION_KEY_SELECT_KEY_CABINET = "core.model.KeyCabinetHelper#application_key_select_key_cabinet";

    private ACache mACache;
    private ApplicationKeyPosition mApplicationKeyInfo;

    public static KeyCabinetHelper instance() {
        if (INSTANCE == null) {
            synchronized (KeyCabinetDevice.class) {
                INSTANCE = new KeyCabinetHelper();
            }
        }
        return INSTANCE;
    }

    private KeyCabinetHelper() {
        mACache = BaseApplication.getInstance().getCache();
    }

    /**
     * 清楚申请钥匙被选中的钥匙信息
     */
    public void clearSelectKeyCabinetOfApplicationKey() {
        mApplicationKeyInfo = null;
        mACache.remove(APPLICATION_KEY_SELECT_KEY_CABINET);
    }

    /**
     * 申请钥匙选中钥匙柜
     */
    public void setSelectKeyCabinetOfApplicationKey(KeyCabinetDevice keyCabinetDevice) {
        if(keyCabinetDevice != null){
            ApplicationKeyPosition applicationKeyInfo = getApplicationKeySelectKeyPosition();
            applicationKeyInfo.setKeyCabinetDevice(keyCabinetDevice);
            cacheApplicationKeyInfo(applicationKeyInfo);
            EventBus.getDefault().post(Action.ApplicationKeyOfDeviceChange);
        }
    }

    /**设置申请审批的钥匙位*/
    public void setKeyPositionOfApplicationKey(KeyPosition keyPosition){
        if(keyPosition != null){
            ApplicationKeyPosition applicationKeyInfo = getApplicationKeySelectKeyPosition();
            applicationKeyInfo.setKeyPosition(keyPosition);
            cacheApplicationKeyInfo(applicationKeyInfo);
            EventBus.getDefault().post(Action.KeyPositionChangedOfApplicationKey);
        }
    }

    /**设置申请审批的审批人*/
    public void setApprovalOfApplicationKey(Approver approval){
        if(approval != null){
            ApplicationKeyPosition applicationKeyInfo = getApplicationKeySelectKeyPosition();
            applicationKeyInfo.setApprovel(approval);
            cacheApplicationKeyInfo(applicationKeyInfo);
            EventBus.getDefault().post(Action.ApprovalChangeOfApplicationKey);
        }
    }

    /**
     * 申请钥匙选中钥匙柜
     */
    public void cacheApplicationKeyInfo(ApplicationKeyPosition applicationKeyInfo) {
        this.mApplicationKeyInfo = applicationKeyInfo;
        mACache.put(APPLICATION_KEY_SELECT_KEY_CABINET, applicationKeyInfo);
    }

    /**
     * 获取申请钥匙选中的钥匙柜
     */
    public ApplicationKeyPosition getApplicationKeySelectKeyPosition() {
        if(mApplicationKeyInfo == null){
            mApplicationKeyInfo = (ApplicationKeyPosition) mACache.getAsObject(APPLICATION_KEY_SELECT_KEY_CABINET);
        }
        if(mApplicationKeyInfo == null){
            mApplicationKeyInfo = new ApplicationKeyPosition();
        }
        return mApplicationKeyInfo;
    }

    /**选择申请的钥匙信息是否完成*/
    public boolean selectDeviceOfApplicationKeyComplate(){
        ApplicationKeyPosition applicationKeyInfo = (ApplicationKeyPosition) mACache.getAsObject(APPLICATION_KEY_SELECT_KEY_CABINET);
        return applicationKeyInfo != null
                && applicationKeyInfo.getKeyCabinetDevice() != null
                && applicationKeyInfo.getKeyPosition() != null;
    }


    public void applicationKeySelectComplate(){

    }
}
