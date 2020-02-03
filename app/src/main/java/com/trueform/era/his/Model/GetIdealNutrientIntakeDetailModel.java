package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetIdealNutrientIntakeDetailModel {
    @SerializedName("sNo")
    @Expose
    public Integer sNo;
    @SerializedName("intakeTime")
    @Expose
    public String intakeTime;
    @SerializedName("intakeQuantity")
    @Expose
    public Integer intakeQuantity;

    public Integer getsNo() {
        return sNo;
    }

    public String getIntakeTime() {
        return intakeTime;
    }

    public Integer getIntakeQuantity() {
        return intakeQuantity;
    }
}
