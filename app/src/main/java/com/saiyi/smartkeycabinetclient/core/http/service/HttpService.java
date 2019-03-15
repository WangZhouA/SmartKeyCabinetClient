package com.saiyi.smartkeycabinetclient.core.http.service;

import com.lib.fast.common.http.BaseResponse;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyPositionDetails;
import com.saiyi.smartkeycabinetclient.device.model.bean.Message;
import com.saiyi.smartkeycabinetclient.key.model.bean.Application;
import com.saiyi.smartkeycabinetclient.key.model.bean.ApplicationDetails;
import com.saiyi.smartkeycabinetclient.key.model.bean.Applicationer;
import com.saiyi.smartkeycabinetclient.key.model.bean.Approver;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyPosition;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * created by siwei on 2018/12/3
 */
public interface HttpService {

    //===================================用户模块接口================================

    /**
     * 登录
     */
    @POST("keycabinet/app/user/login")
    Observable<BaseResponse<User>> login(@Body RequestBody requestBody);

    /**
     * 注册
     */
    @POST("keycabinet/app/user/register")
    Observable<BaseResponse<Void>> register(@Body RequestBody requestBody);

    /**
     * 查询账号是否存在
     */
    @GET("keycabinet/app/user/checkPhone")
    Observable<BaseResponse<Void>> checkPhone(@Query("phone") String phone);

    /**
     * 查询个人信息
     */
    @GET("keycabinet/app/user/getUserByUserId")
    Observable<BaseResponse<User>> getUserByUserId(@Query("userId") long uid);

    /**
     * 找回密码
     */
    @POST("keycabinet/app/user/updatePasswordByPhone")
    Observable<BaseResponse<Void>> updatePasswordByPhone(@Body RequestBody requestBody);

    /**
     * 意见反馈
     */
    @Multipart
    @POST("keycabinet/app/user/addOpinion")
    Observable<BaseResponse<Void>> addOpinion(@Part List<MultipartBody.Part> parts);

    /**
     * 发送验证码
     */
    @GET("keycabinet/app/user/sendCode")
    Observable<BaseResponse<Void>> sendCode(@Query("phone") String phone);

    /**
     * 修改个人信息
     */
    @POST("keycabinet/app/user/updateUserByUserId")
    Observable<BaseResponse<Void>> updateUserByUserId(@Body RequestBody requestBody);

    /**
     * 上传图片
     */
    @Multipart
    @POST("keycabinet/app/user/addHeadPicture")
    Observable<BaseResponse<String>> addHeadPicture(@Part List<MultipartBody.Part> parts);


    //=================================申请钥匙模块接口==================================

    /**
     * 修改申请
     */
    @POST("keycabinet/app/apply/updateApply")
    Observable<BaseResponse<Void>> updateApply(@Body RequestBody requestBody);

    /**
     * 审批人列表
     */
    @GET("keycabinet/app/apply/getApprover")
    Observable<BaseResponse<List<Approver>>> getApprover(@Query("userId") long uid);

    /**
     * 审批列表
     */
    @POST("keycabinet/app/apply/getExaminedList")
    Observable<BaseResponse<List<Applicationer>>> getExaminedList(@Body RequestBody body);

    /**
     * 审批申请
     */
    @POST("keycabinet/app/apply/updateApplyState")
    Observable<BaseResponse<Void>> updateApplyState(@Body RequestBody body);

    /**
     * 延时申请
     */
    @POST("keycabinet/app/apply/delayedApply")
    Observable<BaseResponse<Void>> delayedApply(@Body RequestBody body);

    /**
     * 申请列表
     */
    @POST("keycabinet/app/apply/getApplyList")
    Observable<BaseResponse<List<Application>>> getApplyList(@Body RequestBody body);

    /**
     * 申请详情
     */
    @GET("keycabinet/app/apply/getApplyDetails")
    Observable<BaseResponse<ApplicationDetails>> getApplyDetails(@Query("aid") long aid);

    /**
     * 申请钥匙
     */
    @POST("keycabinet/app/apply/addApply")
    Observable<BaseResponse<Void>> addApply(@Body RequestBody requestBody);


    //=================================设备模块接口==================================

    /**
     * 修改设备名称
     */
    @POST("keycabinet/app/device/updateDeviceinfoName")
    Observable<BaseResponse<Void>> updateDeviceinfoName(@Body RequestBody requestBody);

    /**
     * 删除设备
     */
    @GET("keycabinet/app/device/delectDeviceinfo")
    Observable<BaseResponse<Void>> delectDeviceinfo(@Query("deviceinfoId") long did);

    /**
     * 我的消息
     */
    @GET("keycabinet/app/device/queryCodeList")
    Observable<BaseResponse<List<Message>>> queryCodeList(@Query("userId") long uid);

    /**
     * 添加设备
     */
    @POST("keycabinet/app/device/addDeviceinfo")
    Observable<BaseResponse<Void>> addDeviceinfo(@Body RequestBody requestBody);

    /**
     * 设备列表
     */
    @GET("keycabinet/app/device/getDeviceList")
    Observable<BaseResponse<List<KeyCabinetDevice>>> getDeviceList(@Query("userId") long uid);

    /**
     * 钥匙列表
     */
    @GET("keycabinet/app/device/getKeyList")
    Observable<BaseResponse<List<KeyPosition>>> getKeyList(@Query("did") long did);

    /**
     * 钥匙位详情
     */
    @GET("keycabinet/app/device/queryKeyDetails")
    Observable<BaseResponse<KeyPositionDetails>> queryKeyDetails(@Query("kid") long kid);
}
