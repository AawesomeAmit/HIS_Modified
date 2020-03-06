package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NutrientTable {
    @SerializedName("nutrientName")
    @Expose
    public String nutrientName;
    @SerializedName("nutrient")
    @Expose
    public String nutrient;

    public String getNutrientName() {
        return nutrientName;
    }

    public String getNutrient() {
        return nutrient;
    }
}
