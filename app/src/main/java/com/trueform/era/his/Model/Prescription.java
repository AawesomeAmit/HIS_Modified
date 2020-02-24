package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prescription {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("drugID")
    @Expose
    public Integer drugID;
    @SerializedName("drugName")
    @Expose
    public String drugName;
    @SerializedName("dosageForm")
    @Expose
    public String dosageForm;
    @SerializedName("doseStrength")
    @Expose
    public String doseStrength;
    @SerializedName("doseUnit")
    @Expose
    public String doseUnit;
    @SerializedName("doseFrequency")
    @Expose
    public String doseFrequency;
    @SerializedName("duration")
    @Expose
    public String duration;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("prescribedDated")
    @Expose
    public String prescribedDated;
    @SerializedName("isShow")
    @Expose
    public Integer isShow;

    public Prescription(Integer drugID, String drugName, String dosageForm, String doseStrength, String doseUnit, String doseFrequency, String duration, String remark, Integer isShow) {
        this.drugID = drugID;
        this.drugName = drugName;
        this.dosageForm = dosageForm;
        this.doseStrength = doseStrength;
        this.doseUnit = doseUnit;
        this.doseFrequency = doseFrequency;
        this.duration = duration;
        this.remark = remark;
        this.isShow=isShow;
    }

    public void setDrugID(Integer drugID) {
        this.drugID = drugID;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDrugID() {
        return drugID;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public String getDoseStrength() {
        return doseStrength;
    }

    public String getDoseUnit() {
        return doseUnit;
    }

    public String getDoseFrequency() {
        return doseFrequency;
    }

    public String getDuration() {
        return duration;
    }

    public String getRemark() {
        return remark;
    }

    public String getPrescribedDated() {
        return prescribedDated;
    }

    public Integer getIsShow() {
        return isShow;
    }
}
