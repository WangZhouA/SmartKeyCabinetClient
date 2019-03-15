package com.saiyi.smartkeycabinetclient.user.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.lib.fast.common.activity.BaseApplication;
import com.lib.fast.common.config.IBuildConfig;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.core.base.BKActivity;

import butterknife.BindView;

public class AboutUsActivity extends BKActivity {

    @BindView(R.id.ap_name_tv)
    TextView mApName_tv;

    @BindView(R.id.ap_version_tv)
    TextView mApVersionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }

    @Override
    protected void initView() {
        super.initView();
        getTitleBar().setTitle("关于我们");
    }

    @Override
    protected void initData() {
        super.initData();
        IBuildConfig buildConfig = BaseApplication.getInstance().getBuildConfig();
        mApName_tv.setText(buildConfig.getAppName(this));
        mApVersionTv.setText(String.format("V%s", buildConfig.getVersionName(this)));
    }
}
