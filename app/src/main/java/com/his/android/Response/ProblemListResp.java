package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.ProblemList;

import java.util.List;

public class ProblemListResp {
    @SerializedName("problemList")
    @Expose
    public List<ProblemList> problemList = null;

    public List<ProblemList> getProblemList() {
        return problemList;
    }
}
