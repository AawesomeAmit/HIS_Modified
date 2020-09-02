package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalAnalysisModel {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("vitalName")
    @Expose
    public String vitalName;

    public VitalAnalysisModel(Integer id, String vitalName) {
        this.id = id;
        this.vitalName = vitalName;
    }

    public Integer getId() {
        return id;
    }

    public String getVitalName() {
        return vitalName;
    }
}
