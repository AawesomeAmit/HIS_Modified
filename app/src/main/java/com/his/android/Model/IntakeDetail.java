package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntakeDetail {
    @SerializedName("intakeDateTime")
    @Expose
    public String intakeDateTime;
    @SerializedName("intake")
    @Expose
    public String intake;

    public String getIntakeDateTime() {
        return intakeDateTime;
    }

    public String getIntake() {
        return intake;
    }
}
