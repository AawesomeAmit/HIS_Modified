package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicineDetail {
    @SerializedName("prescriptionID")
    @Expose
    public Integer prescriptionID;
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
    @SerializedName("medicineName")
    @Expose
    public String medicineName;
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
    @SerializedName("noOfDays")
    @Expose
    public String noOfDays;
    @SerializedName("remark")
    @Expose
    public String remark;

    public Integer getPrescriptionID() {
        return prescriptionID;
    }

    public Integer getPmID() {
        return pmID;
    }

    public String getMedicineName() {
        return medicineName;
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

    public String getNoOfDays() {
        return noOfDays;
    }

    public String getRemark() {
        return remark;
    }
}
