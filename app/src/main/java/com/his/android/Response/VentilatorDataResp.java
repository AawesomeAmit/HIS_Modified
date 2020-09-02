package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.VentilatorData;

import java.util.List;

public class VentilatorDataResp {
    @SerializedName("ventilatorData")
    @Expose
    public List<VentilatorData> ventilatorData = null;

    public List<VentilatorData> getVentilatorData() {
        return ventilatorData;
    }
}
