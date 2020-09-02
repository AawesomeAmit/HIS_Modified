package com.his.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Nutrient {
    @SerializedName("nutrientCategory")
    @Expose
    public String nutrientCategory;
    @SerializedName("nutrientList")
    @Expose
    public List<NutrientList> nutrientList = null;

    public String getNutrientCategory() {
        return nutrientCategory;
    }

    public List<NutrientList> getNutrientList() {
        return nutrientList;
    }
}
