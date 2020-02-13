package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalciumVitalReport {
    @SerializedName("pmid")
    @Expose
    public Integer pmid;
    @SerializedName("vmid")
    @Expose
    public Integer vmid;
    @SerializedName("vitalName")
    @Expose
    public String vitalName;
    @SerializedName("vmValue")
    @Expose
    public Double vmValue;

    public Integer getPmid() {
        return pmid;
    }

    public Integer getVmid() {
        return vmid;
    }

    public String getVitalName() {
        return vitalName;
    }

    public Double getVmValue() {
        return vmValue;
    }
}
