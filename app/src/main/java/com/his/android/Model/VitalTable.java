package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalTable {
    @SerializedName("vitalName")
    @Expose
    public String vitalName;
    @SerializedName("vitalValue")
    @Expose
    public String vitalValue;

    public String getVitalName() {
        return vitalName;
    }

    public String getVitalValue() {
        return vitalValue;
    }
}
