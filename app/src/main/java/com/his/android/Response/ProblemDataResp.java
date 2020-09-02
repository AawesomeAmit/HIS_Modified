package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.ComplainList;

import java.util.List;

public class ProblemDataResp {
    @SerializedName("complainList")
    @Expose
    public List<ComplainList> complainList = null;

    public List<ComplainList> getComplainList() {
        return complainList;
    }
}
