package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.VentilatorSettingDetail;

import java.util.List;

public class VentilatorSettingDetailResp {
    @SerializedName("ventilatorSettingDetails")
    @Expose
    public List<VentilatorSettingDetail> ventilatorSettingDetails = null;

    public List<VentilatorSettingDetail> getVentilatorSettingDetails() {
        return ventilatorSettingDetails;
    }
}
