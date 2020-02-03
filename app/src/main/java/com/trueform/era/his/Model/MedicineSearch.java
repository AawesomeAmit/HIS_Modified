package com.trueform.era.his.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicineSearch {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
    @SerializedName("drugID")
    @Expose
    public Integer drugID;
    @SerializedName("sno")
    @Expose
    public Object sno;
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
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("createdDate")
    @Expose
    public String createdDate;
    @NonNull
    @Override
    public String toString() {
        return drugName;
    }

    public MedicineSearch(Integer drugID, String drugName, String dosageForm, String doseStrength, String doseUnit, String doseFrequency, String duration, String remark) {
        this.drugID = drugID;
        this.drugName = drugName;
        this.dosageForm = dosageForm;
        this.doseStrength = doseStrength;
        this.doseUnit = doseUnit;
        this.doseFrequency = doseFrequency;
        this.duration = duration;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPmID() {
        return pmID;
    }

    public Integer getDrugID() {
        return drugID;
    }

    public Object getSno() {
        return sno;
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

    public void setDrugID(Integer drugID) {
        this.drugID = drugID;
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

    public Integer getStatus() {
        return status;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
