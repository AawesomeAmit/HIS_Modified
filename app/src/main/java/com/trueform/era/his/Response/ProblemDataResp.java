package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.ComplainList;

import java.util.List;

public class ProblemDataResp {
    @SerializedName("complainList")
    @Expose
    public List<ComplainList> complainList = null;

    public List<ComplainList> getComplainList() {
        return complainList;
    }
}
