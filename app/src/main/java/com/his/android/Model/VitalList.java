package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalList {
    @SerializedName("vitalName")
    @Expose
    public String vitalName;
    @SerializedName("vmID")
    @Expose
    public Integer vmID;
    @SerializedName("vitalValue")
    @Expose
    public String vitalValue;
    @SerializedName("vitalUnit")
    @Expose
    public String vitalUnit;
    @SerializedName("vitalColorCode")
    @Expose
    public String vitalColorCode;
    @SerializedName("vitalType")
    @Expose
    public String vitalType;

    public String getVitalName() {
        return vitalName;
    }

    public Integer getVmID() {
        return vmID;
    }

    public String getVitalValue() {
        return vitalValue;
    }

    public String getVitalUnit() {
        return vitalUnit;
    }

    public void setVitalName(String vitalName) {
        this.vitalName = vitalName;
    }

    public void setVmID(Integer vmID) {
        this.vmID = vmID;
    }

    public void setVitalValue(String vitalValue) {
        this.vitalValue = vitalValue;
    }

    public void setVitalUnit(String vitalUnit) {
        this.vitalUnit = vitalUnit;
    }

    public String getVitalColorCode() {
        return vitalColorCode;
    }

    public void setVitalColorCode(String vitalColorCode) {
        this.vitalColorCode = vitalColorCode;
    }

    public String getVitalType() {
        return vitalType;
    }

    public void setVitalType(String vitalType) {
        this.vitalType = vitalType;
    }
}
