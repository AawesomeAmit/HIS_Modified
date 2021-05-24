package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.Problem;

import java.util.List;

public class ProblemResp {
    @SerializedName("problemList")
    @Expose
    public List<Problem> problemList = null;

    public List<Problem> getProblemList() {
        return problemList;
    }
}
