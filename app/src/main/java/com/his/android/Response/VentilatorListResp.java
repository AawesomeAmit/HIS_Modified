package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.VentilatorList;

import java.util.List;

public class VentilatorListResp {
    @SerializedName("ventilatorList")
    @Expose
    public List<VentilatorList> ventilatorList = null;

    public List<VentilatorList> getVentilatorList() {
        return ventilatorList;
    }
}
