package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.CalciumReportWardList;

import java.util.List;

public class CalciumWardListResp {
    @SerializedName("wardList")
    @Expose
    public List<CalciumReportWardList> wardList = null;

    public List<CalciumReportWardList> getWardList() {
        return wardList;
    }
}
