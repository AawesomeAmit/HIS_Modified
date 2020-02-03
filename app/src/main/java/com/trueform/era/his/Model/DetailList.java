package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailList {
    @SerializedName("dataValue")
    @Expose
    public Object dataValue;
    @SerializedName("valueDate")
    @Expose
    public String valueDate;
    @SerializedName("valueDateTime")
    @Expose
    public String valueDateTime;
    @SerializedName("problem")
    @Expose
    public Object problem;
    @SerializedName("medicine")
    @Expose
    public Object medicine;
    @SerializedName("intakeOutput")
    @Expose
    public Object intakeOutput;
    @SerializedName("intakeFood")
    @Expose
    public Object intakeFood;
    @SerializedName("activity")
    @Expose
    public String activity;
    @SerializedName("hr")
    @Expose
    public Integer hr;

    public Object getDataValue() {
        return dataValue;
    }

    public String getValueDate() {
        return valueDate;
    }

    public String getValueDateTime() {
        return valueDateTime;
    }

    public Object getProblem() {
        return problem;
    }

    public Object getMedicine() {
        return medicine;
    }

    public Object getIntakeOutput() {
        return intakeOutput;
    }

    public Object getIntakeFood() {
        return intakeFood;
    }

    public String getActivity() {
        return activity;
    }

    public Integer getHr() {
        return hr;
    }
}
