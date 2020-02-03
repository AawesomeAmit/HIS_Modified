package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.ReportList;

import java.util.List;

public class GetQuestionnaireList {
    @SerializedName("reportList")
    @Expose
    public List<ReportList> reportList = null;

    public List<ReportList> getReportList() {
        return reportList;
    }
}
