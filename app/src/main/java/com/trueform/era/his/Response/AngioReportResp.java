package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.AngioBillList;
import com.trueform.era.his.Model.AngioReportList;

import java.util.List;

public class AngioReportResp {
    @SerializedName("angioBillList")
    @Expose
    private List<AngioBillList> angioBillList = null;
    public List<AngioBillList> getAngioBillList() {
        return angioBillList;
    }
    @SerializedName("angioReportList")
    @Expose
    private List<AngioReportList> angioReportList = null;

    public List<AngioReportList> getAngioReportList() {
        return angioReportList;
    }
}
