package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateSupplementList {
    @SerializedName("dietID")
    @Expose
    public Integer dietID;
    @SerializedName("intakeName")
    @Expose
    public String intakeName;
    @SerializedName("intakeQuantity")
    @Expose
    public String intakeQuantity;
    @SerializedName("unit")
    @Expose
    public String unit;
    @SerializedName("prescribedTime")
    @Expose
    public String prescribedTime;
    @SerializedName("isSupplement")
    @Expose
    public Integer isSupplement;
    @SerializedName("isGiven")
    @Expose
    public Integer isGiven;

    public Integer getDietID() {
        return dietID;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public String getIntakeQuantity() {
        return intakeQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getPrescribedTime() {
        return prescribedTime;
    }

    public Integer getIsSupplement() {
        return isSupplement;
    }

    public Integer getIsGiven() {
        return isGiven;
    }
}
