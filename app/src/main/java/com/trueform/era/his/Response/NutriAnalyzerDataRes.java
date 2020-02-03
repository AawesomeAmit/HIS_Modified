package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.NutriAnalyze;

import java.util.List;

public class NutriAnalyzerDataRes {
    @SerializedName("nutrientCategory")
    @Expose
    private String nutrientCategory;
    @SerializedName("nutrientList")
    @Expose
    private List<NutriAnalyze> nutrientList = null;

    public String getNutrientCategory() {
        return nutrientCategory;
    }

    public void setNutrientCategory(String nutrientCategory) {
        this.nutrientCategory = nutrientCategory;
    }

    public List<NutriAnalyze> getNutrientList() {
        return nutrientList;
    }

    public void setNutrientList(List<NutriAnalyze> nutrientList) {
        this.nutrientList = nutrientList;
    }
}
