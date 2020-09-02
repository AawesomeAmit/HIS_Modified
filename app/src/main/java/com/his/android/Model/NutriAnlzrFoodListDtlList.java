package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NutriAnlzrFoodListDtlList {
    @SerializedName("nutrientName")
    @Expose
    public String nutrientName;
    @SerializedName("achievedNutrientValue")
    @Expose
    public String achievedNutrientValue;
    @SerializedName("rda")
    @Expose
    public String rda;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("achievedRDAPercentage")
    @Expose
    public Integer achievedRDAPercentage;

    public String getNutrientName() {
        return nutrientName;
    }

    public String getAchievedNutrientValue() {
        return achievedNutrientValue;
    }

    public String getRda() {
        return rda;
    }

    public String getUnitName() {
        return unitName;
    }

    public Integer getAchievedRDAPercentage() {
        return achievedRDAPercentage;
    }
}
