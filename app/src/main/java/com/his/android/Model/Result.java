package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("formula")
    @Expose
    public String formula;
    @SerializedName("unit")
    @Expose
    public String unit;
    @SerializedName("calculatedResult")
    @Expose
    public String calculatedResult;

    public String getFormula() {
        return formula;
    }

    public String getUnit() {
        return unit;
    }

    public String getCalculatedResult() {
        return calculatedResult;
    }
}
