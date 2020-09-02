package com.his.android.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitMaster {
    @SerializedName("unitid")
    @Expose
    public Integer unitid;
    @SerializedName("unitname")
    @Expose
    public String unitname;

    public UnitMaster(Integer unitid, String unitname) {
        this.unitid = unitid;
        this.unitname = unitname;
    }

    @NonNull
    @Override
    public String toString() {
        return unitname;
    }

    public Integer getUnitid() {
        return unitid;
    }

    public String getUnitname() {
        return unitname;
    }
}
