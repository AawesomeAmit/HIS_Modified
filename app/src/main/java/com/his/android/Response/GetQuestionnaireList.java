package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.ReportList;

import java.util.List;

public class GetQuestionnaireList {
    @SerializedName("reportList")
    @Expose
    public List<ReportList> reportList = null;

    public List<ReportList> getReportList() {
        return reportList;
    }
}
