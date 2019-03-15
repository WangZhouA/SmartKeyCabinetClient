package com.saiyi.smartkeycabinetclient.account.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.account.presenter.PerfectInfomationPresenter;
import com.saiyi.smartkeycabinetclient.core.base.BKMVPActivity;
import com.saiyi.smartkeycabinetclient.core.model.UserHelper;
import com.saiyi.smartkeycabinetclient.home.ui.HomeActivity;
import com.saiyi.smartkeycabinetclient.user.model.bean.User;

import butterknife.BindView;
import butterknife.OnClick;

public class PerfectInfomationActivity extends BKMVPActivity<PerfectInfomationPresenter> {

    @BindView(R.id.uname_et)
    EditText mUNameEt;

    @BindView(R.id.gender_ck)
    CheckBox mGenderCk;

    @BindView(R.id.office_et)
    EditText mOfficeEt;

    @BindView(R.id.jobnum_et)
    EditText mJobnumEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_infomation);
    }

    @Override
    public PerfectInfomationPresenter initPresenter(Context context) {
        return new PerfectInfomationPresenter(context);
    }

    @Override
    protected void initView() {
        super.initView();
        getTitleBar().setTitle("完善资料");
        initUserInformation();
    }

    private void initUserInformation(){
        User user = UserHelper.instance().getLoginUser();
        mUNameEt.setText(user.getName());
        mGenderCk.setChecked(user.getGender() == User.MAN);
        mJobnumEt.setText(user.getJobno());
        mOfficeEt.setText(user.getJob());
    }

    @OnClick(R.id.submit_btn)
    protected void onSubmitClick() {
        String uname = mUNameEt.getText().toString().trim();
        String office = mOfficeEt.getText().toString().trim();
        String jobNum = mJobnumEt.getText().toString().trim();
        if (getPresenter().submitPerfectInfomation(uname, mGenderCk.isChecked(), office, jobNum)) {
            showCustomLoading("正在提交");
        }
    }

    /**提交信息成功*/
    public void onSubmitPerfectInfomationSuccess() {
        dismissProgressDialog();
        openActivity(HomeActivity.class);
        finish();
    }
}
