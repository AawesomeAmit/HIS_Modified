package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.CalciumReportWardList;

import java.util.List;

public class CalciumWardListResp {
    @SerializedName("wardList")
    @Expose
    public List<CalciumReportWardList> wardList = null;

    public List<CalciumReportWardList> getWardList() {
        return wardList;
    }
}
