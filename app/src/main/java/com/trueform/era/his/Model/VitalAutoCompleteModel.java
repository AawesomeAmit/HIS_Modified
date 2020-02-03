package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalAutoCompleteModel {
    @SerializedName("vmID")
    @Expose
    public Integer vmID;
    @SerializedName("vitalName")
    @Expose
    public String vitalName;
    @SerializedName("vmValue")
    @Expose
    public Object vmValue;
    @SerializedName("valueDateTime")
    @Expose
    public String valueDateTime;
    @SerializedName("valueTime")
    @Expose
    public String valueTime;

    public Integer getVmID() {
        return vmID;
    }

    public String getVitalName() {
        return vitalName;
    }

    public Object getVmValue() {
        return vmValue;
    }

    public String getValueDateTime() {
        return valueDateTime;
    }

    public String getValueTime() {
        return valueTime;
    }
}
