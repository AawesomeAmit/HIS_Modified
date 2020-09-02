package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalList {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("vitalName")
    @Expose
    private String vitalName;
    @SerializedName("unit")
    @Expose
    public String unit;
    @SerializedName("vitalValue")
    @Expose
    public String value;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVitalName(String vitalName) {
        this.vitalName = vitalName;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public VitalList(Integer id, String vitalName, String unit, String value) {
        this.id = id;
        this.vitalName = vitalName;
        this.unit = unit;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }
}
