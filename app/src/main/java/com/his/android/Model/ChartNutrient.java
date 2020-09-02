package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChartNutrient {
    @SerializedName("nutrientID")
    @Expose
    public Integer nutrientID;
    @SerializedName("tagName")
    @Expose
    public String tagName;
    @SerializedName("target")
    @Expose
    public String target;
    @SerializedName("totalNutrientValue")
    @Expose
    public String totalNutrientValue;
    @SerializedName("totalRDAPercentage")
    @Expose
    public Integer totalRDAPercentage;
    @SerializedName("unitName")
    @Expose
    public String unitName;
    @SerializedName("nutrientCategory")
    @Expose
    public String nutrientCategory;

    public Integer getNutrientID() {
        return nutrientID;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTarget() {
        return target;
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

    public String getNutrientCategory() {
        return nutrientCategory;
    }
}
