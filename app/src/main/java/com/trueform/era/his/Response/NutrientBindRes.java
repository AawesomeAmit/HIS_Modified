package com.trueform.era.his.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trueform.era.his.Model.Nutrition;

import java.util.List;

public class NutrientBindRes {
    @SerializedName("nutrition")
    @Expose
    public List<Nutrition> nutrition = null;

    public List<Nutrition> getNutrition() {
        return nutrition;
    }
}
