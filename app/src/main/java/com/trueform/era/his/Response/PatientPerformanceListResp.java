package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.PatientPerformanceListModel;

import java.util.List;

public class PatientPerformanceListResp {
    @SerializedName("performanceList")
    @Expose
    private List<PatientPerformanceListModel> performanceList = null;

    public List<PatientPerformanceListModel> getPerformanceList() {
        return performanceList;
    }

    public void setPerformanceList(List<PatientPerformanceListModel> performanceList) {
        this.performanceList = performanceList;
    }

}
