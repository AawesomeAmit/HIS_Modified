package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplainList {
    @SerializedName("patientComplainAttributeID")
    @Expose
    public Integer patientComplainAttributeID;
    @SerializedName("patientComplainID")
    @Expose
    public Integer patientComplainID;
    @SerializedName("problemID")
    @Expose
    public Integer problemID;
    @SerializedName("problemName")
    @Expose
    public String problemName;
    @SerializedName("remark")
    @Expose
    public Object remark;
    @SerializedName("timeFrom")
    @Expose
    public String timeFrom;
    @SerializedName("timeTo")
    @Expose
    public String timeTo;
    @SerializedName("attributeName")
    @Expose
    public String attributeName;
    @SerializedName("attributeValue")
    @Expose
    public String attributeValue;

    public Integer getPatientComplainAttributeID() {
        return patientComplainAttributeID;
    }

    public Integer getPatientComplainID() {
        return patientComplainID;
    }

    public Integer getProblemID() {
        return problemID;
    }

    public String getProblemName() {
        return problemName;
    }

    public Object getRemark() {
        return remark;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }
}
