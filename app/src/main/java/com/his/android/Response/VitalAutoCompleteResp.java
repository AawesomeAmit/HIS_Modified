package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.VitalAnalysisModel;

import java.util.List;

public class VitalAutoCompleteResp {
    @SerializedName("vitalList")
    @Expose
    public List<VitalAnalysisModel> vitalList = null;

    public List<VitalAnalysisModel> getVitalList() {
        return vitalList;
    }
}
