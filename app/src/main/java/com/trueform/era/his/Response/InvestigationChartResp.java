package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.GraphResult;

import java.util.List;

public class InvestigationChartResp {
    @SerializedName("graphResult")
    @Expose
    public List<GraphResult> graphResult = null;

    public List<GraphResult> getGraphResult() {
        return graphResult;
    }
}
