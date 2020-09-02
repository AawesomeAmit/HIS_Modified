package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.VentilatorSettingDetail;

import java.util.List;

public class VentilatorSettingDetailResp {
    @SerializedName("ventilatorSettingDetails")
    @Expose
    public List<VentilatorSettingDetail> ventilatorSettingDetails = null;

    public List<VentilatorSettingDetail> getVentilatorSettingDetails() {
        return ventilatorSettingDetails;
    }
}
