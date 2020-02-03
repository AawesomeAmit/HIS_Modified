package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.VitalAnalysisModel;

import java.util.List;

public class VitalAutoCompleteResp {
    @SerializedName("vitalList")
    @Expose
    public List<VitalAnalysisModel> vitalList = null;

    public List<VitalAnalysisModel> getVitalList() {
        return vitalList;
    }
}
