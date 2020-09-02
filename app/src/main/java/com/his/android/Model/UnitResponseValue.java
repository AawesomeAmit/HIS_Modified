package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UnitResponseValue {
    @SerializedName("defaultQuantity")
    @Expose
    public String defaultQuantity;
    @SerializedName("defaultUnitID")
    @Expose
    public Integer defaultUnitID;
    @SerializedName("unitList")
    @Expose
    public List<UnitList> unitList = null;

    public String getDefaultQuantity() {
        return defaultQuantity;
    }

    public Integer getDefaultUnitID() {
        return defaultUnitID;
    }

    public List<UnitList> getUnitList() {
        return unitList;
    }
}
