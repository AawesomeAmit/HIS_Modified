package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.VentilatorList;

import java.util.List;

public class VentilatorListResp {
    @SerializedName("ventilatorList")
    @Expose
    public List<VentilatorList> ventilatorList = null;

    public List<VentilatorList> getVentilatorList() {
        return ventilatorList;
    }
}
