package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.ParameterScore;

import java.util.List;

public class ParameterScoreResp {
    @SerializedName("parameterScore")
    @Expose
    private List<ParameterScore> parameterScore = null;

    public List<ParameterScore> getParameterScore() {
        return parameterScore;
    }
}
