package com.saiyi.smartkeycabinetclient.key.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.saiyi.smartkeycabinetclient.R;
import com.saiyi.smartkeycabinetclient.key.model.bean.Approver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * created by siwei on 2018/11/12
 */
public class ApproverAdapter extends BaseQuickAdapter<Approver, BaseViewHolder> {

    private Map<Integer, Boolean> checkMaps = new HashMap<>();

    public ApproverAdapter(@Nullable List<Approver> data) {
        super(R.layout.layout_approvel_item, data);
        initCheckStatus(data);
    }

    @Override
    public void replaceData(@NonNull Collection<? extends Approver> data) {
        super.replaceData(data);
        initCheckStatus(data);
    }

    private void initCheckStatus(@NonNull Collection<? extends Approver> datas) {
        checkMaps.clear();
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                checkMaps.put(i, false);
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, Approver item) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.name_tv, String.format("%s %s", item.getJob(), item.getName()));
        CheckBox checkBox = helper.getView(R.id.approvel_ck);
        checkBox.setChecked(checkMaps.get(position));
        View itemView = helper.getView(R.id.list_item);
        ;
        if (itemView.getTag() == null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox ck = v.findViewById(R.id.approvel_ck);
                    ck.setChecked(!ck.isChecked());
                    int posi = (int) v.getTag();
                    setItemChecked(posi, ck.isChecked());
                }
            });
        }
        itemView.setTag(position);
    }

    //设置某项被选中,维持单选
    private void setItemChecked(int position, boolean isChecked) {
        if (isChecked && checkMaps.containsValue(isChecked)) {
            Iterator<Map.Entry<Integer, Boolean>> iterator=checkMaps.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<Integer, Boolean> entry = iterator.next();
                if(entry.getValue()){
                    entry.setValue(false);
                    notifyItemChanged(entry.getKey());
                }
            }
        }
        checkMaps.put(position, isChecked);
    }

    /**获取被选中的审批人*/
    public List<Approver> getSelectedApprovals(){
        List<Approver> approvals = new ArrayList<>();
        Iterator<Map.Entry<Integer, Boolean>> iterator=checkMaps.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, Boolean> entry = iterator.next();
            if(entry.getValue()){
                approvals.add(getData().get(entry.getKey()));
            }
        }
        return approvals;
    }
}
