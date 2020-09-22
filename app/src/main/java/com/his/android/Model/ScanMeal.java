package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScanMeal {
    @SerializedName("intakeID")
    @Expose
    public Integer intakeID;
    @SerializedName("intakeName")
    @Expose
    public String intakeName;
    @SerializedName("isSupplement")
    @Expose
    public Integer isSupplement;
    @SerializedName("isSynonym")
    @Expose
    public Integer isSynonym;
    @SerializedName("textID")
    @Expose
    public Integer textID;
    @SerializedName("intakeQuantity")
    @Expose
    public Float intakeQuantity;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("unitID")
    @Expose
    public Integer unitID;

    public Integer getIntakeID() {
        return intakeID;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public Integer getIsSupplement() {
        return isSupplement;
    }

    public Integer getIsSynonym() {
        return isSynonym;
    }

    public Integer getTextID() {
        return textID;
    }

    public String getUnitName() {
        return unitName;
    }

    public Float getIntakeQuantity() {
        return intakeQuantity;
    }

    public Integer getUnitID() {
        return unitID;
    }
}
