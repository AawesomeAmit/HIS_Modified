package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.DischargeTypeList;

import java.util.List;

public class DischargeTypeResp {
    @SerializedName("dischargeTypeList")
    @Expose
    public List<DischargeTypeList> dischargeTypeList = null;

    public List<DischargeTypeList> getDischargeTypeList() {
        return dischargeTypeList;
    }
}
