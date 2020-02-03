package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrescriptionList {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
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
    @SerializedName("isStop")
    @Expose
    public Integer isStop;
    @SerializedName("intakeDateTime")
    @Expose
    public String intakeDateTime;
    @SerializedName("timeInterval")
    @Expose
    public Integer timeInterval;
    @SerializedName("nextDose")
    @Expose
    public String nextDose;
    @SerializedName("timeDiff")
    @Expose
    public Integer timeDiff;
    @SerializedName("colorStatus")
    @Expose
    public String colorStatus;

    public Integer getId() {
        return id;
    }

    public Integer getPmID() {
        return pmID;
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

    public Integer getIsStop() {
        return isStop;
    }

    public String getIntakeDateTime() {
        return intakeDateTime;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public String getNextDose() {
        return nextDose;
    }

    public Integer getTimeDiff() {
        return timeDiff;
    }

    public String getColorStatus() {
        return colorStatus;
    }
}
