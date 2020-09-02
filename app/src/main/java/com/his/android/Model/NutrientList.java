package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NutrientList {
    @SerializedName("nutrientID")
    @Expose
    public Integer nutrientID;
    @SerializedName("nutrientName")
    @Expose
    public String nutrientName;
    @SerializedName("target")
    @Expose
    public String target;
    @SerializedName("achievedNutrientValue")
    @Expose
    public String achievedNutrientValue;
    @SerializedName("achievedRDAPercentage")
    @Expose
    public Integer achievedRDAPercentage;
    @SerializedName("achievedRDAColorCode")
    @Expose
    public String achievedRDAColorCode;
    @SerializedName("extraNutrientValue")
    @Expose
    public String extraNutrientValue;
    @SerializedName("extraRDAPercentage")
    @Expose
    public Integer extraRDAPercentage;
    @SerializedName("extraRDAColorCode")
    @Expose
    public String extraRDAColorCode;
    @SerializedName("totalNutrientValue")
    @Expose
    public String totalNutrientValue;
    @SerializedName("totalRDAPercentage")
    @Expose
    public Integer totalRDAPercentage;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("graphCategory")
    @Expose
    public String graphCategory;

    public Integer getNutrientID() {
        return nutrientID;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public String getTarget() {
        return target;
    }

    public String getAchievedNutrientValue() {
        return achievedNutrientValue;
    }

    public Integer getAchievedRDAPercentage() {
        return achievedRDAPercentage;
    }

    public String getAchievedRDAColorCode() {
        return achievedRDAColorCode;
    }

    public String getExtraNutrientValue() {
        return extraNutrientValue;
    }

    public Integer getExtraRDAPercentage() {
        return extraRDAPercentage;
    }

    public String getExtraRDAColorCode() {
        return extraRDAColorCode;
    }

    public String getTotalNutrientValue() {
        return totalNutrientValue;
    }

    public Integer getTotalRDAPercentage() {
        return totalRDAPercentage;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getGraphCategory() {
        return graphCategory;
    }
}
