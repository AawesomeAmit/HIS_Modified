package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalSubListAnalysingGraph {
    @SerializedName("vmValue")
    @Expose
    public Double vmValue;
    @SerializedName("valueDateTime")
    @Expose
    public String valueDateTime;

    public Double getVmValue() {
        return vmValue;
    }

    public String getValueDateTime() {
        return valueDateTime;
    }
}
