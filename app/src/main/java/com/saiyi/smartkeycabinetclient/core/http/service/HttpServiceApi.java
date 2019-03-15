package com.saiyi.smartkeycabinetclient.core.http.service;

import com.elvishew.xlog.XLog;
import com.lib.fast.common.http.BaseResponse;
import com.lib.fast.common.http.RetorfitServices;
import com.lib.fast.common.http.params.JsonRequestBodyBuilder;
import com.lib.fast.common.utils.DateUtils;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyPosition;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyPositionDetails;
import com.saiyi.smartkeycabinetclient.device.model.bean.Message;
import com.saiyi.smartkeycabinetclient.key.model.bean.Application;
import com.saiyi.smartkeycabinetclient.key.model.bean.ApplicationDetails;
import com.saiyi.smartkeycabinetclient.key.model.bean.ApplicationKeyPosition;
import com.saiyi.smartkeycabinetclient.key.model.bean.Applicationer;
import com.saiyi.smartkeycabinetclient.key.model.bean.Approver;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Query;

/**
 * created by siwei on 2018/12/3
 */
public class HttpServiceApi {

    private static HttpServiceApi sHttpServiceApi;
    private HttpService mHttpService;

    private HttpServiceApi() {
        mHttpService = RetorfitServices.getService(HttpService.class);
    }

    public static HttpServiceApi instance() {
        if (sHttpServiceApi == null) {
            synchronized (HttpServiceApi.class) {
                sHttpServiceApi = new HttpServiceApi();
            }
        }
        return sHttpServiceApi;
    }

    //===================================用户模块接口================================

    /**
     * 登录
     *
     * @param phone 密码
     * @param pwd   手机号
     */
    public Observable<BaseResponse<User>> login(String phone, String pwd) {
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("phone", phone)
                .addParam("password", pwd)
                .build();
        return mHttpService.login(requestBody);
    }

    /**
     * 注册
     *
     * @param phone      手机号
     * @param password   密码
     * @param repassword 确认密码
     * @param code       验证码
     */
    public Observable<BaseResponse<Void>> register(String phone, String password, String repassword, String code) {
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("phone", phone)
                .addParam("password", password)
                .addParam("repassword", repassword)
                .addParam("code", code).build();
        return mHttpService.register(requestBody);
    }

    /**
     * 查询账号是否存在
     *
     * @param phone 手机号
     */
    public Observable<BaseResponse<Void>> checkPhone(String phone) {
        return mHttpService.checkPhone(phone);
    }

    /**
     * 查询个人信息
     *
     * @param uid 用户id
     */
    public Observable<BaseResponse<User>> getUserByUserId(long uid) {
        return mHttpService.getUserByUserId(uid);
    }

    /**
     * 找回密码
     *
     * @param phone      手机号
     * @param password   密码
     * @param repassword 确认密码
     * @param code       验证码
     */
    public Observable<BaseResponse<Void>> updatePasswordByPhone(String phone, String password, String repassword, String code) {
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("phone", phone)
                .addParam("password", password)
                .addParam("repassword", repassword)
                .addParam("code", code)
                .build();
        return mHttpService.updatePasswordByPhone(requestBody);
    }

    /**
     * 意见反馈
     *
     * @param uid     用户id
     * @param content 反馈内容
     * @param file    反馈图片
     */
    public Observable<BaseResponse<Void>> addOpinion(long uid, String content, File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("userId", String.valueOf(uid))
                .addFormDataPart("content", content);
        RequestBody imgRequestBody = RequestBody.create(MultipartBody.FORM, file);
        builder.addFormDataPart("file", String.format("opinion_%s_%s", String.valueOf(uid), file.getName()), imgRequestBody);
        return mHttpService.addOpinion(builder.build().parts());
    }

    /**
     * 发送验证码
     *
     * @param phone 手机号
     */
    public Observable<BaseResponse<Void>> sendCode(String phone) {
        return mHttpService.sendCode(phone);
    }

    /**
     * 修改部分个人信息
     */
    public Observable<BaseResponse<Void>> updateSomeUserInfo(User user) {
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("userId", user.getUid())
                .addParam("sex", user.getGender())
                .addParam("name", user.getName())
                .addParam("jobno", user.getJobno())
                .build();
        return mHttpService.updateUserByUserId(requestBody);
    }

    /**
     * 修改个人信息
     */
    public Observable<BaseResponse<Void>> updateUserByUserId(User user) {
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("userId", user.getUid())
                .addParam("sex", user.getGender())
                .addParam("name", user.getName())
                .addParam("jobno", user.getJobno())
                .addParam("job", user.getJob())
                .build();
        return mHttpService.updateUserByUserId(requestBody);
    }

    /**
     * 上传图片
     *
     * @param uid     用户id
     * @param imgFile 图片文件
     */
    public Observable<BaseResponse<String>> addHeadPicture(long uid, File imgFile) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userId", String.valueOf(uid));
        RequestBody imgRequestBody = RequestBody.create(MultipartBody.FORM, imgFile);
        XLog.d("fileName:%s", String.format("avater_%s_%s", String.valueOf(uid), imgFile.getName()));
        builder.addFormDataPart("file", String.format("avater_%s_%s", String.valueOf(uid), imgFile.getName()), imgRequestBody);
        return mHttpService.addHeadPicture(builder.build().parts());
    }

    //=================================申请钥匙模块接口==================================

    /**
     * 修改申请
     */
    public Observable<BaseResponse<Void>> updateApply(User applicationUser, ApplicationKeyPosition applicationKeyInfo) {
        //格式:2018-10-15 14:00
        SimpleDateFormat simpleDateFormat = DateUtils.createYMDHMFormat("-", "-", " ", ":");
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("aid", applicationUser.getUid())
                .addParam("auditor", applicationKeyInfo.getApprovel().getUid())
                .addParam("startTime", simpleDateFormat.format(new Date(applicationKeyInfo.getStartTime())))
                .addParam("endTime", simpleDateFormat.format(new Date(applicationKeyInfo.getEndTime())))
                .addParam("name", applicationKeyInfo.getKeyPosition().getPositionName())
                .addParam("position", applicationKeyInfo.getKeyPosition().getPosition())
                .addParam("reason", applicationKeyInfo.getReason())
                .build();
        return mHttpService.updateApply(requestBody);
    }

    /**
     * 审批人列表
     */
    public Observable<BaseResponse<List<Approver>>> getApprover(@Query("userId") long uid) {
        return mHttpService.getApprover(uid);
    }

    /**
     * 审批列表
     */
    public Observable<BaseResponse<List<Applicationer>>> getExaminedList(@KeyPosition.KeyPositionStatus int status, long uid) {
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("state", status)
                .addParam("userId", uid)
                .build();
        return mHttpService.getExaminedList(requestBody);
    }

    /**
     * 审批申请
     */
    public Observable<BaseResponse<Void>> updateApplyState(long aid, String disagreement, @Approver.ApproverStatus int status) {
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("aid", aid)
                .addParam("disagreement", disagreement)
                .addParam("status", status).build();
        return mHttpService.updateApplyState(requestBody);
    }

    /**
     * 延时申请
     */
    public Observable<BaseResponse<Void>> delayedApply(long aid, long auditor, long startTime, long endTime, String reason) {
        //格式:2018-10-15 14:00
        SimpleDateFormat simpleDateFormat = DateUtils.createYMDHMFormat("-", "-", " ", ":");
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("aid", aid)
                .addParam("auditor", auditor)
                .addParam("startTime", simpleDateFormat.format(new Date(startTime)))
                .addParam("endTime", simpleDateFormat.format(new Date(endTime)))
                .addParam("reason", reason)
                .build();
        return mHttpService.delayedApply(requestBody);
    }

    /**
     * 申请列表
     */
    public Observable<BaseResponse<List<Application>>> getApplyList(long uid, String condition, @Approver.ApproverStatus int state) {
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("uid", uid)
                .addParam("condition", condition)
                .addParam("state", state)
                .build();
        return mHttpService.getApplyList(requestBody);
    }

    /**
     * 申请详情
     */
    public Observable<BaseResponse<ApplicationDetails>> getApplyDetails(long aid) {
        return mHttpService.getApplyDetails(aid);
    }

    /**
     * 申请钥匙
     */
    public Observable<BaseResponse<Void>> addApply(User applicationUser, ApplicationKeyPosition applicationKeyPosition) {
        //格式:2018-10-15 14:00
        SimpleDateFormat simpleDateFormat = DateUtils.createYMDHMFormat("-", "-", " ", ":");
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("auditor", applicationKeyPosition.getApprovel().getUid())
                .addParam("startTime", simpleDateFormat.format(applicationKeyPosition.getStartTime()))
                .addParam("endTime", simpleDateFormat.format(applicationKeyPosition.getEndTime()))
                .addParam("mac", applicationKeyPosition.getKeyCabinetDevice().getMac())
                .addParam("name", applicationKeyPosition.getKeyPosition().getPositionName())
                .addParam("position", applicationKeyPosition.getKeyPosition().getPosition())
                .addParam("reason", applicationKeyPosition.getReason())
                .addParam("userId", applicationUser.getUid())
                .build();
        return mHttpService.addApply(requestBody);
    }


    //=================================设备模块接口==================================

    /**
     * 修改设备名称
     */
    public Observable<BaseResponse<Void>> updateDeviceinfoName(long did, String deviceName) {
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("deviceinfoId", did)
                .addParam("name", deviceName)
                .build();
        return mHttpService.updateDeviceinfoName(requestBody);
    }

    /**
     * 删除设备
     */
    public Observable<BaseResponse<Void>> delectDeviceinfo(long did) {
        return mHttpService.delectDeviceinfo(did);
    }

    /**
     * 我的消息
     */
    public Observable<BaseResponse<List<Message>>> queryCodeList(long uid) {
        return mHttpService.queryCodeList(uid);
    }

    /**
     * 添加设备
     */
    public Observable<BaseResponse<Void>> addDeviceinfo(String mac, long uid) {
        RequestBody requestBody = JsonRequestBodyBuilder.create()
                .addParam("mac", mac)
                .addParam("userId", uid)
                .build();
        return mHttpService.addDeviceinfo(requestBody);
    }

    /**
     * 设备列表
     */
    public Observable<BaseResponse<List<KeyCabinetDevice>>> getDeviceList(long uid) {
        return mHttpService.getDeviceList(uid);
    }

    /**
     * 钥匙列表
     */
    public Observable<BaseResponse<List<KeyPosition>>> getKeyList(long did) {
        return mHttpService.getKeyList(did);
    }

    /**
     * 钥匙位详情
     */
    public Observable<BaseResponse<KeyPositionDetails>> queryKeyDetails(long kid) {
        return mHttpService.queryKeyDetails(kid);
    }

}
