package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObservationList {
    @SerializedName("valueDateTime")
    @Expose
    public String valueDateTime;
    @SerializedName("valueDateTime1")
    @Expose
    public String valueDateTime1;
    @SerializedName("valueTime")
    @Expose
    public String valueTime;
    @SerializedName("problem")
    @Expose
    public Object problem;
    @SerializedName("activity")
    @Expose
    public String activity;
    @SerializedName("exercise")
    @Expose
    public Object exercise;
    @SerializedName("investigation")
    @Expose
    public Object investigation;
    @SerializedName("foodIntake")
    @Expose
    public Object foodIntake;
    @SerializedName("intakeOutput")
    @Expose
    public Object intakeOutput;
    @SerializedName("intakeMedicine")
    @Expose
    public Object intakeMedicine;

    public String getValueDateTime() {
        return valueDateTime;
    }

    public String getValueDateTime1() {
        return valueDateTime1;
    }

    public String getValueTime() {
        return valueTime;
    }

    public Object getProblem() {
        return problem;
    }

    public String getActivity() {
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

    public Object getIntakeOutput() {
        return intakeOutput;
    }

    public Object getIntakeMedicine() {
        return intakeMedicine;
    }
}
