package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.ParameterScore;

import java.util.List;

public class ParameterScoreResp {
    @SerializedName("parameterScore")
    @Expose
    private List<ParameterScore> parameterScore = null;

    public List<ParameterScore> getParameterScore() {
        return parameterScore;
    }
}
