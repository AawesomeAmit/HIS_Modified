package com.trueform.era.his.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NutrientFoodResponseValue {
    @SerializedName("FoodList")
    @Expose
    public List<FoodList> foodList = null;
    @SerializedName("Nutrients")
    @Expose
    public List<Nutrient> nutrients = null;
    @SerializedName("SupplementList")
    @Expose
    public List<SupplementFoodList> supplementList = null;
    @SerializedName("ImmediateEffect")
    @Expose
    public List<Object> immediateEffect = null;
    @SerializedName("GraphCategory")
    @Expose
    public List<GraphCategory> graphCategory = null;
    @SerializedName("ChartNutrients")
    @Expose
    public List<ChartNutrient> chartNutrients = null;

    public List<FoodList> getFoodList() {
        return foodList;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public List<SupplementFoodList> getSupplementList() {
        return supplementList;
    }

    public List<Object> getImmediateEffect() {
        return immediateEffect;
    }

    public List<GraphCategory> getGraphCategory() {
        return graphCategory;
    }

    public List<ChartNutrient> getChartNutrients() {
        return chartNutrients;
    }
}
