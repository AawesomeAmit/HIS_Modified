package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.AngioReportList;

import java.util.List;

public class AngioReportResp {
    @SerializedName("angioReportList")
    @Expose
    public List<AngioReportList> angioReportList = null;

    public List<AngioReportList> getAngioReportList() {
        return angioReportList;
    }
}
