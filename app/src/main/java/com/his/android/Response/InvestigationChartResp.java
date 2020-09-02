package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.GraphResult;

import java.util.List;

public class InvestigationChartResp {
    @SerializedName("graphResult")
    @Expose
    public List<GraphResult> graphResult = null;

    public List<GraphResult> getGraphResult() {
        return graphResult;
    }
}
