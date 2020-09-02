package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalMaster {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("vitalName")
    @Expose
    public String vitalName;

    public VitalMaster(Integer id, String vitalName) {
        this.id = id;
        this.vitalName = vitalName;
    }

    @Override
    public String toString() {
        return vitalName;
    }

    public Integer getId() {
        return id;
    }

    public String getVitalName() {
        return vitalName;
    }
}
