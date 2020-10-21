package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScanMeal {
    @SerializedName("intakeType")
    @Expose
    public Integer intakeType;
    @SerializedName("intakeID")
    @Expose
    public Integer intakeID;
    @SerializedName("intakeName")
    @Expose
    public String intakeName;
    @SerializedName("isSynonym")
    @Expose
    public Integer isSynonym;
    @SerializedName("textID")
    @Expose
    public Integer textID;
    @SerializedName("intakeQuantity")
    @Expose
    public String intakeQuantity;
    @SerializedName("unitID")
    @Expose
    public Integer unitID;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("pmID")
    @Expose
    public Integer pmID;
    @SerializedName("doseForm")
    @Expose
    public String doseForm;
    @SerializedName("prescriptionID")
    @Expose
    public Integer prescriptionID;

    public Integer getIntakeType() {
        return intakeType;
    }

    public Integer getIntakeID() {
        return intakeID;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public Integer getIsSynonym() {
        return isSynonym;
    }

    public Integer getTextID() {
        return textID;
    }

    public String getIntakeQuantity() {
        return intakeQuantity;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public Integer getPmID() {
        return pmID;
    }

    public String getDoseForm() {
        return doseForm;
    }

    public Integer getPrescriptionID() {
        return prescriptionID;
    }
}
