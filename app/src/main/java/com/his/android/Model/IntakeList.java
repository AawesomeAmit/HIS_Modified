package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntakeList {
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
    @SerializedName("intakeDate")
    @Expose
    public String intakeDate;
    @SerializedName("intakeDateTime")
    @Expose
    public String intakeDateTime;
    @SerializedName("displayName")
    @Expose
    public String displayName;

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

    public String getIntakeDate() {
        return intakeDate;
    }

    public String getIntakeDateTime() {
        return intakeDateTime;
    }

    public String getDisplayName() {
        return displayName;
    }
}
