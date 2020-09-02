package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NutrientListAnalyzingGraph {
    @SerializedName("graphDateTime")
    @Expose
    public String graphDateTime;
    @SerializedName("valueTime")
    @Expose
    public String valueTime;
    @SerializedName("nutrient")
    @Expose
    public String nutrient;

    public String getGraphDateTime() {
        return graphDateTime;
    }

    public String getValueTime() {
        return valueTime;
    }

    public String getNutrient() {
        return nutrient;
    }
}
