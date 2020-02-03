package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntakeOutputList {
    @SerializedName("valueDateTime")
    @Expose
    public String valueDateTime;
    @SerializedName("intakeQty")
    @Expose
    public Integer intakeQty;
    @SerializedName("outputQty")
    @Expose
    public Integer outputQty;

    public String getValueDateTime() {
        return valueDateTime;
    }

    public Integer getIntakeQty() {
        return intakeQty;
    }

    public Integer getOutputQty() {
        return outputQty;
    }
}
