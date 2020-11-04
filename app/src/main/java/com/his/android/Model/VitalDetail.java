package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalDetail {
    @SerializedName("vitalName")
    @Expose
    public String vitalName;
    @SerializedName("vitalValue")
    @Expose
    public String vitalValue;
    @SerializedName("dataDuration")
    @Expose
    public String dataDuration;

    public String getVitalName() {
        return vitalName;
    }

    public String getVitalValue() {
        return vitalValue;
    }

    public String getDataDuration() {
        return dataDuration;
    }
}
