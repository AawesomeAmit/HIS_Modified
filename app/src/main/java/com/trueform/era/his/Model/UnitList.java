package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("unitName")
    @Expose
    public String unitName;

    @Override
    public String toString() {
        return unitName;
    }

    public UnitList(Integer id, String unitName) {
        this.id = id;
        this.unitName = unitName;
    }

    public Integer getId() {
        return id;
    }

    public String getUnitName() {
        return unitName;
    }
}
