package com.saiyi.smartkeycabinetclient.user.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.lib.fast.common.dialog.BaseDialog;
import com.lib.fast.common.dialog.HeadPortraitMenuDialog;
import com.lib.fast.common.dialog.InputPwdDialog;
import com.lib.fast.common.helper.ImgSetHelper;
import com.lib.fast.common.utils.StringUtils;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPActivity;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;
import com.saiyi.smartkeycabinetclient.user.presenter.UserInfoPresenter;
import com.saiyi.smartkeycabinetclient.user.ui.dialog.GenderMenuDialog;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class UserInfoActivity extends BKMVPActivity<UserInfoPresenter>
        implements ImgSetHelper.SetListener, BaseDialog.OnDialogClickListener {

    @BindView(R.id.name_tv)
    TextView mNameTv;

    @BindView(R.id.avater_iv)
    ImageView mAvaterIv;

    @BindView(R.id.gender_tv)
    TextView mGenderTv;

    @BindView(R.id.phone_tv)
    TextView mPhoneTv;

    @BindView(R.id.office_tv)
    TextView mOfficeTv;

    @BindView(R.id.jobnum_tv)
    TextView mJobnumTv;

    private ImgSetHelper mImgSetHelper;
    private HeadPortraitMenuDialog mHeadPortraitMenuDialog;
    private GenderMenuDialog mGenderMenuDialog;
    private InputPwdDialog mInputPwdDialog;
    private CropCircleTransformation mCropCircleTransformation;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
    }

    @Override
    public UserInfoPresenter initPresenter(Context context) {
        return new UserInfoPresenter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        getTitleBar().setTitle("个人中心");
    }

    @Override
    protected void initData() {
        super.initData();
        mCropCircleTransformation = new CropCircleTransformation();
        mImgSetHelper = new ImgSetHelper(this);
        mImgSetHelper.setListener(this);
        getPresenter().getUserDetials();
    }

    @OnClick(R.id.avater_rl)
    protected void onAvaterClick() {
        dismissProgressDialog();
        mHeadPortraitMenuDialog = new HeadPortraitMenuDialog(this);
        mHeadPortraitMenuDialog.setClick(this);
        mHeadPortraitMenuDialog.show();
    }

    /**
     * 获取用户详细信息成功
     */
    public void onGetUserDetialsSuccess(User user) {
        mUser = user;
        dismissProgressDialog();
        Picasso.with(this).load(user.getAvater())
                .error(R.drawable.headsculpture)
                .transform(mCropCircleTransformation)
                .into(mAvaterIv);
        mGenderTv.setText(User.getGender(user.getGender()));
        mNameTv.setText(user.getName());
        mPhoneTv.setText(StringUtils.getOmitTelStr(user.getPhone()));
        mOfficeTv.setText(user.getJob());
        mJobnumTv.setText(user.getJobno());
    }

    /**
     * 上传头像成功
     */
    public void onUploadAvaterSuccess(String avator) {
        dismissProgressDialog();
        Picasso.with(this).load(avator)
                .error(R.drawable.headsculpture)
                .transform(mCropCircleTransformation)
                .into(mAvaterIv);
    }

    /**
     * 更新用户信息
     */
    public void onUpdateUserComplate(User user) {
        mUser = user;
        dismissProgressDialog();
        mNameTv.setText(user.getName());
        mGenderTv.setText(User.getGender(user.getGender()));
        mOfficeTv.setText(user.getJob());
        mJobnumTv.setText(user.getJobno());
    }

    @Override
    protected void dismissProgressDialog() {
        super.dismissProgressDialog();
        if (mHeadPortraitMenuDialog != null && mHeadPortraitMenuDialog.isShowing()) {
            mHeadPortraitMenuDialog.dismiss();
        }
    }


    @Override
    public void onCropSuccess(File file) {
        showCustomLoading("正在上传");
        getPresenter().uploadAvater(file);
    }

    @Override
    public void onCropFaild() {
        toast("设置失败");
    }

    @Override
    public void onDialogClick(int whichOne) {
        switch (whichOne) {
            case HeadPortraitMenuDialog.WHICH_TAKE_PHOTO:
                mImgSetHelper.takePhotoAndCrop();
                break;
            case HeadPortraitMenuDialog.WHICH_SELECT_PHOTO:
                mImgSetHelper.selectPhotoAndCrop();
                break;
        }
    }

    @OnClick({R.id.name_rl, R.id.gender_rl, R.id.phone_rl, R.id.office_rl, R.id.jobnum_rl})
    protected void onMenuItemClick(View view) {
        switch (view.getId()) {
            case R.id.name_rl:
                showInputNameDialog();
                break;
            case R.id.gender_rl:
                showGenderMenuDialog();
                break;
            case R.id.phone_rl:
                break;
            case R.id.office_rl:
                break;
            case R.id.jobnum_rl:
                showInputJonnoDialog();
                break;
        }
    }

    //显示输入姓名弹窗
    private void showInputNameDialog() {
        dismissProgressDialog();
        mInputPwdDialog = new InputPwdDialog(this);
        mInputPwdDialog.hidenTitle()
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .setInputText(mNameTv.getText().toString())
                .setInputHintText("请输入名称")
                .setMsgText("请输入名称")
                .setClick(new BaseDialog.OnDialogClickListener() {
                    @Override
                    public void onDialogClick(int whichOne) {
                        switch (whichOne) {
                            case InputPwdDialog.WHICH_COMPLATE:
                                updateUserName(mInputPwdDialog.getInputText().trim());
                                break;
                        }
                    }
                });
        mInputPwdDialog.show();
    }

    //修改用户的名称
    private void updateUserName(String name) {
        if (TextUtils.isEmpty(name)) {
            toast("请输入有效的名称");
        } else {
            try {
                User newUser = (User) mUser.clone();
                newUser.setName(name);
                getPresenter().updateUser(newUser, mUser);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    //显示性别弹窗
    private void showGenderMenuDialog() {
        dismissProgressDialog();
        mGenderMenuDialog = new GenderMenuDialog(this);
        mGenderMenuDialog.setClick(new BaseDialog.OnDialogClickListener() {
            @Override
            public void onDialogClick(int whichOne) {
                switch (whichOne) {
                    case GenderMenuDialog.WHICH_MAN:
                        updateUserGender(User.MAN);
                        break;
                    case GenderMenuDialog.WHICH_FEMAN:
                        updateUserGender(User.FEMAN);
                        break;
                }
            }
        });
        mGenderMenuDialog.show();
    }

    //修改用户性别
    private void updateUserGender(@User.Gender int gender) {
        try {
            User newUser = (User) mUser.clone();
            XLog.tag("okhttp").d("clone new user:%s", newUser.toString());
            newUser.setGender(gender);
            getPresenter().updateUser(newUser, mUser);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

    //显示输入姓名弹窗
    private void showInputJonnoDialog() {
        dismissProgressDialog();
        mInputPwdDialog = new InputPwdDialog(this);
        mInputPwdDialog.hidenTitle()
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .setInputText(mJobnumTv.getText().toString())
                .setInputHintText("请输入名称")
                .setMsgText("请输入名称")
                .setClick(new BaseDialog.OnDialogClickListener() {
                    @Override
                    public void onDialogClick(int whichOne) {
                        switch (whichOne) {
                            case InputPwdDialog.WHICH_COMPLATE:
                                updateUserJonno(mInputPwdDialog.getInputText().trim());
                                break;
                        }
                    }
                });
        mInputPwdDialog.show();
    }

    //修改用户的名称
    private void updateUserJonno(String name) {
        if (TextUtils.isEmpty(name)) {
            toast("请输入有效的工号");
        } else {
            try {
                User newUser = (User) mUser.clone();
                newUser.setJobno(name);
                getPresenter().updateUser(newUser, mUser);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

}
