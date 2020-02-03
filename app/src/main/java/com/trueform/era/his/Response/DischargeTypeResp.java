package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.DischargeTypeList;

import java.util.List;

public class DischargeTypeResp {
    @SerializedName("dischargeTypeList")
    @Expose
    public List<DischargeTypeList> dischargeTypeList = null;

    public List<DischargeTypeList> getDischargeTypeList() {
        return dischargeTypeList;
    }
}
