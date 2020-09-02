package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.VitalList;

import java.util.List;

public class VitalListResp {
    @SerializedName("vitalList")
    @Expose
    public List<VitalList> vitalList = null;

    public void setVitalList(List<VitalList> vitalList) {
        this.vitalList = vitalList;
    }

    public List<VitalList> getVitalList() {
        return vitalList;
    }
}
