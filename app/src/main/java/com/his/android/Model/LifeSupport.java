package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LifeSupport {

    @SerializedName("lifeSupportID")
    @Expose
    public Integer lifeSupportID;

    public Integer getLifeSupportID() {
        return lifeSupportID;
    }
}