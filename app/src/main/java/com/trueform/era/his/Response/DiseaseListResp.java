package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.DiseaseList;

import java.util.List;

public class DiseaseListResp {
    @SerializedName("diseaseList")
    @Expose
    public List<DiseaseList> diseaseList = null;

    public List<DiseaseList> getDiseaseList() {
        return diseaseList;
    }
}
