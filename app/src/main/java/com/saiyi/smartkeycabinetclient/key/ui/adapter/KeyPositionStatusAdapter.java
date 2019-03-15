package com.saiyi.smartkeycabinetclient.key.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyPosition;

import java.util.Arrays;

/**
 * created by siwei on 2018/11/9
 */
public class KeyPositionStatusAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    public KeyPositionStatusAdapter() {
        super(R.layout.layout_key_status_item, Arrays.asList(new Integer[]{KeyPosition.StatusNormal, KeyPosition.StatusUseing}));
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        helper.setText(R.id.status_tv, KeyPosition.getKeyPositionStatus(item));
        helper.setBackgroundRes(R.id.status_view, KeyPosition.getKeyPositionBgRes(item));
    }
}
