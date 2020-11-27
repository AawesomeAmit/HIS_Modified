package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LifeSupportMasterList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("lifeSupportType")
    @Expose
    public String lifeSupportType;

    public Integer getId() {
        return id;
    }

    public LifeSupportMasterList(Integer id, String lifeSupportType) {
        this.id = id;
        this.lifeSupportType = lifeSupportType;
    }

    @Override
    public String toString() {
        return lifeSupportType;
    }

    public String getLifeSupportType() {
        return lifeSupportType;
    }
}
