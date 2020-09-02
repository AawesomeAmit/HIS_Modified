package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientDiagnosisVitalListModel {
    @SerializedName("pmid")
    @Expose
    private Integer pmid;
    @SerializedName("vmid")
    @Expose
    private Integer vmid;
    @SerializedName("vmValue")
    @Expose
    private String vmValue;
    @SerializedName("vitalName")
    @Expose
    private String vitalName;
    @SerializedName("lastVitalDate")
    @Expose
    private String lastVitalDate;
    @SerializedName("normalRange")
    @Expose
    private String normalRange;

    public Integer getPmid() {
        return pmid;
    }

    public Integer getVmid() {
        return vmid;
    }

    public String getVmValue() {
        return vmValue;
    }

    public String getVitalName() {
        return vitalName;
    }

    public String getLastVitalDate() {
        return lastVitalDate;
    }

    public String getNormalRange() {
        return normalRange;
    }

}
