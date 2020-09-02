package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.AngioBillList;
import com.his.android.Model.AngioReportList;

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
