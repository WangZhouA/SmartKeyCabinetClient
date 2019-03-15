package com.saiyi.smartkeycabinetclient.key.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyCabinetDevice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * created by siwei on 2018/11/9
 */
public class SelectKeyCabinetAdapter extends BaseQuickAdapter<KeyCabinetDevice, BaseViewHolder> {

    private Map<Integer, Boolean> checkStatusMap = new HashMap<>();

    public SelectKeyCabinetAdapter(@Nullable List<KeyCabinetDevice> data) {
        super(R.layout.layout_select_key_cabinet_item, data);
        initCheckStatus(data);
    }

    //初始化被选中的状态
    private void initCheckStatus(Collection<? extends KeyCabinetDevice> devices) {
        checkStatusMap.clear();
        if (devices != null) {
            for (int i = 0; i < devices.size(); i++) {
                checkStatusMap.put(i, false);
            }
        }
    }

    @Override
    public void replaceData(@NonNull Collection<? extends KeyCabinetDevice> data) {
        initCheckStatus(data);
        super.replaceData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KeyCabinetDevice item) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.key_cabinet_name_tv, item.getDeviceName());
        final CheckBox checkBox = helper.getView(R.id.key_cabinet_ck);
        boolean isCheck = checkStatusMap.get(position);
        checkBox.setChecked(isCheck);


        View itemView = helper.getView(R.id.list_item);
        if (itemView.getTag() == null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = v.findViewById(R.id.key_cabinet_ck);
                    cb.setChecked(!cb.isChecked());
                    int position = (int) v.getTag();
                    setItemChecked(position, cb.isChecked());
                }
            });
        }
        itemView.setTag(position);
    }

    private void setItemChecked(int position, boolean isChecked) {
        if (checkStatusMap.containsKey(position)) {
            if (isChecked) {
                if (checkStatusMap.containsValue(true)) {
                    //循环寻找item,保持一个设备被选中
                    synchronized (checkStatusMap) {
                        Iterator<Map.Entry<Integer, Boolean>> iterator = checkStatusMap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<Integer, Boolean> entry = iterator.next();
                            if (entry.getValue()) {
                                entry.setValue(false);
                                notifyItemChanged(entry.getKey());
                            }
                        }
                    }
                }
            }
            checkStatusMap.put(position, isChecked);
        }
    }

    /**
     * 获取被选中的item
     */
    public KeyCabinetDevice getCheckedItem() {
        synchronized (checkStatusMap) {
            Iterator<Map.Entry<Integer, Boolean>> iterator = checkStatusMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Boolean> entry = iterator.next();
                if (entry.getValue()) {
                    return getData().get(entry.getKey());
                }
            }
        }
        return null;
    }

}
