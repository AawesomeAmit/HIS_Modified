package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.DiseaseList;

import java.util.List;

public class DiseaseListResp {
    @SerializedName("diseaseList")
    @Expose
    public List<DiseaseList> diseaseList = null;

    public List<DiseaseList> getDiseaseList() {
        return diseaseList;
    }
}
