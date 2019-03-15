package com.saiyi.smartkeycabinetclient.key.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elvishew.xlog.XLog;
import com.lib.fast.common.widgets.CheckTextView;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.device.model.bean.KeyPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * created by siwei on 2018/11/9
 */
public class KeyPositionAdapter extends BaseQuickAdapter<KeyPosition, BaseViewHolder> {

    private Map<Integer, Boolean> checkStatusMaps = new HashMap<>();

    public KeyPositionAdapter(@Nullable List<KeyPosition> data) {
        super(R.layout.layout_key_position_item, data);
        initCheckStatus(data);
    }

    @Override
    public void replaceData(@NonNull Collection<? extends KeyPosition> data) {
        super.replaceData(data);
        initCheckStatus(data);
    }

    private void initCheckStatus(Collection<? extends KeyPosition> datas) {
        checkStatusMaps.clear();
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                checkStatusMaps.put(i, false);
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, KeyPosition item) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.name_tv, item.getPosition());
        helper.setBackgroundRes(R.id.name_tv, KeyPosition.getKeyPositionBgRes(item.getStatus()));
        CheckTextView ctv = helper.getView(R.id.status_ctv);
        ctv.setChecked(checkStatusMaps.get(position));
        ctv.setEnabled(getItemEnable(item));
        if (ctv.getTag() == null) {
            ctv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    CheckTextView ctv = (CheckTextView) v;
                    ctv.setChecked(!ctv.isChecked());
                    XLog.tag("CKTEST").d("position:%s checked:%s", position, ctv.isChecked());
                    if (ctv.isChecked()) {
                        setItemChecked(position);
                    }
                }
            });
        }
        ctv.setTag(position);
    }

    private boolean getItemEnable(KeyPosition item) {
        if (item != null) {
            return item.getStatus() == KeyPosition.StatusNormal;
        }
        return false;
    }

    private void setItemChecked(int position) {
        Iterator<Map.Entry<Integer, Boolean>> iterator = checkStatusMaps.entrySet().iterator();
        if (checkStatusMaps.containsValue(true)) {
            while (iterator.hasNext()) {
                Map.Entry<Integer, Boolean> entry = iterator.next();
                if (entry.getValue()) {
                    entry.setValue(false);
                    notifyItemChanged(entry.getKey());
                }
            }
        }
        checkStatusMaps.put(position, true);
    }

    public List<KeyPosition> getCheckedItems(){
        List<KeyPosition> keyPositions = new ArrayList<>();
        Iterator<Map.Entry<Integer, Boolean>> iterator = checkStatusMaps.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, Boolean> entry = iterator.next();
            if(entry.getValue()){
                keyPositions.add(getData().get(entry.getKey()));
            }
        }
        return keyPositions;
    }

}
