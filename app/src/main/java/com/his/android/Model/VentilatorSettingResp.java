package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VentilatorSettingResp {
    @SerializedName("ventilatorSettingList")
    @Expose
    public List<VentilatorSettingList> ventilatorSettingList = null;

    public List<VentilatorSettingList> getVentilatorSettingList() {
        return ventilatorSettingList;
    }
}
