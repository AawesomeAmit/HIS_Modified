package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ControlList {
    @SerializedName("parameterID")
    @Expose
    public Integer parameterID;
    @SerializedName("parameterName")
    @Expose
    public String parameterName;
    @SerializedName("controlType")
    @Expose
    public String controlType;
    @SerializedName("labelDisplay")
    @Expose
    public String labelDisplay;
    @SerializedName("referenceID")
    @Expose
    public String referenceID;
    @SerializedName("isScore")
    @Expose
    public Boolean isScore;
    @SerializedName("parameterValue")
    @Expose
    public String parameterValue;
    @SerializedName("parameterScore")
    @Expose
    public String parameterScore;

    public Integer getParameterID() {
        return parameterID;
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getControlType() {
        return controlType;
    }

    public String getLabelDisplay() {
        return labelDisplay;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public Boolean getScore() {
        return isScore;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public String getParameterScore() {
        return parameterScore;
    }
}
