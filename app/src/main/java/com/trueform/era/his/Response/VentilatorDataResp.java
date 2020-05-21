package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.VentilatorData;

import java.util.List;

public class VentilatorDataResp {
    @SerializedName("ventilatorData")
    @Expose
    public List<VentilatorData> ventilatorData = null;

    public List<VentilatorData> getVentilatorData() {
        return ventilatorData;
    }
}
