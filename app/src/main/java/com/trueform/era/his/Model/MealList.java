package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MealList {
    @SerializedName("intakeID")
    @Expose
    public Integer intakeID;
    @SerializedName("intakeName")
    @Expose
    public String intakeName;
    @SerializedName("textID")
    @Expose
    public Integer textID;
    @SerializedName("isSupplement")
    @Expose
    public Integer isSupplement;
    @SerializedName("isSynonym")
    @Expose
    public Integer isSynonym;

    public Integer getTextID() {
        return textID;
    }

    public Integer getIsSupplement() {
        return isSupplement;
    }

    public Integer getIsSynonym() {
        return isSynonym;
    }

    @Override
    public String toString() {
        return intakeName;
    }

    public Integer getIntakeID() {
        return intakeID;
    }

    public String getIntakeName() {
        return intakeName;
    }
}
