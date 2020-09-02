package com.his.android.Model;

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

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    public void setVmid(Integer vmid) {
        this.vmid = vmid;
    }

    public void setVitalName(String vitalName) {
        this.vitalName = vitalName;
    }

    public void setVmValue(Double vmValue) {
        this.vmValue = vmValue;
    }

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
