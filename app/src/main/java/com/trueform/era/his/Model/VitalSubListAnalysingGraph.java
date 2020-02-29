package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalSubListAnalysingGraph {
    @SerializedName("vmID")
    @Expose
    public Integer vmID;
    @SerializedName("vitalName")
    @Expose
    public String vitalName;
    @SerializedName("vmValue")
    @Expose
    public String vmValue;

    public Integer getVmID() {
        return vmID;
    }

    public String getVitalName() {
        return vitalName;
    }

    public String getVmValue() {
        return vmValue;
    }
}
