package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalAnalysisModel {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("vitalName")
    @Expose
    public String vitalName;

    public Integer getId() {
        return id;
    }

    public String getVitalName() {
        return vitalName;
    }
}
