package com.his.android.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.his.android.Model.Nutrition;

import java.util.List;

public class NutrientBindRes {
    @SerializedName("nutrition")
    @Expose
    public List<Nutrition> nutrition = null;

    public List<Nutrition> getNutrition() {
        return nutrition;
    }
}
