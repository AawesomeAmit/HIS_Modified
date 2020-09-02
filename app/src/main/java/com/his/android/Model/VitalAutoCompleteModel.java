package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VitalAutoCompleteModel {
    @SerializedName("valueDateTime")
    @Expose
    public String valueDateTime;
    @SerializedName("valueTime")
    @Expose
    public String valueTime;
    @SerializedName("vital")
    @Expose
    public String vital;
    @SerializedName("problem")
    @Expose
    public Object problem;
    @SerializedName("activity")
    @Expose
    public Object activity;
    @SerializedName("exercise")
    @Expose
    public Object exercise;
    @SerializedName("investigation")
    @Expose
    public Object investigation;
    @SerializedName("foodIntake")
    @Expose
    public Object foodIntake;
    @SerializedName("intakeQty")
    @Expose
    public Object intakeQty;
    @SerializedName("outputQty")
    @Expose
    public Object outputQty;
    @SerializedName("intakeMedicine")
    @Expose
    public Object intakeMedicine;
    @SerializedName("activityDetail")
    @Expose
    public Object activityDetail;

    public String getValueDateTime() {
        return valueDateTime;
    }

    public String getValueTime() {
        return valueTime;
    }

    public String getVital() {
        return vital;
    }

    public Object getProblem() {
        return problem;
    }

    public Object getActivity() {
        return activity;
    }

    public Object getExercise() {
        return exercise;
    }

    public Object getInvestigation() {
        return investigation;
    }

    public Object getFoodIntake() {
        return foodIntake;
    }

    public Object getIntakeQty() {
        return intakeQty;
    }

    public Object getOutputQty() {
        return outputQty;
    }

    public Object getIntakeMedicine() {
        return intakeMedicine;
    }

    public Object getActivityDetail() {
        return activityDetail;
    }
}
