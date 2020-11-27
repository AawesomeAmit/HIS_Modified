package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientIsOnOxygen {
    @SerializedName("onOxygen")
    @Expose
    public Integer onOxygen;

    public Integer getOnOxygen() {
        return onOxygen;
    }
}
